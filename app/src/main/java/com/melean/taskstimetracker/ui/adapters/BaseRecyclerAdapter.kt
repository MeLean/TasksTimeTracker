package com.melean.taskstimetracker.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

abstract class BaseRecyclerAdapter<T>(private val mContext: Context, private var items: List<T>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val onRecyclerItemClicked: OnRecyclerItemClicked
    var lastSelectedView: View? = null
        private set
    private var currentSelectedPosition = -1

    abstract fun setViewHolder(parent: ViewGroup, onRecyclerItemClicked: OnRecyclerItemClicked): RecyclerView.ViewHolder

    abstract fun onBindData(holder: RecyclerView.ViewHolder, `val`: T)

    init {
        onRecyclerItemClicked = onGetRecyclerItemClickListener()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return setViewHolder(parent, onRecyclerItemClicked)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(holder, items!![position])
        holder.itemView.isSelected = position == currentSelectedPosition
        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    fun setItems(incomingItems: ArrayList<T>) {
        items = incomingItems
        this.notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return items!![position]
    }

    interface OnRecyclerItemClicked {
        fun onItemClicked(view: View, position: Int)
    }


    private fun onGetRecyclerItemClickListener(): OnRecyclerItemClicked {
        return object : OnRecyclerItemClicked {
            override fun onItemClicked(view: View, position: Int) {
                currentSelectedPosition = position

                if (lastSelectedView != null) {
                    val lastSelectedPosition = lastSelectedView!!.tag as Int
                    lastSelectedView!!.isSelected = false

                    if (position != lastSelectedPosition) {
                        setViewSelected(view)
                    } else {
                        view.isSelected = false
                        lastSelectedView = null
                    }
                } else {
                    setViewSelected(view)
                }
            }
        }
    }

    private fun setViewSelected(view: View) {
        view.isSelected = true
        lastSelectedView = view
    }
}