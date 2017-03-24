package com.melean.taskstimetracker;

import android.content.res.Resources;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.record_tasks.RecordTaskActivity;
import com.melean.taskstimetracker.recycler_view_assertion_utils.TestUtils;
import com.melean.taskstimetracker.recycler_view_assertion_utils.TestsFaker;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class ButtonsStartStopTests {
    private Resources res;

    @Rule
    public ActivityTestRule<RecordTaskActivity> mActivityRule =
            new ActivityTestRule<>(RecordTaskActivity.class);

    @Before
    public void setUp(){
         res = mActivityRule.getActivity().getResources();
    }

    @Test
    @LargeTest
    public void clickStartRecord_PauseButtonAndStartStopToggle() throws Throwable{
        final RecordTaskActivity  activity = mActivityRule.getActivity();
        List<TaskModel> mFakeTaskModels = new ArrayList<>();
        List<EmployeeModel> mFakeEmployeeModels = new ArrayList<>();

        TestsFaker.fillUpWithFakeData(mFakeTaskModels, mFakeEmployeeModels, 2);
        TestsFaker.launchRecordFragmentFromUi(
                activity,
                mFakeTaskModels,
                mFakeEmployeeModels
        );

        TestUtils.performClickAtPosition(R.id.tasks_list, 0);
        TestUtils.performClickAtPosition(R.id.employees_list, 0);

        TestUtils.UnlockAndWakeUpDeviceOnUi(activity);
        //first click should show pause button and change image to save on record button
        onView(withId(R.id.fab_record)).perform(click());
        onView(withId(R.id.fab_pause)).check(matches(isDisplayed()));
        onView(withContentDescription(res.getString(R.string.save))).check(matches(isDisplayed()));

        //second click should remove pause button and change image to play on record button
        onView(withId(R.id.fab_record)).perform(click());
        onView(withId(R.id.fab_pause)).check(matches(not(isDisplayed())));
        onView(withContentDescription(res.getString(R.string.start))).check(matches(isDisplayed()));

        onView(withId(R.id.fab_record)).perform(click());
        onView(withId(R.id.fab_pause)).perform(click());
        onView(withContentDescription(res.getString(R.string.start))).check(matches(isDisplayed()));

    }
}
