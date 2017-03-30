package com.melean.taskstimetracker.recycler_view_assertion_utils;

import android.app.Activity;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.UiThreadTestRule;
import android.view.WindowManager;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

public class TestUtils {
    //RecyclerViewMatcher provider
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    //UiThreadTestRule provider
    public static UiThreadTestRule getUiThreadRule() {
        return new UiThreadTestRule();
    }

    public static void UnlockAndWakeUpDeviceOnUi(final Activity activity) throws Throwable {
        getUiThreadRule().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //unlock and wake device
                activity.getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            }
        });
    }

    public static void sSimulateWorkingProcessOnUi(final long fakeWorkingMilliseconds) throws Throwable {
        getUiThreadRule().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(fakeWorkingMilliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void performClickOnViewWithId(int id){
        onView(withId(id)).perform(click());
    }

    public static void performClickOnViewWithText(String text){
        onView(withText(text)).perform(click());
    }

    public static void performClickAtPosition(int recyclerViewId, int position) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition(position));
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(position, click()));
    }

    public static void assertIsViewDisplayed(int id, boolean shouldBeDisplayed){
        ViewAssertion displayedAssertion = matches(not(isDisplayed()));
        if (shouldBeDisplayed){
            displayedAssertion = matches(isDisplayed());
        }

        onView(withId(id)).check(displayedAssertion);
    }

    public static void assertIsViewDisplayed(String contentDescription, boolean shouldBeDisplayed){
        ViewAssertion displayedAssertion = matches(not(isDisplayed()));
        if (shouldBeDisplayed){
            displayedAssertion = matches(isDisplayed());
        }

        onView(withContentDescription(contentDescription)).check(displayedAssertion);
    }

    public static void assertItemHasViewWithText(int recyclerViewId, int latClickedPosition, String expectedText) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition(latClickedPosition));
        onView(withRecyclerView(recyclerViewId).atPosition(latClickedPosition))
                .check(matches(hasDescendant(withText(expectedText))));
    }

    public static void assertPositionIsSelected(int recyclerViewId, int position, boolean shouldBeSelected) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition(position));
        ViewAssertion assertion = matches(not(ViewMatchers.isSelected()));

        if(shouldBeSelected){
            assertion = matches(ViewMatchers.isSelected());
        }

        onView(withRecyclerView(recyclerViewId).atPosition(position))
                .check(assertion);

    }

    public static void assertThatViewHasDescendantWithText(int viewId, String expectedText) {
        onView(withId(viewId)).check(matches(hasDescendant(withText(expectedText))));
    }

    public static void assertSingleSelection(int recyclerViewId, List mFakeList) {
        for (int i = 0; i < mFakeList.size(); i++) {
            TestUtils.performClickAtPosition(recyclerViewId, i);
            TestUtils.assertPositionIsSelected(recyclerViewId, i, true);
            if (i == 0) {
                //click again in order to deselect item
                TestUtils.performClickAtPosition(recyclerViewId, i);
                TestUtils.assertPositionIsSelected(recyclerViewId, i, false);
            }
        }

        for (int j = 0; j < mFakeList.size(); j++) {
            boolean isSelected = false;
            if (j == mFakeList.size() - 1) {
                isSelected = true;
            }

            TestUtils.assertPositionIsSelected(recyclerViewId, j, isSelected);
        }
    }

}
