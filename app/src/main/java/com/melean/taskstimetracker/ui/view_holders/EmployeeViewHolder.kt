package com.melean.taskstimetracker.ui.view_holders

import android.view.View
import android.widget.TextView

import com.melean.taskstimetracker.R
import com.melean.taskstimetracker.ui.adapters.BaseRecyclerAdapter

class EmployeeViewHolder(view: View, onRecyclerItemClicked: BaseRecyclerAdapter.OnRecyclerItemClicked) : BaseViewHolder(view, onRecyclerItemClicked) {
    var employeeName: TextView? = null
        private set

    override fun onBindData(view: View) {
        employeeName = view.findViewById(R.id.employee_name) as TextView
    }
}
