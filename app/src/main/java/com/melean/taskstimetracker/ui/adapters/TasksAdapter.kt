package com.melean.taskstimetracker.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.melean.taskstimetracker.R
import com.melean.taskstimetracker.data.models.TaskModel
import com.melean.taskstimetracker.ui.view_holders.TaskViewHolder

class TasksAdapter(context: Context, items: List<TaskModel>) : BaseRecyclerAdapter<TaskModel>(context, items) {

    override fun setViewHolder(parent: ViewGroup, onRecyclerItemClicked: BaseRecyclerAdapter.OnRecyclerItemClicked): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_task_row, parent, false)
        return TaskViewHolder(view, onRecyclerItemClicked)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, `val`: TaskModel) {
        val taskHolder = holder as TaskViewHolder
        taskHolder.taskName!!.text = `val`.taskName
    }
}
