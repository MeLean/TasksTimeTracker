package com.melean.taskstimetracker.record_tasks.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.record_tasks.adapters.BaseRecyclerAdapter;

public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView taskName;
    private BaseRecyclerAdapter.OnRecyclerItemClicked mOnRecyclerItemClicked;

    public TaskViewHolder(View view, BaseRecyclerAdapter.OnRecyclerItemClicked onRecyclerItemClicked) {
        super(view);
        taskName = (TextView) view.findViewById(R.id.task_name);
        mOnRecyclerItemClicked = onRecyclerItemClicked;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mOnRecyclerItemClicked.onItemClicked(view, getAdapterPosition());
    }

    public TextView getTaskName() {
        return taskName;
    }
}
