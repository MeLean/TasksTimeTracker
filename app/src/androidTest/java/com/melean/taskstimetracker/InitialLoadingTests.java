package com.melean.taskstimetracker;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.melean.taskstimetracker.recordTasks.RecordTaskActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class InitialLoadingTests {
    @Rule
    public ActivityTestRule<RecordTaskActivity> testedActivity =
            new ActivityTestRule<>(RecordTaskActivity.class);

    @Test
    @LargeTest
    public void CheckStartingViews_whenNoTask(){
        onView(withId(R.id.no_tasks)).check(matches(isDisplayed()));
        onView(withId(R.id.tasks_list)).check(matches((not(isDisplayed()))));
    }

   /* @Test
    @LargeTest
    public void CheckStartingViews_whenHasTasks(){
        onView(withId(R.id.tasks_list)).check(matches(isDisplayed()));
        onView(withId(R.id.no_tasks)).check(matches(not(isDisplayed())));
    }*/
}
