package com.melean.taskstimetracker.recycler_view_assertion_utils;

import android.support.v4.app.FragmentManager;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.ui.activities.MainScreenActivity;
import com.melean.taskstimetracker.ui.fragments.MainScreenFragment;

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
            final MainScreenActivity activity,
            final List<TaskModel> mFakeTaskModels,
            final List<EmployeeModel> mFakeEmployeeModels) throws Throwable {

        TestUtils.getUiThreadRule().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.initFragment();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                MainScreenFragment fragment =
                        (MainScreenFragment) fragmentManager.findFragmentByTag(MainScreenFragment.TAG);
                fragment.showTasksList(mFakeTaskModels);
                fragment.showEmployeesList(mFakeEmployeeModels);
            }
        });
    }


}
