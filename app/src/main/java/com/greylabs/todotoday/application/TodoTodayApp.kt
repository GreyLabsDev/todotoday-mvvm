package com.greylabs.todotoday.application

import android.app.Application
import com.greylabs.todotoday.di.appModule
import org.koin.android.ext.android.startKoin

class TodoTodayApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}