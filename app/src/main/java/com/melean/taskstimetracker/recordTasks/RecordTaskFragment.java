package com.melean.taskstimetracker.recordTasks;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntity;

public class RecordTaskFragment extends Fragment implements RecordTaskContract.View {


    public RecordTaskFragment() {
        // Required empty public constructor
    }


    public static RecordTaskFragment newInstance() {
        return new RecordTaskFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record_task, container, false);
    }

    @Override
    public void showTaskPane() {

    }

    @Override
    public void showErrorRecordIntend(boolean isRecording) {

    }

    @Override
    public void startTimeCounter() {

    }

    @Override
    public void stopTimeCounter() {

    }

    @Override
    public void showTaskNamePiker() {

    }

    @Override
    public void showEmployeePicker() {

    }

    @Override
    public TaskEntity makeTask() {
        return new TaskEntity();
    }

    @Override
    public boolean isTaskInterrupted() {
        return false;
    }
}
