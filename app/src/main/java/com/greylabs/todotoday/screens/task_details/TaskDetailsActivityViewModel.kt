package com.greylabs.todotoday.screens.task_details

import androidx.lifecycle.*
import com.greylabs.todotoday.base.BaseViewModel
import com.greylabs.todotoday.base.ProgressState
import com.greylabs.todotoday.data.AppPrefs
import com.greylabs.todotoday.data.TestingRepository
import com.greylabs.todotoday.data.db.AppDatabase
import com.greylabs.todotoday.screens.tasks.data_model.TaskDataModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.getKoin
import org.koin.standalone.inject
import java.util.*
import java.util.concurrent.TimeUnit

class TaskDetailsActivityViewModel(private val appDatabase: AppDatabase,
                                   private val prefs: AppPrefs): ViewModel(), LifecycleObserver, BaseViewModel, KoinComponent {

    private var task: MutableLiveData<TaskDataModel> = MutableLiveData()
    private var progressState: MutableLiveData<ProgressState> = MutableLiveData()

    private var disposables = CompositeDisposable()

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

    fun loadTask(taskId: String) {
        disposables += Observable.fromCallable {
            appDatabase.getTaskDao().getById(taskId)
        }.doOnSubscribe {
            progressState.postValue(ProgressState.Loading())
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {taskFromDb ->
                            progressState.postValue(ProgressState.Done())
                            task.postValue(
                                    TaskDataModel(
                                            UUID.fromString(taskFromDb.id),
                                            taskFromDb.name,
                                            taskFromDb.description,
                                            taskFromDb.addDate
                                    )
                            )
                        },
                        {error ->
                            progressState.postValue(ProgressState.Error())
                        }
                )
    }

    fun deleteTask() {
        task.value?.name?.let {taskName ->
            disposables += Observable.fromCallable {
                appDatabase.getTaskDao().deleteByName(taskName)
            }.doOnSubscribe {
                progressState.postValue(ProgressState.Loading())
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                progressState.postValue(ProgressState.Finish())
                            },
                            {error ->
                                progressState.postValue(ProgressState.Error())
                            }
                    )
        }

    }

}