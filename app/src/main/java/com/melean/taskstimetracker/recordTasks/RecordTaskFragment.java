package com.melean.taskstimetracker.recordTasks;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase;
import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskEntityModel;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.data.repositories.ITaskRepository;
import com.melean.taskstimetracker.data.repositories.TaskRepositoryImplementation;
import com.melean.taskstimetracker.recordTasks.adapters.TasksAdapter;

import java.util.List;

public class RecordTaskFragment extends Fragment implements RecordTaskContract.View {
    private RecyclerView mTasksRecycler;
    private RecordTaskPresenter mPresenter;
    private TextView mNoTasks;
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
        View view =  inflater.inflate(R.layout.fragment_record_task, container, false);
        ITaskRepository repository =
                new TaskRepositoryImplementation(new RealmDatabase(getContext()));
        mPresenter = new RecordTaskPresenter(this, repository);
        mTasksRecycler = (RecyclerView) view.findViewById(R.id.tasks_list);
        mNoTasks = (TextView) view.findViewById(R.id.no_tasks);

        mPresenter.loadTasks();
        //mPresenter.loadEmployees();
        return view;
    }

    @Override
    public void showTasksList(List<TaskModel> tasks) {
        if (tasks.size() > 0){
            mTasksRecycler.setAdapter(new TasksAdapter(tasks));
            mTasksRecycler.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
            mTasksRecycler.setItemAnimator(new DefaultItemAnimator());
            mNoTasks.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmployeesList(List<EmployeeModel> employees) {

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
