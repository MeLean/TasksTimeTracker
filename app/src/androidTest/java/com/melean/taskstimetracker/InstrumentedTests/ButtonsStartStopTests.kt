package com.melean.taskstimetracker.InstrumentedTests

import android.content.res.Resources
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import com.melean.taskstimetracker.R
import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.data.models.TaskModel
import com.melean.taskstimetracker.ui.activities.MainScreenActivity
import com.melean.taskstimetracker.recycler_view_assertion_utils.TestUtils
import com.melean.taskstimetracker.recycler_view_assertion_utils.TestsFaker


import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import java.util.ArrayList

import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.not

@RunWith(AndroidJUnit4::class)
class ButtonsStartStopTests {
    private var res: Resources? = null

    @Rule
    var mActivityRule = ActivityTestRule(MainScreenActivity::class.java)

    @Before
    fun setUp() {
        res = mActivityRule.activity.resources
    }

    @Test
    @LargeTest
    @Throws(Throwable::class)
    fun clickStartRecord_PauseButtonAndStartStopToggle() {
        val activity = mActivityRule.activity
        val mFakeTaskModels = ArrayList<TaskModel>()
        val mFakeEmployeeModels = ArrayList<EmployeeModel>()

        TestsFaker.fillUpWithFakeData(mFakeTaskModels, mFakeEmployeeModels, 2)
        TestsFaker.launchRecordFragmentFromUi(
                activity,
                mFakeTaskModels,
                mFakeEmployeeModels
        )

        TestUtils.performClickAtPosition(R.id.tasks_list, 0)
        TestUtils.performClickAtPosition(R.id.employees_list, 0)

        TestUtils.UnlockAndWakeUpDeviceOnUi(activity)
        //first click should show pause button and change image to save on record button
        TestUtils.performClickOnViewWithId(R.id.fab_record)
        TestUtils.assertIsViewDisplayed(R.id.fab_pause, true)
        TestUtils.assertIsViewDisplayed(res!!.getString(R.string.save), true)

        //second click should remove pause button and change image to play on record button
        TestUtils.performClickOnViewWithId(R.id.fab_record)
        TestUtils.assertIsViewDisplayed(R.id.fab_pause, false)
        TestUtils.assertIsViewDisplayed(res!!.getString(R.string.start), true)

        TestUtils.performClickOnViewWithId(R.id.fab_record)
        TestUtils.performClickOnViewWithId(R.id.fab_pause)
        TestUtils.assertIsViewDisplayed(res!!.getString(R.string.start), true)
    }
}
