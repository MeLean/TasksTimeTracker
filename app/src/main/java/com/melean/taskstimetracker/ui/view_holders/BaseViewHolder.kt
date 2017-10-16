package com.melean.taskstimetracker.ui.view_holders

import android.support.v7.widget.RecyclerView
import android.view.View

import com.melean.taskstimetracker.ui.adapters.BaseRecyclerAdapter

abstract class BaseViewHolder(val view: View, private val mOnRecyclerItemClicked: BaseRecyclerAdapter.OnRecyclerItemClicked) : RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
        onBindData(view)
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        mOnRecyclerItemClicked.onItemClicked(view, adapterPosition)
    }

    abstract fun onBindData(view: View)
}