package com.greylabs.todotoday.screens.add_task

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.greylabs.todotoday.base.BaseViewModel
import com.greylabs.todotoday.base.ProgressState
import com.greylabs.todotoday.data.db.AppDatabase
import com.greylabs.todotoday.data.db.entity.TaskEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import java.util.*

class AddTaskActivityViewModel(private val appDatabase: AppDatabase) : ViewModel(), BaseViewModel, LifecycleObserver {

    private var disposables = CompositeDisposable()
    private var progressState: MutableLiveData<ProgressState> = MutableLiveData()

    override fun dispose() {
        disposables.dispose()
    }

    fun getProgressState(): MutableLiveData<ProgressState> {
        return progressState
    }

    fun createTask(taskName: String, taskDescription: String) {
        disposables += Observable.fromCallable {
            appDatabase.getTaskDao().insert(
                    TaskEntity(
                            UUID.randomUUID().toString(),
                            taskName,
                            taskDescription,
                            Calendar.getInstance().time.toString()
                    )
            )
        }.doOnSubscribe {
            progressState.postValue(ProgressState.Loading())
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            progressState.postValue(ProgressState.Done())
                        },
                        {error ->
                            progressState.postValue(ProgressState.Error())
                        }
                )
    }

}