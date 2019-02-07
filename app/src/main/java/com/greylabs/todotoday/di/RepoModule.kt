package com.greylabs.todotoday.di

import androidx.room.Room
import com.greylabs.todotoday.data.TestingRepository
import com.greylabs.todotoday.data.TestingRepositoryImpl
import com.greylabs.todotoday.data.db.AppDatabase
import com.greylabs.todotoday.data.db.DB_NAME
import org.koin.dsl.module.module

val repoModule = module {

    factory<TestingRepository> { TestingRepositoryImpl() }

    single<AppDatabase> { Room.databaseBuilder(get(), AppDatabase::class.java, DB_NAME).build() }

}