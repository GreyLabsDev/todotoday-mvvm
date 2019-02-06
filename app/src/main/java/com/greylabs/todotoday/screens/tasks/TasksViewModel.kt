package com.greylabs.todotoday.screens.tasks

import androidx.lifecycle.*
import com.greylabs.todotoday.data.TestingRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TasksViewModel(val testingRepo: TestingRepository) : ViewModel(), LifecycleObserver {

    private var text: MutableLiveData<String> = MutableLiveData()
    private var disposables = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun loadData() {
        disposables += Observable.range(1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap {
                    Observable.just(it).delay(1, TimeUnit.SECONDS)
                }
                .startWith(
                    Observable.just(0)
                )
                .subscribe { data ->
                    text.postValue("Text $data")
                }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun dispose() {
        disposables.dispose()
    }

    fun getTasksText(): MutableLiveData<String> {
        return text
    }
}