package com.greylabs.todotoday.di

import com.greylabs.todotoday.data.AppPrefs
import org.koin.dsl.module.module

val prefsModule = module {

    single { AppPrefs(get()) }

}