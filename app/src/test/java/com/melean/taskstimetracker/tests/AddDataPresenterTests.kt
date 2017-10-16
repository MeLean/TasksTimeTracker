package com.melean.taskstimetracker.tests

import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.data.models.TaskModel
import com.melean.taskstimetracker.data.repositories.ITaskRepository
import com.melean.taskstimetracker.ui.interfaces.AddDataContract
import com.melean.taskstimetracker.ui.presenters.AddDataPresenter
import com.melean.taskstimetracker.ui.presenters.RecordTaskPresenter

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import org.mockito.Mockito.verify

class AddDataPresenterTests {
    @Mock
    private val mRecordTaskRepository: ITaskRepository? = null

    @Mock
    private val mAddDataView: AddDataContract.View? = null

    private var mAddDataPresenter: AddDataPresenter? = null

    @Before
    fun initData() {
        //init mockito
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        mAddDataPresenter = AddDataPresenter(mAddDataView!!, mRecordTaskRepository!!)
    }

    @Test
    fun onClickAddEmployee_ShouldShowAddEmployeeView() {
        mAddDataPresenter!!.addEmployee()
        verify(mAddDataView)?.showAddEmployeeView()
    }

    @Test
    fun onClickAddTask_ShouldShowAddTaskView() {
        mAddDataPresenter!!.addTaskView()
        verify(mAddDataView)?.showAddTaskView()
    }


    @Test
    fun onSaveEmployee_SaveEmployee() {
        val fakeEmployee = EmployeeModel("Tester 1")
        mAddDataPresenter!!.saveEmployee(fakeEmployee)
        verify<ITaskRepository>(mRecordTaskRepository).saveEmployee(fakeEmployee)
    }

    @Test
    fun onSaveTask_SaveTask() {
        val fakeTask = TaskModel("Task test 1")
        mAddDataPresenter!!.saveTask(fakeTask)
        verify<ITaskRepository>(mRecordTaskRepository).saveTask(fakeTask)
    }
}
