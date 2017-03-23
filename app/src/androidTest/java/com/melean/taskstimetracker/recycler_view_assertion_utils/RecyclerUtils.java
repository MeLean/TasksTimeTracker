package com.melean.taskstimetracker.recycler_view_assertion_utils;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import com.melean.taskstimetracker.R;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


public class RecyclerUtils {

    public static void checkIfDisplayedAsExpected(int recyclerViewId, int noItemsViewId, List mFakeList) {
        onView(withId(recyclerViewId)).check(matches(isDisplayed()));
        onView(withId(noItemsViewId)).check(matches((not(isDisplayed()))));

        onView(withId(R.id.tasks_list))
                .check(new RecyclerViewItemCountAssertion(mFakeList.size()));
    }

    public static void checkRecyclerViewItemHasViewWithText(int recyclerViewId, int latClickedPosition, String expectedText) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition(latClickedPosition));
        onView(withRecyclerView(recyclerViewId).atPosition(latClickedPosition))
                .check(matches(hasDescendant(withText(expectedText))));
    }

    public static void assertViewAtPositionIsSelected(int recyclerViewId, int position, boolean mustBeSelected) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition(position));
        ViewAssertion assertion = matches(not(ViewMatchers.isSelected()));

        if(mustBeSelected){
            assertion = matches(ViewMatchers.isSelected());
        }

        onView(withRecyclerView(recyclerViewId).atPosition(position))
                .check(assertion);

    }

    public static void performClickAtPosition(int recyclerViewId, int position) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition(position));
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(position, click()));
    }

    //RecyclerViewMatcher helper
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

}
