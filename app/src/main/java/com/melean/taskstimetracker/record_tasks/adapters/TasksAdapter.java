package com.melean.taskstimetracker.record_tasks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.data.models.TaskModel;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    private List<TaskModel> taskList;

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_task_row, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.taskName.setText(
                taskList.get(position).getTaskName()
        );
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView taskName;
        public TaskViewHolder(View view) {
            super(view);
            taskName = (TextView) view.findViewById(R.id.task_name);
        }
    }

    public TasksAdapter(List<TaskModel> taskList) {
        this.taskList = taskList;
    }
}
