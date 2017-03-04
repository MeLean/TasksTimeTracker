package com.melean.taskstimetracker;

import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntity;
import com.melean.taskstimetracker.data.repositories.ITaskRepository;
import com.melean.taskstimetracker.recordTasks.RecordTaskContract;
import com.melean.taskstimetracker.recordTasks.RecordTaskPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class RecordTaskPresenterTests {
    @Mock
    private ITaskRepository mRecordTaskRepository;

    @Mock
    private RecordTaskContract.View mRecordTasksView;


    private RecordTaskPresenter mRecordTaskPresenter;

    public RecordTaskPresenterTests() {
    }

    @Before
    public void initData() {
        //init mockito
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mRecordTaskPresenter = new RecordTaskPresenter(mRecordTasksView, mRecordTaskRepository);
    }

    @Test
    public void onClick_StartRecord_Should_Be_Fine() {
        mRecordTaskPresenter.isRecording = false;
        mRecordTaskPresenter.startRecording();
        verify(mRecordTasksView).startTimeCounter();
    }


    @Test
    public void onClick_StartRecord_Should_Show_Error() {
        mRecordTaskPresenter.isRecording = true;
        mRecordTaskPresenter.startRecording();
        verify(mRecordTasksView).showErrorRecordIntend(true);
    }

    @Test
    public void onClick_StopRecord_Should_Be_Fine() {
        mRecordTaskPresenter.isRecording = true;
        mRecordTaskPresenter.stopRecording();
        verify(mRecordTasksView).stopTimeCounter();

        ArgumentCaptor<String> employeeArgument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> taskNameArgument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> timeWorkedArgument = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Boolean> isInterruptedArgument = ArgumentCaptor.forClass(Boolean.class);

        verify(mRecordTaskRepository).saveTask(
                employeeArgument.capture(),
                taskNameArgument.capture(),
                timeWorkedArgument.capture(),
                isInterruptedArgument.capture()
        );

        assertEquals(mRecordTasksView.getEmployeeName(),employeeArgument.getValue());
        assertEquals(mRecordTasksView.getTaskName(),taskNameArgument.getValue());
        assertEquals(Long.valueOf(mRecordTasksView.getSecondsWorked()), timeWorkedArgument.getValue());
        assertEquals(Boolean.valueOf(mRecordTasksView.isInterrupted()), isInterruptedArgument.getValue());
    }

    @Test
    public void onClick_StopRecord_Should_Show_Error() {
        mRecordTaskPresenter.isRecording = false;
        mRecordTaskPresenter.stopRecording();
        verify(mRecordTasksView).showErrorRecordIntend(false);
    }

    @Test
    public void onPickTaskName_Should_Be_Fine() {
        mRecordTaskPresenter.isRecording = false;
        mRecordTaskPresenter.pickTaskName();
        verify(mRecordTasksView).showTaskNamePiker();
    }

    @Test
    public void onPickTaskName_Should_Show_Error() {
        mRecordTaskPresenter.isRecording = true;
        mRecordTaskPresenter.pickTaskName();
        verify(mRecordTasksView).showErrorRecordIntend(true);
    }

    @Test
    public void onPickAnEmployee_Should_Be_Fine() {
        mRecordTaskPresenter.isRecording = false;
        mRecordTaskPresenter.pickAnEmployee();
        verify(mRecordTasksView).showEmployeePicker();
    }

    @Test
    public void onPickAnEmployee_Should_Show_Error() {
        mRecordTaskPresenter.isRecording = true;
        mRecordTaskPresenter.pickAnEmployee();
        verify(mRecordTasksView).showErrorRecordIntend(true);
    }
}
