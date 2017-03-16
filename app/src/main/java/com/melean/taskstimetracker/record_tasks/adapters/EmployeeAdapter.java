package com.melean.taskstimetracker.record_tasks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.data.models.EmployeeModel;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<EmployeeModel> employeeList;

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_employee_row, parent, false);

        return new EmployeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        holder.employeeName.setText(
                employeeList.get(position).getEmployeeName()
        );
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        public TextView employeeName;
        public EmployeeViewHolder(View view) {
            super(view);
            employeeName = (TextView) view.findViewById(R.id.employee_name);
        }
    }

    public EmployeeAdapter(List<EmployeeModel> employeeList) {
        this.employeeList = employeeList;
    }
}
