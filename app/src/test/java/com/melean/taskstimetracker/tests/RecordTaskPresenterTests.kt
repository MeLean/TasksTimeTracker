package com.melean.taskstimetracker.tests

import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.data.models.TaskEntityModel
import com.melean.taskstimetracker.data.models.TaskModel
import com.melean.taskstimetracker.data.repositories.ITaskRepository
import com.melean.taskstimetracker.ui.enums.ApplicationError
import com.melean.taskstimetracker.ui.interfaces.RecordTaskContract
import com.melean.taskstimetracker.ui.presenters.RecordTaskPresenter

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import junit.framework.Assert.assertEquals
import org.mockito.Mockito.verify

class RecordTaskPresenterTests {
    @Mock
    private val mRecordTaskRepository: ITaskRepository? = null

    @Mock
    private val mRecordTasksView: RecordTaskContract.View? = null

    @Captor
    internal var tasksModelCaptor: ArgumentCaptor<MutableList<TaskModel>>? = null

    @Captor
    internal var employeeModelCaptor: ArgumentCaptor<MutableList<EmployeeModel>>? = null

    private var mRecordTaskPresenter: RecordTaskPresenter? = null

    @Before
    fun initData() {
        //init mockito
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        mRecordTaskPresenter = RecordTaskPresenter(mRecordTasksView!!, mRecordTaskRepository!!)
    }

    @Test
    fun onClickStartRecording_ShouldStartRecording() {
        mRecordTaskPresenter!!.setIsRecording(false)
        mRecordTaskPresenter!!.startRecording()
        verify(mRecordTasksView)?.toggleRecording(false)
    }

    @Test
    fun onClickStartRecord_ShouldShowError() {
        mRecordTaskPresenter!!.setIsRecording(true)
        mRecordTaskPresenter!!.startRecording()
        verify(mRecordTasksView)?.showErrorRecordIntend(ApplicationError.NOT_PERMITTED_WHILE_RECORDING)
    }

    @Test
    fun onClickStopRecord_ShouldStopRecordingWell() {
        mRecordTaskPresenter!!.setIsRecording(true)
        mRecordTaskPresenter!!.stopRecording(false)
        verify(mRecordTasksView)?.toggleRecording(false)

        val taskModelArgument = ArgumentCaptor.forClass(TaskEntityModel::class.java)
        verify<ITaskRepository>(mRecordTaskRepository).saveTaskEntity(taskModelArgument.capture())
        assertEquals(mRecordTasksView!!.taskModel, taskModelArgument.value)
    }

    @Test
    fun onClickStopRecord_ShouldShowError() {
        mRecordTaskPresenter!!.setIsRecording(false)
        mRecordTaskPresenter!!.stopRecording(false)
        verify(mRecordTasksView)?.showErrorRecordIntend(ApplicationError.PERMITTED_ONLY_WHILE_RECORDING)
    }

    @Test
    fun onDisplayTasksEmployees_ShouldShowError() {
        mRecordTaskPresenter!!.setIsRecording(false)
        mRecordTaskPresenter!!.loadTasks()
        mRecordTaskPresenter!!.loadEmployees()

        val taskCallbackArgument = ArgumentCaptor.forClass(
                ITaskRepository.LoadTasksCallback::class.java)

        val employeeCallbackArgument = ArgumentCaptor.forClass(
                ITaskRepository.LoadEmployeesCallback::class.java)

        verify<ITaskRepository>(mRecordTaskRepository).getTasks(taskCallbackArgument.capture())
        taskCallbackArgument.value.onTasksLoaded(tasksModelCaptor!!.capture())
        verify(mRecordTasksView)?.showTasksList(tasksModelCaptor!!.capture())

        verify<ITaskRepository>(mRecordTaskRepository).getEmployees(employeeCallbackArgument.capture())
        employeeCallbackArgument.value.onEmployeesLoaded(employeeModelCaptor!!.capture())
        verify(mRecordTasksView)?.showEmployeesList(employeeModelCaptor!!.capture())
    }
}
