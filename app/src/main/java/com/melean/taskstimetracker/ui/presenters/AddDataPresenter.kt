package com.melean.taskstimetracker.ui.presenters

import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.data.models.TaskModel
import com.melean.taskstimetracker.data.repositories.ITaskRepository
import com.melean.taskstimetracker.ui.interfaces.AddDataContract

class AddDataPresenter(private val addDataView: AddDataContract.View, private val taskRepository: ITaskRepository) : AddDataContract.UserActionsListener {

    override fun addEmployee() {
        addDataView.showAddEmployeeView()
    }

    override fun addTaskView() {
        addDataView.showAddTaskView()
    }

    override fun saveEmployee(employee: EmployeeModel) {
        taskRepository.saveEmployee(employee)
    }

    override fun saveTask(task: TaskModel) {
        taskRepository.saveTask(task)
    }
}
