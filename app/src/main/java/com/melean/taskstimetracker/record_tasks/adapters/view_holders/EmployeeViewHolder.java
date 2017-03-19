package com.melean.taskstimetracker.record_tasks.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.record_tasks.adapters.BaseRecyclerAdapter;

public class EmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView employeeName;
    private BaseRecyclerAdapter.OnRecyclerItemClicked mOnRecyclerItemClicked;

    public EmployeeViewHolder(View view, BaseRecyclerAdapter.OnRecyclerItemClicked onRecyclerItemClicked) {
        super(view);
        employeeName = (TextView) view.findViewById(R.id.employee_name);
        mOnRecyclerItemClicked = onRecyclerItemClicked;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mOnRecyclerItemClicked.onItemClicked(view, getAdapterPosition());
    }

    public TextView getEmployeeName() {
        return employeeName;
    }
}
