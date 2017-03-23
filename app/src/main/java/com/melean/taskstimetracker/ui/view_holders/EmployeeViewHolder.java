package com.melean.taskstimetracker.ui.view_holders;

import android.view.View;
import android.widget.TextView;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.ui.adapters.BaseRecyclerAdapter;

public class EmployeeViewHolder extends BaseViewHolder{
    private TextView employeeName;
    public EmployeeViewHolder(View view, BaseRecyclerAdapter.OnRecyclerItemClicked onRecyclerItemClicked) {
        super(view, onRecyclerItemClicked);
    }

    @Override
    public void onBindData(View view) {
        employeeName = (TextView) view.findViewById(R.id.employee_name);
    }

    public TextView getEmployeeName() {
        return employeeName;
    }
}
