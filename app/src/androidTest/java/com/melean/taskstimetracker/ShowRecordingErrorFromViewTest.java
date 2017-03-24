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


@RunWith(AndroidJUnit4.class)
public class ShowRecordingErrorFromViewTest {
    private List<EmployeeModel> mFakeEmployeeModels;
    private List<TaskModel> mFakeTaskModels;
    private Resources mResources;
    @Rule
    public ActivityTestRule<RecordTaskActivity> mActivityRule =
            new ActivityTestRule<>(RecordTaskActivity.class, true, true);

    @Before
    public void setUp(){
        mResources = mActivityRule.getActivity().getResources();
    }

    @Test
    @LargeTest
    public void ShowingErrors() throws Throwable {
        TestUtils.UnlockAndWakeUpDeviceOnUi(mActivityRule.getActivity());

        TestUtils.assertIsViewDisplayed(R.id.no_tasks, true);

        TestUtils.performClickOnViewWithId(R.id.fab_record);

        TestUtils.assertIsViewDisplayed(R.id.noting_selected_error, true);

        assertErrorTextShown(R.id.noting_selected_error, mResources.getString(R.string.error_not_full_selection));

        this.intFakeDataOnUi();

        TestUtils.performClickAtPosition(R.id.tasks_list, mFakeTaskModels.size() - 1);
        TestUtils.performClickOnViewWithId(R.id.fab_record);
        assertErrorTextShown(R.id.noting_selected_error, mResources.getString(R.string.error_not_full_selection));


        TestUtils.performClickAtPosition(R.id.tasks_list, mFakeTaskModels.size() - 1);
        TestUtils.performClickAtPosition(R.id.employees_list, mFakeEmployeeModels.size() - 1);
        TestUtils.performClickOnViewWithId(R.id.fab_record);
        assertErrorTextShown(R.id.noting_selected_error, mResources.getString(R.string.error_not_full_selection));
    }

    public void assertErrorTextShown(int viewId, String textToShow) {
        TestUtils.assertThatViewHasDescendantWithText(viewId, textToShow);
        TestUtils.performClickOnViewWithId(R.id.btn_error_dialog_ok);
    }

    public void intFakeDataOnUi() throws Throwable {
        mFakeTaskModels = new ArrayList<>();
        mFakeEmployeeModels = new ArrayList<>();
        TestsFaker.fillUpWithFakeData(mFakeTaskModels, mFakeEmployeeModels, 2);
        TestsFaker.launchRecordFragmentFromUi(
                mActivityRule.getActivity(),
                mFakeTaskModels,
                mFakeEmployeeModels
        );
    }
}
