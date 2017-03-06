package com.melean.taskstimetracker;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.melean.taskstimetracker.recordTasks.RecordTaskActivity;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class UITests {
    @Rule
    public ActivityTestRule<RecordTaskActivity> testedActivity =
            new ActivityTestRule<>(RecordTaskActivity.class);

    @Test
    @LargeTest
    public void clickStartRecord_showPauseRecordButton(){

        onView(withId(R.id.fab_record)).perform(click());
        onView(withId(R.id.fab_pause)).check(matches(isDisplayed()));
    }
}
