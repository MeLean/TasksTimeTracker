package com.melean.taskstimetracker.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.melean.taskstimetracker.R
import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.ui.view_holders.EmployeeViewHolder

class EmployeeAdapter(context: Context, items: List<EmployeeModel>) : BaseRecyclerAdapter<EmployeeModel>(context, items) {

    override fun setViewHolder(parent: ViewGroup, onRecyclerItemClicked: BaseRecyclerAdapter.OnRecyclerItemClicked): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_employee_row, parent, false)
        return EmployeeViewHolder(view, onRecyclerItemClicked)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, `val`: EmployeeModel) {
        val empHolder = holder as EmployeeViewHolder
        empHolder.employeeName!!.text = `val`.employeeName
    }
}
