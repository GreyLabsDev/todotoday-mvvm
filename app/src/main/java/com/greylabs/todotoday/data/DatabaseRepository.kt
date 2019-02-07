package com.greylabs.todotoday.data

import com.greylabs.todotoday.screens.tasks.data_model.TaskDataModel
import java.util.*

interface DatabaseRepository {
    fun getTaskList(): MutableList<TaskDataModel>
}

class DatabaseRepositoryImpl : DatabaseRepository {
    override fun getTaskList(): MutableList<TaskDataModel> {
        val tasks: MutableList<TaskDataModel> = mutableListOf()
        for (i in 0..5) {
            tasks.add(TaskDataModel(
                    UUID.randomUUID(),
                    "Working task #$i",
                    "Working task description",
                    Calendar.getInstance().time.toString()
            ))
        }
        return tasks
    }
}