package com.greylabs.todotoday.screens.tasks.data_model

import java.util.*

data class TaskDataModel(
        var id: UUID,
        var name: String,
        var description: String,
        var addDate: String
)