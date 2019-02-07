package com.greylabs.todotoday.screens.tasks

import java.util.*

interface TasksActivityNavigator {

    fun navigateToTaskDescription(id: UUID)

    fun navigateToInfo()
}