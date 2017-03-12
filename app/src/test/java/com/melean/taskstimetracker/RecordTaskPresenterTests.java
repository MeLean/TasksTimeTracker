package com.melean.taskstimetracker;

import com.melean.taskstimetracker.data.database.RealmObjects.TaskRealmObject;
import com.melean.taskstimetracker.data.models.TaskEntityModel;
import com.melean.taskstimetracker.data.repositories.ITaskRepository;
import com.melean.taskstimetracker.recordTasks.RecordTaskContract;
import com.melean.taskstimetracker.recordTasks.RecordTaskPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    public void onClickStartRecording_ShouldStartRecording() {
        mRecordTaskPresenter.isRecording = false;
        mRecordTaskPresenter.startRecording();
        verify(mRecordTasksView).toggleTimeCounter(true);
    }

    @Test
    public void onClickStartRecord_ShouldShowError() {
        mRecordTaskPresenter.isRecording = true;
        mRecordTaskPresenter.startRecording();
        verify(mRecordTasksView).showErrorRecordIntend(true);
    }

    @Test
    public void onClickStopRecord_ShouldStopRecordingWell() {
        mRecordTaskPresenter.isRecording = true;
        mRecordTaskPresenter.stopRecording();
        verify(mRecordTasksView).toggleTimeCounter(false);

        ArgumentCaptor<TaskEntityModel> taskModelArgument = ArgumentCaptor.forClass(TaskEntityModel.class);

        verify(mRecordTaskRepository).saveTaskEntity(taskModelArgument.capture());

        assertEquals(mRecordTasksView.getTaskModel(), taskModelArgument.getValue());
    }

    @Test
    public void onClickStopRecord_ShouldShowError() {
        mRecordTaskPresenter.isRecording = false;
        mRecordTaskPresenter.stopRecording();
        verify(mRecordTasksView).showErrorRecordIntend(false);
    }

    @Test
    public void onGetTaskName_ShouldShowError() {
        mRecordTaskPresenter.isRecording = false;
        mRecordTaskPresenter.getTasksNames();
        ArgumentCaptor<ITaskRepository.GetTasksCallback> taskCallbackArgument =
                ArgumentCaptor.forClass(ITaskRepository.GetTasksCallback.class);
        verify(mRecordTaskRepository).getTasks(taskCallbackArgument.capture());
    }
}
