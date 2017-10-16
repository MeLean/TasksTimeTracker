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


@RunWith(AndroidJUnit4::class)
class ShowApplicationErrorFromViewTest {
    private var mFakeEmployeeModels: List<EmployeeModel>? = null
    private var mFakeTaskModels: List<TaskModel>? = null
    private var mResources: Resources? = null
    @Rule
    var mActivityRule = ActivityTestRule(MainScreenActivity::class.java, true, true)

    @Before
    fun setUp() {
        mResources = mActivityRule.activity.resources
    }

    @Test
    @LargeTest
    @Throws(Throwable::class)
    fun ShowingErrors() {
        TestUtils.UnlockAndWakeUpDeviceOnUi(mActivityRule.activity)

        TestUtils.assertIsViewDisplayed(R.id.no_tasks, true)

        TestUtils.performClickOnViewWithId(R.id.fab_record)

        TestUtils.assertIsViewDisplayed(R.id.noting_selected_error, true)

        assertErrorTextShown(R.id.noting_selected_error, mResources!!.getString(R.string.error_not_full_selection))

        this.intFakeDataOnUi()

        TestUtils.performClickAtPosition(R.id.tasks_list, mFakeTaskModels!!.size - 1)
        TestUtils.performClickOnViewWithId(R.id.fab_record)
        assertErrorTextShown(R.id.noting_selected_error, mResources!!.getString(R.string.error_not_full_selection))


        TestUtils.performClickAtPosition(R.id.tasks_list, mFakeTaskModels!!.size - 1)
        TestUtils.performClickAtPosition(R.id.employees_list, mFakeEmployeeModels!!.size - 1)
        TestUtils.performClickOnViewWithId(R.id.fab_record)
        assertErrorTextShown(R.id.noting_selected_error, mResources!!.getString(R.string.error_not_full_selection))
    }

    fun assertErrorTextShown(viewId: Int, textToShow: String) {
        TestUtils.assertThatViewHasDescendantWithText(viewId, textToShow)
        TestUtils.performClickOnViewWithId(R.id.btn_error_dialog_ok)
    }

    @Throws(Throwable::class)
    fun intFakeDataOnUi() {
        mFakeTaskModels = mutableListOf()
        mFakeEmployeeModels = mutableListOf()
        TestsFaker.fillUpWithFakeData(mFakeTaskModels as MutableList<TaskModel>, mFakeEmployeeModels as MutableList<EmployeeModel>, 2)
        TestsFaker.launchRecordFragmentFromUi(
                mActivityRule.activity,
                mFakeTaskModels as MutableList<TaskModel>,
                mFakeEmployeeModels as MutableList<EmployeeModel>
        )
    }
}
