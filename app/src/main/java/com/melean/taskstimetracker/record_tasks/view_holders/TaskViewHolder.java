package com.melean.taskstimetracker.record_tasks.view_holders;

import android.view.View;
import android.widget.TextView;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.record_tasks.adapters.BaseRecyclerAdapter;

public class TaskViewHolder extends BaseViewHolder{
    private TextView taskName;

    public TaskViewHolder(View view, BaseRecyclerAdapter.OnRecyclerItemClicked onRecyclerItemClicked) {
        super(view, onRecyclerItemClicked);
    }

    @Override
    public void onBindData(View view) {
        taskName = (TextView) view.findViewById(R.id.task_name);
    }

    public TextView getTaskName() {
        return taskName;
    }
}
