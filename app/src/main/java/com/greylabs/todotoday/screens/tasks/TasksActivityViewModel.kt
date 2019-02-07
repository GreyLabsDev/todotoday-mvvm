package com.greylabs.todotoday.screens.tasks

import androidx.lifecycle.*
import com.greylabs.todotoday.base.ProgressState
import com.greylabs.todotoday.data.TestingRepository
import com.greylabs.todotoday.data.db.AppDatabase
import com.greylabs.todotoday.screens.tasks.data_model.TaskDataModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class TasksActivityViewModel(private val appDatabase: AppDatabase) : ViewModel(), LifecycleObserver {

    private var text: MutableLiveData<String> = MutableLiveData()
    private var tasks: MutableLiveData<MutableList<TaskDataModel>> = MutableLiveData()
    private var progressState: MutableLiveData<ProgressState> = MutableLiveData()
    private var disposables = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadData() {
        disposables += Observable.fromCallable {
            appDatabase.getTaskDao().getAll()
        }.doOnSubscribe {
            progressState.postValue(ProgressState.Loading())
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {tasksFromDb ->
                            tasks.postValue(tasksFromDb.map {
                                TaskDataModel(
                                        UUID.fromString(it.id),
                                        it.name,
                                        it.description,
                                        it.addDate
                                )
                            }.toMutableList())
                            progressState.postValue(ProgressState.Done())
                        },
                        {error ->
                            progressState.postValue(ProgressState.Error())
                        }
                )
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