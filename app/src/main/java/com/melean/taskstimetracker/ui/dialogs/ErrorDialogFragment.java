package com.melean.taskstimetracker.ui.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.melean.taskstimetracker.R;

public class ErrorDialogFragment extends DialogFragment {
    public static final String TAG = "com.melean.taskstimetracker.ui.dialogs.ErrorDialogFragment";
    private static final String ARG_MESSAGE = "com.melean.taskstimetracker.ui.dialogs.message";

    private String mMessage;

    public ErrorDialogFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ErrorDialogFragment newInstance(String message) {
        ErrorDialogFragment fragment = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMessage = getArguments().getString(ARG_MESSAGE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity =  getActivity();
        View view = activity.getLayoutInflater()
                .inflate(R.layout.dialog_fragment_error, new LinearLayout(activity), false);

        // Set values
        ((TextView) view.findViewById(R.id.tw_error_dialog_message)).setText(mMessage);
        (view.findViewById(R.id.btn_error_dialog_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        // Build dialog
        Dialog builder = new Dialog(activity);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setContentView(view);
        builder.setCancelable(true);
        return builder;
    }

}
