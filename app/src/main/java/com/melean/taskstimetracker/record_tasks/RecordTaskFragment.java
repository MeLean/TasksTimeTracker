package com.melean.taskstimetracker.record_tasks;


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
import com.melean.taskstimetracker.record_tasks.adapters.EmployeeAdapter;
import com.melean.taskstimetracker.record_tasks.adapters.TasksAdapter;

import java.util.List;

public class RecordTaskFragment extends Fragment implements RecordTaskContract.View {
    public static final String TAG = "com.melean.taskstimetracker.recordTasks.recordtaskfragment";
    private RecordTaskPresenter mPresenter;
    private RecyclerView mTasksRecycler, mEmployeesRecycler;
    private TextView mNoTasks, mNoEmployees;

    public RecordTaskFragment() {
        // Required empty public constructor
    }

    public static RecordTaskFragment getNewInstance() {
        return new RecordTaskFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_task, container, false);
        ITaskRepository repository =
                new TaskRepositoryImplementation(new RealmDatabase(getContext()));
        mPresenter = new RecordTaskPresenter(this, repository);
        mTasksRecycler = (RecyclerView) view.findViewById(R.id.tasks_list);
        mEmployeesRecycler = (RecyclerView) view.findViewById(R.id.employees_list);
        mNoTasks = (TextView) view.findViewById(R.id.no_tasks);
        mNoEmployees = (TextView) view.findViewById(R.id.no_employees);
        mPresenter.loadTasks();
        mPresenter.loadEmployees();
        return view;
    }

    @Override
    public void showTasksList(List<TaskModel> tasks) {
        if (tasks.size() > 0) {
            manageRecycler(mTasksRecycler, new TasksAdapter(tasks), mNoTasks);
        }
    }

    @Override
    public void showEmployeesList(List<EmployeeModel> employees) {
        if (employees.size() > 0) {
            manageRecycler(mEmployeesRecycler, new EmployeeAdapter(employees), mNoEmployees);
        }

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

    private void manageRecycler(RecyclerView recyclerView, RecyclerView.Adapter adapter, TextView noItemsView) {

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        noItemsView.setVisibility(View.GONE);

    }
}
