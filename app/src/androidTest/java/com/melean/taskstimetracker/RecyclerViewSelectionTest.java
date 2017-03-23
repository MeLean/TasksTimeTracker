package com.melean.taskstimetracker;

import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.record_tasks.RecordTaskActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewSelectionTest {
    List<EmployeeModel> employees;
    List<TaskModel> tasks;
    @Rule
    public ActivityTestRule<RecordTaskActivity> mActivityRule =
            new ActivityTestRule<>(RecordTaskActivity.class, true, true);

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Before
    public void initData(){
        employees = new ArrayList<>();
        employees.add(new EmployeeModel("Employee 1"));
        employees.add(new EmployeeModel("Employee 2"));
        employees.add(new EmployeeModel("Employee 3"));

    /*    RecyclerViewSelectionTest(mEmployeesRecycler,
                new EmployeeAdapter(getContext(), employees), mNoEmployees);*/

        tasks = new ArrayList<>();
        tasks.add(new TaskModel("Task 1"));
        tasks.add(new TaskModel("Task 2"));
        tasks.add(new TaskModel("Task 3"));

     /*   RecyclerViewSelectionTest(mTasksRecycler,
                new TasksAdapter(getContext(), tasks), mNoTasks);*/
    }


}
