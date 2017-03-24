package com.melean.taskstimetracker.recycler_view_assertion_utils;

import android.support.test.rule.UiThreadTestRule;
import android.support.v4.app.FragmentManager;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.record_tasks.RecordTaskActivity;
import com.melean.taskstimetracker.record_tasks.RecordTaskFragment;

import java.util.List;

public class TestsFaker {
    public static void fillUpWithFakeData(
            List<TaskModel> fakeTaskModels, List<EmployeeModel> fakeEmployeeModels, int testingRecyclerViewItemsCount) {

        for (int i = 0; i < testingRecyclerViewItemsCount; i++) {
            int num = i + 1;
            fakeTaskModels.add(new TaskModel("Task " + num));
            fakeEmployeeModels.add(new EmployeeModel("Employee " + num));
        }
    }

    public static void launchRecordFragmentFromUi(
            final RecordTaskActivity activity,
            final List<TaskModel> mFakeTaskModels,
            final List<EmployeeModel> mFakeEmployeeModels) throws Throwable {

        TestUtils.getUiThreadRule().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.initFragment();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                RecordTaskFragment fragment =
                        (RecordTaskFragment) fragmentManager.findFragmentByTag(RecordTaskFragment.TAG);
                fragment.showTasksList(mFakeTaskModels);
                fragment.showEmployeesList(mFakeEmployeeModels);
            }
        });
    }


}
