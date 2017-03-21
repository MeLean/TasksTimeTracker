package com.melean.taskstimetracker.record_tasks;


import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.constants.Preferences;
import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase;
import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskEntityModel;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.data.repositories.ITaskRepository;
import com.melean.taskstimetracker.data.repositories.TaskRepositoryImplementation;
import com.melean.taskstimetracker.record_tasks.adapters.EmployeeAdapter;
import com.melean.taskstimetracker.record_tasks.adapters.TasksAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecordTaskFragment extends Fragment implements RecordTaskContract.View {
    public static final String TAG = "com.melean.taskstimetracker.recordTasks.recordtaskfragment";
    private RecordTaskPresenter mPresenter;
    private Chronometer mTimer;
    private RecyclerView mTasksRecycler, mEmployeesRecycler;
    private TextView mNoTasks, mNoEmployees;
    private String selectedTaskName, selectedEmployeeName;
    boolean isTaskInterrupted = false;

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
        mTimer = (Chronometer)view.findViewById(R.id.timer);
        mTasksRecycler = (RecyclerView) view.findViewById(R.id.tasks_list);
        mEmployeesRecycler = (RecyclerView) view.findViewById(R.id.employees_list);
        mNoTasks = (TextView) view.findViewById(R.id.no_tasks);
        mNoEmployees = (TextView) view.findViewById(R.id.no_employees);
        mPresenter.loadTasks();
        mPresenter.loadEmployees();

        //todo delete me just testing
        List<EmployeeModel> employees = new ArrayList<>();
        employees.add(new EmployeeModel("Moncho"));
        employees.add(new EmployeeModel("Pencho"));
        employees.add(new EmployeeModel("Gencho"));
        manageRecycler(mEmployeesRecycler,
                new EmployeeAdapter(getContext(), employees), mNoEmployees);

        List<TaskModel> tasks = new ArrayList<>();
        tasks.add(new TaskModel("Task 1"));
        tasks.add(new TaskModel("Task 2"));
        tasks.add(new TaskModel("Task 3"));
        manageRecycler(mTasksRecycler,
                new TasksAdapter(getContext(), tasks), mNoTasks);

        //todo delete me just testing

        return view;
    }

    @Override
    public void showTasksList(List<TaskModel> tasks) {
        if (tasks.size() > 0) {
            manageRecycler(mTasksRecycler,
                    new TasksAdapter(getContext(), tasks), mNoTasks);
        }
    }

    @Override
    public void showEmployeesList(List<EmployeeModel> employees) {
        if (employees.size() > 0) {
            manageRecycler(mEmployeesRecycler,
                    new EmployeeAdapter(getContext(), employees), mNoEmployees);
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
        long timePassed = SystemClock.elapsedRealtime() - mTimer.getBase(); //todo set base when chronometer starts
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(Preferences.ENTITY_DATE_FORMAT, Locale.getDefault());
        //todo implement onClickListeners in recyclers
        return new TaskEntityModel(
                selectedTaskName,
                selectedEmployeeName,
                timePassed,
                isTaskInterrupted,
                dateFormat.format(System.currentTimeMillis())
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        this.setRetainInstance(true);
    }

    private void manageRecycler(RecyclerView recyclerView,
                                RecyclerView.Adapter adapter,
                                TextView noItemsView) {

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext().getApplicationContext())
        );
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        noItemsView.setVisibility(View.GONE);
    }
}
