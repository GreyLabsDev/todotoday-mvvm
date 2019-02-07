package com.greylabs.todotoday.screens.tasks.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greylabs.todotoday.R
import com.greylabs.todotoday.ext.inflate
import com.greylabs.todotoday.screens.tasks.data_model.TaskDataModel

class TasksAdapter(val context: Context) : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    var tasksList: MutableList<TaskDataModel> = mutableListOf()
    var itemClickListener: ItemClickListener? = null

    fun addTasks(newTasks: MutableList<TaskDataModel>) {
        tasksList.clear()
        tasksList.addAll(newTasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = parent.inflate(R.layout.item_task)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return TasksViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(tasksList[position])
        holder.setOnClickListener {
            itemClickListener?.onItemClick(tasksList[position])
        }
    }

    inner class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var textTitle: TextView = view.findViewById(R.id.textTaskTitle)

        fun bind(data: TaskDataModel) {
            textTitle.text = data.name
        }

        fun setOnClickListener(callback: () -> Unit) {
            textTitle.setOnClickListener {
                callback()
            }
        }
    }
}