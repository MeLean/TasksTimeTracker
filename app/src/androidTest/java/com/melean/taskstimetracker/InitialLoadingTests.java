package com.melean.taskstimetracker;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.recycler_view_assertions.RecyclerViewItemCountAssertion;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.record_tasks.RecordTaskActivity;
import com.melean.taskstimetracker.record_tasks.RecordTaskFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class InitialLoadingTests {
    private List<EmployeeModel> mFakeEmployeeModels;
    private List<TaskModel> mFakeTaskModels;

    @Rule
    public ActivityTestRule<RecordTaskActivity> mActivityRule =
            new ActivityTestRule<>(RecordTaskActivity.class, true, true);

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Before
    public void initData(){
        mFakeEmployeeModels = new ArrayList<>();
        mFakeTaskModels = new ArrayList<>();

        for (int i = 0; i <3; i++){
            mFakeTaskModels.add(new TaskModel("Task " + i));
            mFakeEmployeeModels.add(new EmployeeModel("Employee " + i));
        }
    }

    @Test
    @LargeTest
    public void CheckStartingViews_WhenHasSomeTasks() throws Throwable {
        uiThreadTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecordTaskActivity activity = mActivityRule.getActivity();
                activity.initFragment();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                RecordTaskFragment fragment =
                        (RecordTaskFragment) fragmentManager.findFragmentByTag(RecordTaskFragment.TAG);
                fragment.showTasksList(mFakeTaskModels);
                fragment.showEmployeesList(mFakeEmployeeModels);
            }
        });

        onView(withId(R.id.tasks_list)).check(matches(isDisplayed()));
        onView(withId(R.id.no_tasks)).check(matches((not(isDisplayed()))));
        onView(withId(R.id.tasks_list))
                .check(new RecyclerViewItemCountAssertion(mFakeTaskModels.size()));
        onView(withId(R.id.employees_list))
                .check(new RecyclerViewItemCountAssertion(mFakeEmployeeModels.size()));
        onView(withId(R.id.tasks_list))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(mFakeTaskModels.size() - 1,click()));
    }
}
