package com.greylabs.todotoday.screens.tasks

import android.os.Bundle
import android.transition.TransitionManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.greylabs.todotoday.R
import com.greylabs.todotoday.base.BaseView
import com.greylabs.todotoday.screens.task_detail.startTaskDetailsActivity
import kotlinx.android.synthetic.main.activity_tasks.*
import java.util.*

class TasksActivity : AppCompatActivity(), BaseView, TasksNavigator {


    lateinit var viewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        viewModel = ViewModelProviders.of(this).get(TasksViewModel::class.java)
        lifecycle.addObserver(viewModel)

        initViews()
        initListeners()
        initToolbar()
        initViewModelObserving()
    }

    override fun initToolbar() {}

    override fun initViews() {}

    override fun initListeners() {
        btnStartTimer.setOnClickListener {
            viewModel.loadData()
        }
        btnOpenTask.setOnClickListener {
            navigateToTaskDescription(UUID.randomUUID())
        }
    }

    override fun initViewModelObserving() {
        viewModel.getTasksText().observe(this, Observer {text ->
            TransitionManager.beginDelayedTransition(rootLayout)
            testText.text = text
        })
    }

    override fun navigateToTaskDescription(id: UUID) {
        startTaskDetailsActivity(this)
    }

    override fun navigateToInfo() {

    }
}