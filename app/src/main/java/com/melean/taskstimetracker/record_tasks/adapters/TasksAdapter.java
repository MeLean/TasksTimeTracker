package com.melean.taskstimetracker.record_tasks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.record_tasks.view_holders.TaskViewHolder;

import java.util.List;

public class TasksAdapter extends BaseRecyclerAdapter<TaskModel> {

    public TasksAdapter(Context context, List<TaskModel> items, RecyclerView tasksRecycler) {
        super(context, items, tasksRecycler);
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent, BaseRecyclerAdapter.OnRecyclerItemClicked onRecyclerItemClicked) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_task_row, parent, false);
        return new TaskViewHolder(view, onRecyclerItemClicked);
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder holder, TaskModel val) {
        TaskViewHolder taskHolder = (TaskViewHolder)holder;
        taskHolder.getTaskName().setText(val.getTaskName());
    }
}
