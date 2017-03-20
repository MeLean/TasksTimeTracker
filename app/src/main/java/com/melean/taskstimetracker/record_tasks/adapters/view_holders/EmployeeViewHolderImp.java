package com.melean.taskstimetracker.record_tasks.adapters.view_holders;

import android.view.View;
import android.widget.TextView;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.record_tasks.adapters.BaseRecyclerAdapter;

public class EmployeeViewHolderImp extends BaseViewHolder{
    private TextView employeeName;
    public EmployeeViewHolderImp(View view, BaseRecyclerAdapter.OnRecyclerItemClicked onRecyclerItemClicked) {
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
