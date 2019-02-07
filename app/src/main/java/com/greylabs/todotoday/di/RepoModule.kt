package com.greylabs.todotoday.di

import com.greylabs.todotoday.data.DatabaseRepository
import com.greylabs.todotoday.data.DatabaseRepositoryImpl
import com.greylabs.todotoday.data.TestingRepository
import com.greylabs.todotoday.data.TestingRepositoryImpl
import org.koin.dsl.module.module

val repoModule = module {

    factory<TestingRepository> { TestingRepositoryImpl() }

    single<DatabaseRepository> { DatabaseRepositoryImpl() }

}