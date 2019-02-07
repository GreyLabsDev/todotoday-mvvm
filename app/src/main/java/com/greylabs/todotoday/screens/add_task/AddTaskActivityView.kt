package com.greylabs.todotoday.screens.add_task

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.transition.TransitionManager
import com.greylabs.todotoday.R
import com.greylabs.todotoday.base.BaseView
import com.greylabs.todotoday.base.ProgressState
import kotlinx.android.synthetic.main.activity_add_task.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*



fun startAddTaskActivity(context: Context) {
    val intent = Intent(context, AddTaskActivityView::class.java)

    context.startActivity(intent)
}

class AddTaskActivityView: AppCompatActivity(), BaseView, AddTaskActivityNavigator {


    val viewModel: AddTaskActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        lifecycle.addObserver(viewModel)



        initToolbar()
        initViews()
        initListeners()
        initViewModelObserving()
    }

    override fun initToolbar() {
    }

    override fun initViews() {
    }

    override fun initListeners() {
        btnCreateTask.setOnClickListener {
            val taskName = editTextTaskName.text.toString()
            val taskDescription = editTextTaskDescription.text.toString()
            if (taskName != "" && taskDescription != "") {
                viewModel.createTask(taskName, taskDescription)
            }
        }
    }

    override fun initViewModelObserving() {
        viewModel.getProgressState().observe(this, Observer {state ->
            when (state) {
                is ProgressState.Loading -> {
                    TransitionManager.beginDelayedTransition(rootView)
                    progressBarContainer.visibility = View.VISIBLE
                }
                is ProgressState.Done -> {
                    TransitionManager.beginDelayedTransition(rootView)
                    progressBarContainer.visibility = View.GONE
                    finish()
                }
                else -> {
                    TransitionManager.beginDelayedTransition(rootView)
                    progressBarContainer.visibility = View.GONE
                }
            }
        })
    }
}