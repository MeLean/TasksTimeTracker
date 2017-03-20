package com.melean.taskstimetracker.record_tasks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.record_tasks.view_holders.EmployeeViewHolder;

import java.util.List;

public class EmployeeAdapter extends BaseRecyclerAdapter<EmployeeModel>{
    public EmployeeAdapter(Context context, List<EmployeeModel> items, RecyclerView employeesRecycler) {
        super(context, items, employeesRecycler);
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent, OnRecyclerItemClicked onRecyclerItemClicked) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_employee_row, parent, false);
        return new EmployeeViewHolder(view, onRecyclerItemClicked);
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder holder, EmployeeModel val) {
        EmployeeViewHolder empHolder = (EmployeeViewHolder)holder;
        empHolder.getEmployeeName().setText(val.getEmployeeName());
    }
}
