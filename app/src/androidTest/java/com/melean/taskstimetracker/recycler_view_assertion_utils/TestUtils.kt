package com.melean.taskstimetracker.recycler_view_assertion_utils

import android.app.Activity
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.UiThreadTestRule
import android.view.WindowManager

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Matchers.not

object TestUtils {

    //UiThreadTestRule provider
    val uiThreadRule: UiThreadTestRule
        get() = UiThreadTestRule()

    //RecyclerViewMatcher provider
    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    @Throws(Throwable::class)
    fun UnlockAndWakeUpDeviceOnUi(activity: Activity) {
        uiThreadRule.runOnUiThread {
            //unlock and wake device
            activity.window
                    .addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        }
    }

    @Throws(Throwable::class)
    fun sSimulateWorkingProcessOnUi(fakeWorkingMilliseconds: Long) {
        uiThreadRule.runOnUiThread {
            try {
                Thread.sleep(fakeWorkingMilliseconds)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    fun performClickOnViewWithId(id: Int) {
        onView(withId(id)).perform(click())
    }

    fun performClickOnViewWithText(text: String) {
        onView(withText(text)).perform(click())
    }

    fun performClickAtPosition(recyclerViewId: Int, position: Int) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
    }

    fun assertIsViewDisplayed(id: Int, shouldBeDisplayed: Boolean) {
        var displayedAssertion = matches(not<View>(isDisplayed()))
        if (shouldBeDisplayed) {
            displayedAssertion = matches(isDisplayed())
        }

        onView(withId(id)).check(displayedAssertion)
    }

    fun assertIsViewDisplayed(contentDescription: String, shouldBeDisplayed: Boolean) {
        var displayedAssertion = matches(not<View>(isDisplayed()))
        if (shouldBeDisplayed) {
            displayedAssertion = matches(isDisplayed())
        }

        onView(withContentDescription(contentDescription)).check(displayedAssertion)
    }

    fun assertItemHasViewWithText(recyclerViewId: Int, latClickedPosition: Int, expectedText: String) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(latClickedPosition))
        onView(withRecyclerView(recyclerViewId).atPosition(latClickedPosition))
                .check(matches(hasDescendant(withText(expectedText))))
    }

    fun assertPositionIsSelected(recyclerViewId: Int, position: Int, shouldBeSelected: Boolean) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
        var assertion = matches(not<View>(ViewMatchers.isSelected()))

        if (shouldBeSelected) {
            assertion = matches(ViewMatchers.isSelected())
        }

        onView(withRecyclerView(recyclerViewId).atPosition(position))
                .check(assertion)

    }

    fun assertThatViewHasDescendantWithText(viewId: Int, expectedText: String) {
        onView(withId(viewId)).check(matches(hasDescendant(withText(expectedText))))
    }

    fun assertSingleSelection(recyclerViewId: Int, mFakeList: List<*>) {
        for (i in mFakeList.indices) {
            TestUtils.performClickAtPosition(recyclerViewId, i)
            TestUtils.assertPositionIsSelected(recyclerViewId, i, true)
            if (i == 0) {
                //click again in order to deselect item
                TestUtils.performClickAtPosition(recyclerViewId, i)
                TestUtils.assertPositionIsSelected(recyclerViewId, i, false)
            }
        }

        for (j in mFakeList.indices) {
            var isSelected = false
            if (j == mFakeList.size - 1) {
                isSelected = true
            }

            TestUtils.assertPositionIsSelected(recyclerViewId, j, isSelected)
        }
    }

}
