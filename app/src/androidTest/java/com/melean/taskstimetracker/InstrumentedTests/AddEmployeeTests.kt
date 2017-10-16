package com.melean.taskstimetracker.InstrumentedTests

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import com.melean.taskstimetracker.R
import com.melean.taskstimetracker.ui.activities.MainScreenActivity
import com.melean.taskstimetracker.recycler_view_assertion_utils.TestUtils

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddEmployeeTests {
    @Rule
    var mActivityRule = ActivityTestRule(MainScreenActivity::class.java)

    @Test
    @LargeTest
    @Throws(Throwable::class)
    fun onAddEmployee_ShouldAddEmployee() {
        val activity = mActivityRule.activity
        TestUtils.UnlockAndWakeUpDeviceOnUi(activity)

        TestUtils.performClickOnViewWithId(R.id.btn_add_employee)
        TestUtils.assertIsViewDisplayed(R.id.add_employee_fragment, true)
    }
}
