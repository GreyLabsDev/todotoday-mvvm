package com.greylabs.todotoday.screens.task_details

import androidx.lifecycle.*
import com.greylabs.todotoday.base.BaseViewModel
import com.greylabs.todotoday.base.ProgressState
import com.greylabs.todotoday.data.AppPrefs
import com.greylabs.todotoday.data.TestingRepository
import com.greylabs.todotoday.screens.tasks.data_model.TaskDataModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.getKoin
import org.koin.standalone.inject
import java.util.*
import java.util.concurrent.TimeUnit

class TaskDetailsActivityViewModel(private val testRepo: TestingRepository,
                                   private val prefs: AppPrefs): ViewModel(), LifecycleObserver, BaseViewModel, KoinComponent {

    private var task: MutableLiveData<TaskDataModel> = MutableLiveData()
    private var progressState: MutableLiveData<ProgressState> = MutableLiveData()

    private var disposables = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun loadTaskData() {
        disposables += Observable.just(TaskDataModel(
                UUID.randomUUID(),
                "Test task",
                "Test task description",
                Calendar.getInstance().time
        )).doOnSubscribe{
            progressState.postValue(ProgressState.Loading())
        }.delay(3, TimeUnit.SECONDS).subscribe { taskData ->
            progressState.postValue(ProgressState.Done())
            task.postValue(taskData)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun dispose() {
        disposables.dispose()
    }

    fun getTask(): MutableLiveData<TaskDataModel> {
        return task
    }

    fun getProgressState(): MutableLiveData<ProgressState> {
        return progressState
    }

}