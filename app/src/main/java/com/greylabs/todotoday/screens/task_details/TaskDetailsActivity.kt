package com.greylabs.todotoday.screens.task_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.greylabs.todotoday.R
import com.greylabs.todotoday.base.BaseView
import com.greylabs.todotoday.base.ProgressState
import com.greylabs.todotoday.screens.task_details.TaskDetailsViewModel
import kotlinx.android.synthetic.main.activity_task_details.*

fun startTaskDetailsActivity(context: Context) {
    val intent = Intent(context, TaskDetailsActivity::class.java)
    context.startActivity(intent)
}

class TaskDetailsActivity : AppCompatActivity(), BaseView {

    private lateinit var viewModel: TaskDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        viewModel = ViewModelProviders.of(this).get(TaskDetailsViewModel::class.java)
        lifecycle.addObserver(viewModel)

        initToolbar()
        initViews()
        initListeners()
        initViewModelObserving()
    }

    override fun initToolbar() {}

    override fun initViews() {}

    override fun initListeners() {
        btnReloadData.setOnClickListener {
            viewModel.loadTaskData()
        }
    }

    override fun initViewModelObserving() {
        viewModel.getProgressState().observe( this, Observer {state ->
            when (state) {
                is ProgressState.Loading -> {
                    TransitionManager.beginDelayedTransition(rootView)
                    progressBarContainer.visibility = View.VISIBLE
                }
                is ProgressState.Done -> {
                    TransitionManager.beginDelayedTransition(rootView)
                    progressBarContainer.visibility = View.GONE
                }
                else -> {
                    TransitionManager.beginDelayedTransition(rootView)
                    progressBarContainer.visibility = View.VISIBLE
                }
            }
        })
        viewModel.getTask().observe(this, Observer {task ->
            TransitionManager.beginDelayedTransition(rootView)
            textTaskName.text = task.name
            textTaskDescription.text = task.description
        })
    }

}