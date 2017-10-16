package com.melean.taskstimetracker.ui.view_holders

import android.view.View
import android.widget.TextView

import com.melean.taskstimetracker.R
import com.melean.taskstimetracker.ui.adapters.BaseRecyclerAdapter

class TaskViewHolder(view: View, onRecyclerItemClicked: BaseRecyclerAdapter.OnRecyclerItemClicked) : BaseViewHolder(view, onRecyclerItemClicked) {
    var taskName: TextView? = null
        private set

    override fun onBindData(view: View) {
        taskName = view.findViewById(R.id.task_name) as TextView
    }
}
