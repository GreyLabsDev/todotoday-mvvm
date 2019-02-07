package com.greylabs.todotoday.screens.tasks

import androidx.lifecycle.*
import com.greylabs.todotoday.base.ProgressState
import com.greylabs.todotoday.data.DatabaseRepository
import com.greylabs.todotoday.data.TestingRepository
import com.greylabs.todotoday.screens.tasks.data_model.TaskDataModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TasksActivityViewModel(private val testingRepo: DatabaseRepository) : ViewModel(), LifecycleObserver {

    private var text: MutableLiveData<String> = MutableLiveData()
    private var tasks: MutableLiveData<MutableList<TaskDataModel>> = MutableLiveData()
    private var progressState: MutableLiveData<ProgressState> = MutableLiveData()
    private var disposables = CompositeDisposable()

    fun loadData() {
        disposables += Observable.just(testingRepo.getTaskList())
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    progressState.postValue(ProgressState.Loading())
                }
                .subscribe{loadedTasks ->
                    progressState.postValue(ProgressState.Done())
                    tasks.postValue(loadedTasks)
                }
    }

    fun getTasksText(): MutableLiveData<String> {
        return text
    }

    fun getTasks(): MutableLiveData<MutableList<TaskDataModel>> {
        return tasks
    }

    fun getProgressState(): MutableLiveData<ProgressState> {
        return progressState
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun dispose() {
        disposables.dispose()
    }
}