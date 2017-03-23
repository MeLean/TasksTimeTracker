package com.melean.taskstimetracker;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.view.WindowManager;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskEntityModel;
import com.melean.taskstimetracker.recycler_view_assertion_utils.RecyclerUtils;
import com.melean.taskstimetracker.recycler_view_assertion_utils.RecyclerViewMatcher;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.record_tasks.RecordTaskActivity;
import com.melean.taskstimetracker.record_tasks.RecordTaskFragment;
import com.melean.taskstimetracker.recycler_view_assertion_utils.RecyclerViewItemCountAssertion;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewClickedAndTaskCreatedTest {
    private List<EmployeeModel> mFakeEmployeeModels;
    private List<TaskModel> mFakeTaskModels;
    private TaskEntityModel mCreatedTaskEntity;


    @Rule
    public ActivityTestRule<RecordTaskActivity> mActivityRule =
            new ActivityTestRule<>(RecordTaskActivity.class, true, true);

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Before
    public void setUp(){
        mFakeEmployeeModels = new ArrayList<>();
        mFakeTaskModels = new ArrayList<>();
        //make fake
        int testingRecyclerViewItemsCount = 12;
        for (int i = 0; i <testingRecyclerViewItemsCount; i++){
            int num = i + 1;
            mFakeTaskModels.add(new TaskModel("Task " + num));
            mFakeEmployeeModels.add(new EmployeeModel("Employee " + num));
        }
    }

    @Test
    @LargeTest
    public void CreateAndSaveTaskEntity() throws Throwable {

        uiThreadTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecordTaskActivity activity = mActivityRule.getActivity();
                //unlock and wake device
                activity.getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

                activity.initFragment();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                RecordTaskFragment fragment =
                        (RecordTaskFragment) fragmentManager.findFragmentByTag(RecordTaskFragment.TAG);
                fragment.showTasksList(mFakeTaskModels);
                fragment.showEmployeesList(mFakeEmployeeModels);
            }
        });

        RecyclerUtils.checkIfDisplayedAsExpected(R.id.tasks_list, R.id.no_tasks, mFakeTaskModels);

        RecyclerUtils.checkIfDisplayedAsExpected(R.id.employees_list, R.id.no_employees, mFakeEmployeeModels);

        performClicksOnRecyclerViewItem(R.id.tasks_list, mFakeTaskModels);

        performClicksOnRecyclerViewItem(R.id.employees_list, mFakeEmployeeModels);

        onView(withId(R.id.fab_record)).perform(click());

        final long fakeWorkingMilliseconds = 2000L;
        uiThreadTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(fakeWorkingMilliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        onView(withId(R.id.fab_record)).perform(click());

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

        checkCreatedTaskEntity(fakeWorkingMilliseconds);
    }

    private void checkCreatedTaskEntity(long fakeWorkingMilliseconds) {
        RecyclerUtils.checkRecyclerViewItemHasViewWithText(
                R.id.tasks_list,
                mFakeTaskModels.size() - 1,
                mCreatedTaskEntity.getTaskName()
        );

        RecyclerUtils.checkRecyclerViewItemHasViewWithText(
                R.id.employees_list,
                mFakeEmployeeModels.size() - 1,
                mCreatedTaskEntity.getEmployeeName()
        );

        if(mCreatedTaskEntity.isInterrupted()){
            throw new AssertionFailedError(
                    "Button \"stop\" was clicked in tests and Task entity should be not interrupted!");
        }

        if(mCreatedTaskEntity.getDateAdded() == null){
            throw new AssertionFailedError("Task entity must have a date yours is null!");
        }


        if(!(mCreatedTaskEntity.getSecondsWorked() > 0)){
            throw new AssertionFailedError(mCreatedTaskEntity.getSecondsWorked() +
                            " is not valid value for seconds worked");
        }

        long dif =  mCreatedTaskEntity.getSecondsWorked() * 1000 - fakeWorkingMilliseconds;
        long maxAcceptableDif = 50;
        if(dif <= -maxAcceptableDif || dif >= maxAcceptableDif){
            throw new AssertionFailedError(mCreatedTaskEntity.getSecondsWorked() +
                    " is not valid value for seconds worked!");
        }
    }

    private void performClicksOnRecyclerViewItem(int recyclerViewId, List mFakeList) {
        for (int i = 0; i < mFakeList.size(); i++){
            RecyclerUtils.performClickAtPosition(recyclerViewId, i);
            RecyclerUtils.assertViewAtPositionIsSelected(recyclerViewId, i, true);
            if(i == 0){
                //click again in order to deselect item
                RecyclerUtils.performClickAtPosition(recyclerViewId, i);
                RecyclerUtils.assertViewAtPositionIsSelected(recyclerViewId, i, false);
            }
        }

        for (int j = 0; j < mFakeList.size(); j++){
            boolean isSelected = false;
            if (j == mFakeList.size() - 1){
                isSelected = true;
            }

            RecyclerUtils.assertViewAtPositionIsSelected(recyclerViewId, j, isSelected);
        }
    }

    private void setTaskEntity(TaskEntityModel createdTask){
        mCreatedTaskEntity = createdTask;
    }

}


