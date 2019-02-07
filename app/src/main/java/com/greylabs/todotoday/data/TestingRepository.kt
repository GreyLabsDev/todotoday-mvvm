package com.greylabs.todotoday.data

import com.greylabs.todotoday.screens.tasks.data_model.TaskDataModel
import java.util.*

interface TestingRepository {
    fun getTaskList(): MutableList<TaskDataModel>
}

class TestingRepositoryImpl: TestingRepository {

    override fun getTaskList(): MutableList<TaskDataModel> {
        val tasks: MutableList<TaskDataModel> = mutableListOf()
        for (i in 0..5) {
            tasks.add(TaskDataModel(
                    UUID.randomUUID(),
                    "Test task #$i",
                    "Test task description",
                    Calendar.getInstance().time.toString()
            ))
        }
        return tasks
    }
}