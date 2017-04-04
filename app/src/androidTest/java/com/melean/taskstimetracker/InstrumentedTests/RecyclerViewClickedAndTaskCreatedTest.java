package com.melean.taskstimetracker.InstrumentedTests;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskEntityModel;
import com.melean.taskstimetracker.recycler_view_assertion_utils.RecyclerViewItemCountAssertion;
import com.melean.taskstimetracker.recycler_view_assertion_utils.TestUtils;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.ui.activities.MainScreenActivity;
import com.melean.taskstimetracker.ui.fragments.MainScreenFragment;
import com.melean.taskstimetracker.recycler_view_assertion_utils.TestsFaker;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewClickedAndTaskCreatedTest {
    private List<EmployeeModel> mFakeEmployeeModels;
    private List<TaskModel> mFakeTaskModels;
    private TaskEntityModel mCreatedTaskEntity;

    @Rule
    public ActivityTestRule<MainScreenActivity> mActivityRule =
            new ActivityTestRule<>(MainScreenActivity.class, true, true);

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Before
    public void setUp() {
        mFakeTaskModels = new ArrayList<>();
        mFakeEmployeeModels = new ArrayList<>();
        TestsFaker.fillUpWithFakeData(mFakeTaskModels, mFakeEmployeeModels, 2);
    }

    @Test
    @LargeTest
    public void CreateAndSaveTaskEntity() throws Throwable {
        final MainScreenActivity activity = mActivityRule.getActivity();
        TestUtils.UnlockAndWakeUpDeviceOnUi(activity);

        TestsFaker.launchRecordFragmentFromUi(activity, mFakeTaskModels, mFakeEmployeeModels);

        this.checkIfDisplayedAsExpected(R.id.tasks_list, R.id.no_tasks, mFakeTaskModels);

        this.checkIfDisplayedAsExpected(R.id.employees_list, R.id.no_employees, mFakeEmployeeModels);

        TestUtils.assertSingleSelection(R.id.tasks_list, mFakeTaskModels);

        TestUtils.assertSingleSelection(R.id.employees_list, mFakeEmployeeModels);

        TestUtils.performClickOnViewWithId(R.id.fab_record);

        final long fakeWorkingMilliseconds = 2000L;
        TestUtils.sSimulateWorkingProcessOnUi(fakeWorkingMilliseconds);

        TestUtils.performClickOnViewWithId(R.id.fab_record);

        this.startUiThreadToGetTaskEntity();

        this.checkCreatedTaskEntity(fakeWorkingMilliseconds);
    }

    private void startUiThreadToGetTaskEntity() throws Throwable {
        uiThreadTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainScreenActivity activity = mActivityRule.getActivity();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                MainScreenFragment fragment =
                        (MainScreenFragment) fragmentManager.findFragmentByTag(MainScreenFragment.TAG);
                setTaskEntity(fragment.getTaskModel());
            }
        });
    }

    private void setTaskEntity(TaskEntityModel createdTask) {
        mCreatedTaskEntity = createdTask;
    }

    private void checkCreatedTaskEntity(long fakeWorkingMilliseconds) {
        TestUtils.assertItemHasViewWithText(
                R.id.tasks_list,
                mFakeTaskModels.size() - 1,
                mCreatedTaskEntity.getTaskName()
        );

        TestUtils.assertItemHasViewWithText(
                R.id.employees_list,
                mFakeEmployeeModels.size() - 1,
                mCreatedTaskEntity.getEmployeeName()
        );

        if (mCreatedTaskEntity.isInterrupted()) {
            throw new AssertionFailedError(
                    "Button \"stop\" was clicked in tests and Task entity should be not interrupted!");
        }

        if (mCreatedTaskEntity.getDateAdded() == null) {
            throw new AssertionFailedError("Task entity must have a date yours is null!");
        }


        if (!(mCreatedTaskEntity.getSecondsWorked() > 0)) {
            throw new AssertionFailedError(mCreatedTaskEntity.getSecondsWorked() +
                    " is not valid value for seconds worked");
        }

        long dif = mCreatedTaskEntity.getSecondsWorked() * 1000 - fakeWorkingMilliseconds;
        long maxAcceptableDif = 50;
        if (dif <= -maxAcceptableDif || dif >= maxAcceptableDif) {
            throw new AssertionFailedError(mCreatedTaskEntity.getSecondsWorked() +
                    " is not valid value for seconds worked!");
        }
    }

    private void checkIfDisplayedAsExpected(int recyclerViewId, int noItemsViewId, List mFakeList) {
        TestUtils.assertIsViewDisplayed(recyclerViewId, true);
        TestUtils.assertIsViewDisplayed(noItemsViewId, false);

        onView(withId(recyclerViewId))
                .check(new RecyclerViewItemCountAssertion(mFakeList.size()));
    }
}


