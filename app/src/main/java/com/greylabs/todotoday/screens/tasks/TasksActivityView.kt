package com.greylabs.todotoday.screens.tasks

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.greylabs.todotoday.R
import com.greylabs.todotoday.base.BaseView
import com.greylabs.todotoday.base.ProgressState
import com.greylabs.todotoday.screens.task_detail.startTaskDetailsActivity
import com.greylabs.todotoday.screens.tasks.adapter.TasksAdapter
import com.greylabs.todotoday.screens.tasks.data_model.TaskDataModel
import kotlinx.android.synthetic.main.activity_tasks.*
import org.koin.android.viewmodel.ext.android.viewModel

import java.util.*

class TasksActivityView : AppCompatActivity(), BaseView, TasksActivityNavigator {

    val viewModel: TasksActivityViewModel by viewModel()

    private lateinit var tasksAdapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        lifecycle.addObserver(viewModel)

        initViews()
        initListeners()
        initToolbar()
        initViewModelObserving()
    }

    override fun initToolbar() {}

    override fun initViews() {
        tasksAdapter = TasksAdapter(this@TasksActivityView)
        recyclerTasksList.adapter = tasksAdapter
        recyclerTasksList.layoutManager = LinearLayoutManager(this)
    }

    override fun initListeners() {
        btnOpenTask.setOnClickListener {
            navigateToTaskDescription(UUID.randomUUID())
        }
        btnLoadTasks.setOnClickListener {
            viewModel.loadData()
        }
    }

    override fun initViewModelObserving() {
        viewModel.getProgressState().observe(this, Observer {state ->
            when (state) {
                is ProgressState.Loading -> {
                    TransitionManager.beginDelayedTransition(rootLayout)
                    progressBarContainer.visibility = View.VISIBLE
                }
                is ProgressState.Done -> {
                    TransitionManager.beginDelayedTransition(rootLayout)
                    progressBarContainer.visibility = View.GONE
                }
                else -> {

                }
            }
        })
        viewModel.getTasks().observe(this, Observer {tasks ->
            tasksAdapter.addTasks(tasks)
        })
    }

    fun addNewTaskst(tasks: MutableList<TaskDataModel>) {
        tasksAdapter.addTasks(tasks)
    }

    override fun navigateToTaskDescription(id: UUID) {
        startTaskDetailsActivity(this)
    }

    override fun navigateToInfo() {

    }
}