package com.melean.taskstimetracker.record_tasks.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.melean.taskstimetracker.record_tasks.adapters.BaseRecyclerAdapter;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private BaseRecyclerAdapter.OnRecyclerItemClicked mOnRecyclerItemClicked;

    public BaseViewHolder(View view, BaseRecyclerAdapter.OnRecyclerItemClicked onRecyclerItemClicked) {
        super(view);
        mOnRecyclerItemClicked = onRecyclerItemClicked;
        onBindData(view);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mOnRecyclerItemClicked.onItemClicked(view, getAdapterPosition());
    }

    public abstract void onBindData(View view);
}