package com.melean.taskstimetracker;


import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.preference.CheckBoxPreference;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;

import com.melean.taskstimetracker.Utils.RecyclerViewItemCountAssertion;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.recordTasks.RecordTaskActivity;
import com.melean.taskstimetracker.recordTasks.RecordTaskFragment;

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
    private List<TaskModel> mEmptyTaskModels;
    private List<TaskModel> mFakeTaskModels;
    private RecordTaskFragment mTestViewFragment;

    @Rule
    public ActivityTestRule<RecordTaskActivity> mActivityRule =
            new ActivityTestRule<>(RecordTaskActivity.class, true, true);

    @Before
    public void initData(){
        mEmptyTaskModels = new ArrayList<>();
        mFakeTaskModels = new ArrayList<>();

        for (int i = 0; i <3; i++){
            mFakeTaskModels.add(new TaskModel("Task " + i));
        }
    }

/*    @Test
    @LargeTest
    public void CheckStartingViews_WhenNoTask(){
        this.prepareTestedViews();
        mTestViewFragment.showTasksList(mEmptyTaskModels);
        onView(withId(R.id.no_tasks)).check(matches(isDisplayed()));
        onView(withId(R.id.tasks_list))
                .check(new RecyclerViewItemCountAssertion(mEmptyTaskModels.size()));
    }*/

    @Test
    @LargeTest
    public void CheckStartingViews_WhenHasSomeTasks() throws Throwable {
        final RecordTaskActivity activity = mActivityRule.getActivity();

            mActivityRule.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.initFragment();
                    mTestViewFragment = (RecordTaskFragment) activity.getSupportFragmentManager()
                            .findFragmentByTag(RecordTaskFragment.TAG);
                }
            });

        mTestViewFragment.showTasksList(mFakeTaskModels);

        onView(withId(R.id.no_tasks)).check(matches((not(isDisplayed()))));
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition(6,click()));
    }
}
