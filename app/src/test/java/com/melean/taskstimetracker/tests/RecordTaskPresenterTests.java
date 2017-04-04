package com.melean.taskstimetracker.tests;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskEntityModel;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.data.repositories.ITaskRepository;
import com.melean.taskstimetracker.ui.enums.ApplicationError;
import com.melean.taskstimetracker.ui.interfaces.RecordTaskContract;
import com.melean.taskstimetracker.ui.presenters.RecordTaskPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @Captor
    ArgumentCaptor<List<TaskModel>> tasksModelCaptor;

    @Captor
    ArgumentCaptor<List<EmployeeModel>> employeeModelCaptor;

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
        verify(mRecordTasksView).toggleRecording(false);
    }

    @Test
    public void onClickStartRecord_ShouldShowError() {
        mRecordTaskPresenter.isRecording = true;
        mRecordTaskPresenter.startRecording();
        verify(mRecordTasksView).showErrorRecordIntend(ApplicationError.NOT_PERMITTED_WHILE_RECORDING);
    }

    @Test
    public void onClickStopRecord_ShouldStopRecordingWell() {
        mRecordTaskPresenter.isRecording = true;
        mRecordTaskPresenter.stopRecording(false);
        verify(mRecordTasksView).toggleRecording(false);

        ArgumentCaptor<TaskEntityModel> taskModelArgument = ArgumentCaptor.forClass(TaskEntityModel.class);
        verify(mRecordTaskRepository).saveTaskEntity(taskModelArgument.capture());
        assertEquals(mRecordTasksView.getTaskModel(), taskModelArgument.getValue());
    }

    @Test
    public void onClickStopRecord_ShouldShowError() {
        mRecordTaskPresenter.isRecording = false;
        mRecordTaskPresenter.stopRecording(false);
        verify(mRecordTasksView).showErrorRecordIntend(ApplicationError.PERMITTED_ONLY_WHILE_RECORDING);
    }

    @Test
    public void onDisplayTasksEmployees_ShouldShowError() {
        mRecordTaskPresenter.isRecording = false;
        mRecordTaskPresenter.loadTasks();
        mRecordTaskPresenter.loadEmployees();

        ArgumentCaptor<ITaskRepository.LoadTasksCallback> taskCallbackArgument = ArgumentCaptor.forClass(
                ITaskRepository.LoadTasksCallback.class);

        ArgumentCaptor<ITaskRepository.LoadEmployeesCallback> employeeCallbackArgument = ArgumentCaptor.forClass(
                ITaskRepository.LoadEmployeesCallback.class);

        verify(mRecordTaskRepository).getTasks(taskCallbackArgument.capture());
        taskCallbackArgument.getValue().onTasksLoaded(tasksModelCaptor.capture());
        verify(mRecordTasksView).showTasksList(tasksModelCaptor.capture());

        verify(mRecordTaskRepository).getEmployees(employeeCallbackArgument.capture());
        employeeCallbackArgument.getValue().onEmployeesLoaded(employeeModelCaptor.capture());
        verify(mRecordTasksView).showEmployeesList(employeeModelCaptor.capture());
    }
}
