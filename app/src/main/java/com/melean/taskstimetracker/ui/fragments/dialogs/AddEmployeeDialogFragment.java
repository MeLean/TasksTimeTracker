package com.melean.taskstimetracker.ui.fragments.dialogs;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melean.taskstimetracker.R;

public class AddEmployeeDialogFragment extends DialogFragment {


    public AddEmployeeDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_fragment_add_employee, container, false);
    }

}
