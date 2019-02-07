package com.greylabs.todotoday.di

import com.greylabs.todotoday.screens.add_task.AddTaskActivityViewModel
import com.greylabs.todotoday.screens.task_details.TaskDetailsActivityViewModel
import com.greylabs.todotoday.screens.tasks.TasksActivityViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelsModule = module {

    viewModel { TasksActivityViewModel(get()) }

    viewModel { TaskDetailsActivityViewModel(get(), get()) }

    viewModel { AddTaskActivityViewModel(get()) }
}