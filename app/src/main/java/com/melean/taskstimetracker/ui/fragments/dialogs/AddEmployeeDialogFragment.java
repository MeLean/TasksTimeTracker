package com.melean.taskstimetracker.ui.fragments.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.ui.interfaces.AddDataContract;


public class AddEmployeeDialogFragment extends DialogFragment implements View.OnClickListener{
    public static final String TAG = "com.melean.taskstimetracker.ui.fragments.dialogs.add_employee_fragment";
    private EditText mDataInput;
    public AddEmployeeDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_fragment_add_employee, container, false);
        mDataInput = (EditText) view.findViewById(R.id.et_data_input);
        view.findViewById(R.id.btn_data_save).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_data_save){
            ((AddDataContract.View)getParentFragment()).onDataEntered(
                    new EmployeeModel(mDataInput.getText().toString())
            );
        }
    }
}
