package com.melean.taskstimetracker;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.repositories.ITaskRepository;
import com.melean.taskstimetracker.ui.interfaces.AddDataContract;
import com.melean.taskstimetracker.ui.presenters.AddDataPresenter;
import com.melean.taskstimetracker.ui.presenters.RecordTaskPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class AddDataPresenterTests {
    @Mock
    private ITaskRepository mRecordTaskRepository;

    @Mock
    private AddDataContract.View mAddDataView;

    private AddDataPresenter mAddDataPresenter;

    @Before
    public void initData() {
        //init mockito
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mAddDataPresenter = new AddDataPresenter(mAddDataView, mRecordTaskRepository);
    }

    @Test
    public void onClickAddEmployee_ShouldShowAddEmployeeView() {
        mAddDataPresenter.addEmployee();
        verify(mAddDataView).showAddEmployeeView();
    }

    @Test
    public void onClickAddTask_ShouldShowAddTaskView() {
        mAddDataPresenter.addTaskView();
        verify(mAddDataView).showAddTaskView();
    }


    @Test
    public void onSaveTask_SaveTask() {
        EmployeeModel fakeEmployee = new EmployeeModel("Tester 1");
        mAddDataPresenter.saveEmployee(fakeEmployee);
        verify(mRecordTaskRepository).saveEmployee(fakeEmployee);
    }
}
