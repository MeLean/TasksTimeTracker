package com.melean.taskstimetracker.ui.presenters;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.data.repositories.ITaskRepository;
import com.melean.taskstimetracker.ui.interfaces.AddDataContract;

public class AddDataPresenter implements AddDataContract.UserActionsListener{
    private ITaskRepository taskRepository;
    private AddDataContract.View addDataView;
    public AddDataPresenter(AddDataContract.View addDataView, ITaskRepository taskRepository) {
        this.addDataView = addDataView;
        this.taskRepository = taskRepository;
    }

    @Override
    public void addEmployee() {
        addDataView.showAddEmployeeView();
    }

    @Override
    public void addTaskView() {
        addDataView.showAddTaskView();
    }

    @Override
    public void saveEmployee(EmployeeModel employee) {
        taskRepository.saveEmployee(employee);
    }

    @Override
    public void saveTask(TaskModel task) {
        taskRepository.saveTask(task);
    }
}
