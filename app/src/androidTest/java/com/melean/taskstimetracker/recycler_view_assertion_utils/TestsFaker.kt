package com.melean.taskstimetracker.recycler_view_assertion_utils

import android.support.v4.app.FragmentManager

import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.data.models.TaskModel
import com.melean.taskstimetracker.ui.activities.MainScreenActivity
import com.melean.taskstimetracker.ui.fragments.MainScreenFragment

object TestsFaker {
    fun fillUpWithFakeData(
            fakeTaskModels: MutableList<TaskModel>, fakeEmployeeModels: MutableList<EmployeeModel>, testingRecyclerViewItemsCount: Int) {

        for (i in 0 until testingRecyclerViewItemsCount) {
            val num = i + 1
            fakeTaskModels.add(TaskModel("Task " + num))
            fakeEmployeeModels.add(EmployeeModel("Employee " + num))
        }
    }

    @Throws(Throwable::class)
    fun launchRecordFragmentFromUi(
            activity: MainScreenActivity,
            mFakeTaskModels: MutableList<TaskModel>,
            mFakeEmployeeModels: MutableList<EmployeeModel>) {

        TestUtils.uiThreadRule.runOnUiThread( {
            activity.initFragment()
            val fragmentManager = activity.supportFragmentManager
            val fragment = fragmentManager.findFragmentByTag(MainScreenFragment.TAG) as MainScreenFragment
            fragment.showTasksList(mFakeTaskModels)
            fragment.showEmployeesList(mFakeEmployeeModels)
        })
    }


}
