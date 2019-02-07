package com.greylabs.todotoday.screens.tasks.adapter

import com.greylabs.todotoday.screens.tasks.data_model.TaskDataModel

interface ItemClickListener {

    fun onItemClick(task: TaskDataModel)

}