package com.melean.taskstimetracker;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskEntityModel;
import com.melean.taskstimetracker.recycler_view_utils.RecyclerMatcher;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.record_tasks.RecordTaskActivity;
import com.melean.taskstimetracker.record_tasks.RecordTaskFragment;
import com.melean.taskstimetracker.recycler_view_utils.RecyclerViewItemCountAssertion;

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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class InitialLoadingTests {
    private List<EmployeeModel> mFakeEmployeeModels;
    private List<TaskModel> mFakeTaskModels;
    private TaskEntityModel mCreatedTaskEntity;
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
            int num = i + 1;
            mFakeTaskModels.add(new TaskModel("Task " + num));
            mFakeEmployeeModels.add(new EmployeeModel("Employee " + num));
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

        checkIfDisplayedAsExpected(R.id.tasks_list, R.id.no_tasks, mFakeTaskModels);

        checkIfDisplayedAsExpected(R.id.tasks_list, R.id.no_employees, mFakeEmployeeModels);

        checkClicksOnRecyclerViewItems(R.id.tasks_list, mFakeTaskModels);

        checkClicksOnRecyclerViewItems(R.id.employees_list, mFakeEmployeeModels);

        uiThreadTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecordTaskActivity activity = mActivityRule.getActivity();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                RecordTaskFragment fragment =
                        (RecordTaskFragment) fragmentManager.findFragmentByTag(RecordTaskFragment.TAG);
                setTaskEntity(fragment.getTaskModel());
            }
        });

        
        //check task property
        int taskLastTaskPosition = mFakeTaskModels.size() - 1; //last clicked element must be 2
        String expectedTaskName = mCreatedTaskEntity.getTaskName(); //task name must be "Task 3"
        onView(withId(R.id.tasks_list))
            .perform(RecyclerViewActions.scrollToPosition(taskLastTaskPosition))
                .check(matches(
                        RecyclerMatcher.atPosition(
                                taskLastTaskPosition,
                                withText(expectedTaskName)))
        );

        //check employee property
        int employeeLastItemPosition = mFakeEmployeeModels.size() - 1; //last clicked element must be 2
        String expectedEmployeeNameValue = mCreatedTaskEntity.getEmployeeName(); //task name must be "Employee 3"
        onView(withId(R.id.employees_list))
                .perform(RecyclerViewActions.scrollToPosition(employeeLastItemPosition))
                    .check(matches(
                            RecyclerMatcher.atPosition(
                                    employeeLastItemPosition,
                                    withText(expectedEmployeeNameValue)))
        );
    }

    private void checkIfDisplayedAsExpected(int recyclerViewId, int noItemsViewId, List mFakeList) {
        onView(withId(recyclerViewId)).check(matches(isDisplayed()));
        onView(withId(noItemsViewId)).check(matches((not(isDisplayed()))));

        onView(withId(R.id.tasks_list))
                .check(new RecyclerViewItemCountAssertion(mFakeList.size()));
    }

    private void checkClicksOnRecyclerViewItems(int recyclerViewId, List mFakeList) {
        for (int i=0; i < mFakeList.size(); i++){
            performClickAtPosition(recyclerViewId, i);
            if(i == 0){
                //click again in order to deselect item
                performClickAtPosition(recyclerViewId, i);
            }
        }
    }

    private void performClickAtPosition(int recyclerViewId, int position) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(position, click()));
    }

    private void setTaskEntity(TaskEntityModel createdTask){
        mCreatedTaskEntity = createdTask;
    }
}
