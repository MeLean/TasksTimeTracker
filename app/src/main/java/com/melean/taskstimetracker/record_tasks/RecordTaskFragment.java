package com.melean.taskstimetracker.record_tasks;


import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
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
import com.melean.taskstimetracker.ui.adapters.BaseRecyclerAdapter;
import com.melean.taskstimetracker.ui.adapters.EmployeeAdapter;
import com.melean.taskstimetracker.ui.adapters.TasksAdapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecordTaskFragment extends Fragment implements RecordTaskContract.View {
    public static final String TAG = "com.melean.taskstimetracker.recordTasks.recordtaskfragment";
    private RecordTaskPresenter mPresenter;
    private Chronometer mTimer;
    private RecyclerView mTasksRecycler, mEmployeesRecycler;
    private TextView mNoTasks, mNoEmployees;
    private long mSecondsWorked = 0;
    private boolean isTaskInterrupted = false;

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
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(Preferences.ENTITY_DATE_FORMAT, Locale.getDefault());

        String selectedTaskName =
                getSelectedElementString(mTasksRecycler);

        String selectedEmployeeName =
                getSelectedElementString(mEmployeesRecycler);

        return new TaskEntityModel(
                selectedEmployeeName,
                selectedTaskName,
                mSecondsWorked,
                isTaskInterrupted,
                dateFormat.format(System.currentTimeMillis())
        );
    }

    private String getSelectedElementString(
            RecyclerView recyclerView) {

        BaseRecyclerAdapter adapter = (BaseRecyclerAdapter) recyclerView.getAdapter();
        View view =  adapter.getLastSelectedView();
        int id = -1;
        if (adapter instanceof TasksAdapter){
           id = R.id.task_name;
        }else if(adapter instanceof EmployeeAdapter){
            id = R.id.employee_name;
        }

        TextView name = (TextView) view.findViewById(id);
        String result = null;
        if(name != null){
            result = name.getText().toString();
        }

        return result;
    }

    @Override
    public void onPause() {
        super.onPause();
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

    void toggleRecording(FloatingActionButton pauseBtn, FloatingActionButton fabRecord, boolean isInterrupted) {
        String fabRecordContentDescription = fabRecord.getContentDescription().toString();


        if (getString(R.string.start).equals(fabRecordContentDescription)) {
            startTimer(mTimer);
            pauseBtn.setVisibility(View.VISIBLE);
            fabRecord.setImageResource(android.R.drawable.ic_menu_save);
            fabRecord.setContentDescription(getString(R.string.save));
        } else {
            stopTimer(mTimer);
            pauseBtn.setVisibility(View.GONE);
            fabRecord.setImageResource(android.R.drawable.ic_media_play);
            fabRecord.setContentDescription(getString(R.string.start));
            isTaskInterrupted = isInterrupted;
        }
    }

    private void startTimer(Chronometer timer) {
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
    }

    private void stopTimer(Chronometer timer) {
        timer.stop();
        // convert in second from milliseconds
        mSecondsWorked = (SystemClock.elapsedRealtime() - timer.getBase()) / 1000;
    }
}
