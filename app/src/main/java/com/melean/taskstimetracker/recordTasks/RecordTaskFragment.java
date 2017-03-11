package com.melean.taskstimetracker.recordTasks;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase;
import com.melean.taskstimetracker.data.models.TaskEntityModel;

import java.util.List;

public class RecordTaskFragment extends Fragment implements RecordTaskContract.View {

    public RecordTaskFragment() {
        // Required empty public constructor
    }

    public static RecordTaskFragment newInstance() {
        return new RecordTaskFragment();
    }

    @Override
    public void loadTasksList(List<String> tasksNames) {

    }

    @Override
    public void loadEmployeesList(List<String> employeesNames) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_record_task, container, false);
    }

    @Override
    public void showErrorRecordIntend(boolean isRecording) {

    }

    @Override
    public void toggleTimeCounter(boolean isStarted) {

    }

    @Override
    public TaskEntityModel getTaskModel() {
        return null;
    }


}
