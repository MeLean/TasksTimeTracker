package com.melean.taskstimetracker.InstrumentedTests

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.app.FragmentManager

import com.melean.taskstimetracker.R
import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.data.models.TaskEntityModel
import com.melean.taskstimetracker.recycler_view_assertion_utils.RecyclerViewItemCountAssertion
import com.melean.taskstimetracker.recycler_view_assertion_utils.TestUtils
import com.melean.taskstimetracker.data.models.TaskModel
import com.melean.taskstimetracker.ui.activities.MainScreenActivity
import com.melean.taskstimetracker.ui.fragments.MainScreenFragment
import com.melean.taskstimetracker.recycler_view_assertion_utils.TestsFaker

import junit.framework.AssertionFailedError

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import java.util.ArrayList

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.matcher.ViewMatchers.withId

@RunWith(AndroidJUnit4::class)
class RecyclerViewClickedAndTaskCreatedTest {
    private lateinit var mFakeEmployeeModels: MutableList<EmployeeModel>
    private lateinit var mFakeTaskModels: MutableList<TaskModel>
    private lateinit var mCreatedTaskEntity: TaskEntityModel

    @Rule
    var mActivityRule = ActivityTestRule(MainScreenActivity::class.java, true, true)

    @Rule
    var uiThreadTestRule = UiThreadTestRule()

    @Before
    fun setUp() {
        mFakeTaskModels = mutableListOf()
        mFakeEmployeeModels = mutableListOf()
        TestsFaker.fillUpWithFakeData(mFakeTaskModels as MutableList<TaskModel>, mFakeEmployeeModels, 2)
    }

    @Test
    @LargeTest
    @Throws(Throwable::class)
    fun CreateAndSaveTaskEntity() {
        val activity = mActivityRule.activity
        TestUtils.UnlockAndWakeUpDeviceOnUi(activity)

        TestsFaker.launchRecordFragmentFromUi(activity, mFakeTaskModels, mFakeEmployeeModels)

        this.checkIfDisplayedAsExpected(R.id.tasks_list, R.id.no_tasks, mFakeTaskModels)

        this.checkIfDisplayedAsExpected(R.id.employees_list, R.id.no_employees, mFakeEmployeeModels)

        TestUtils.assertSingleSelection(R.id.tasks_list, mFakeTaskModels)

        TestUtils.assertSingleSelection(R.id.employees_list, mFakeEmployeeModels)

        TestUtils.performClickOnViewWithId(R.id.fab_record)

        val fakeWorkingMilliseconds = 2000L
        TestUtils.sSimulateWorkingProcessOnUi(fakeWorkingMilliseconds)

        TestUtils.performClickOnViewWithId(R.id.fab_record)

        this.startUiThreadToGetTaskEntity()

        this.checkCreatedTaskEntity(fakeWorkingMilliseconds)
    }

    @Throws(Throwable::class)
    private fun startUiThreadToGetTaskEntity() {
        uiThreadTestRule.runOnUiThread {
            val activity = mActivityRule.activity
            val fragmentManager = activity.supportFragmentManager
            val fragment = fragmentManager.findFragmentByTag(MainScreenFragment.TAG) as MainScreenFragment
            setTaskEntity(fragment.taskModel)
        }
    }

    private fun setTaskEntity(createdTask: TaskEntityModel) {
        mCreatedTaskEntity = createdTask
    }

    private fun checkCreatedTaskEntity(fakeWorkingMilliseconds: Long) {
        TestUtils.assertItemHasViewWithText(
                R.id.tasks_list,
                mFakeTaskModels.size - 1,
                mCreatedTaskEntity.taskName!!
        )

        TestUtils.assertItemHasViewWithText(
                R.id.employees_list,
                mFakeEmployeeModels.size - 1,
                mCreatedTaskEntity.employeeName!!
        )

        if (mCreatedTaskEntity.isInterrupted) {
            throw AssertionFailedError(
                    "Button \"stop\" was clicked in tests and Task entity should be not interrupted!")
        }

        if (mCreatedTaskEntity.dateAdded == null) {
            throw AssertionFailedError("Task entity must have a date yours is null!")
        }


        if (mCreatedTaskEntity.secondsWorked <= 0) {
            throw AssertionFailedError(mCreatedTaskEntity.secondsWorked.toString() + " is not valid value for seconds worked")
        }

        val dif = mCreatedTaskEntity.secondsWorked * 1000 - fakeWorkingMilliseconds
        val maxAcceptableDif: Long = 50
        if (dif <= -maxAcceptableDif || dif >= maxAcceptableDif) {
            throw AssertionFailedError(mCreatedTaskEntity.secondsWorked.toString() + " is not valid value for seconds worked!")
        }
    }

    private fun checkIfDisplayedAsExpected(recyclerViewId: Int, noItemsViewId: Int, mFakeList: List<*>?) {
        TestUtils.assertIsViewDisplayed(recyclerViewId, true)
        TestUtils.assertIsViewDisplayed(noItemsViewId, false)

        onView(withId(recyclerViewId))
                .check(RecyclerViewItemCountAssertion(mFakeList!!.size))
    }
}


