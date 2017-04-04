package com.melean.taskstimetracker;


import com.melean.taskstimetracker.InstrumentedTests.AddEmployeeTests;
import com.melean.taskstimetracker.InstrumentedTests.ButtonsStartStopTests;
import com.melean.taskstimetracker.InstrumentedTests.RecyclerViewClickedAndTaskCreatedTest;
import com.melean.taskstimetracker.InstrumentedTests.ShowApplicationErrorFromViewTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ButtonsStartStopTests.class,
        RecyclerViewClickedAndTaskCreatedTest.class,
        ShowApplicationErrorFromViewTest.class
})
public class InstrumentedTestSuite {}
