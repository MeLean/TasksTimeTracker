package com.melean.taskstimetracker;

import android.content.res.Resources;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.melean.taskstimetracker.recordTasks.RecordTaskActivity;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class UITests {
    private Resources res;

    @Rule
    public ActivityTestRule<RecordTaskActivity> testedActivity =
            new ActivityTestRule<>(RecordTaskActivity.class);

    @Before
    public void initData(){
         res = testedActivity.getActivity().getResources();
    }

    @Test
    @LargeTest
    public void CheckStartingViewsLoaded(){
        onView(allOf(withId(R.id.tasks_list))).check(matches(isDisplayed()));
    }

    @Test
    @LargeTest
    public void clickStartRecord_PauseButtonAndStartStopToggle(){

        //first click should show pause button and change image to save on record button
        onView(allOf(withId(R.id.fab_record))).perform(click());
        onView(withId(R.id.fab_pause)).check(matches(isDisplayed()));
        onView(withContentDescription(res.getString(R.string.save))).check(matches(isDisplayed()));

        //second click should remove pause button and change image to play on record button
        onView(withId(R.id.fab_record)).perform(click());
        onView(withId(R.id.fab_pause)).check(matches(not(isDisplayed())));
        onView(withContentDescription(res.getString(R.string.start))).check(matches(isDisplayed()));
    }
}
