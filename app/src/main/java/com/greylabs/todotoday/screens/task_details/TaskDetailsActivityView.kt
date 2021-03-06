package com.greylabs.todotoday.screens.task_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.greylabs.todotoday.R
import com.greylabs.todotoday.base.BaseView
import com.greylabs.todotoday.base.ProgressState
import com.greylabs.todotoday.screens.task_details.TaskDetailsActivityViewModel
import kotlinx.android.synthetic.main.activity_task_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

private const val extraTaskId = "task_id"

fun startTaskDetailsActivity(context: Context, taskId: UUID? = null) {
    val intent = Intent(context, TaskDetailsActivity::class.java)
    taskId?.let {
        intent.putExtra(extraTaskId, taskId.toString())
    }
    context.startActivity(intent)
}

class TaskDetailsActivity : AppCompatActivity(), BaseView {

    val viewModel: TaskDetailsActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        lifecycle.addObserver(viewModel)

        initToolbar()
        initViews()
        initListeners()
        initViewModelObserving()

        if (intent.hasExtra(extraTaskId)) {
            viewModel.loadTask(intent.getStringExtra(extraTaskId))
        }
    }

    override fun initToolbar() {}

    override fun initViews() {}

    override fun initListeners() {
        btnDeleteTask.setOnClickListener {
            viewModel.deleteTask()
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
                is ProgressState.Finish -> {
                    finish()
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