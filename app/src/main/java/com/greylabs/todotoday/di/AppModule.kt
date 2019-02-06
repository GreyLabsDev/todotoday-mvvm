package com.greylabs.todotoday.di

import com.greylabs.todotoday.data.TestingRepository
import com.greylabs.todotoday.data.TestingRepositoryImpl
import com.greylabs.todotoday.screens.task_details.TaskDetailsViewModel
import com.greylabs.todotoday.screens.tasks.TasksViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {

    single<TestingRepository> { TestingRepositoryImpl() }

    viewModel { TasksViewModel(get()) }

    viewModel { TaskDetailsViewModel() }
}