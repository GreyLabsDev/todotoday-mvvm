package com.greylabs.todotoday.app

import android.app.Application
import com.greylabs.todotoday.di.repoModule
import com.greylabs.todotoday.di.appModule
import com.greylabs.todotoday.di.viewModelsModule
import org.koin.android.ext.android.startKoin

class TodoTodayApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, repoModule, viewModelsModule))
    }
}