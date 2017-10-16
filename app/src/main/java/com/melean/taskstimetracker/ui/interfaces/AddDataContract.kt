package com.melean.taskstimetracker.ui.interfaces

import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.data.models.TaskModel

interface AddDataContract {
    interface View {
        fun showAddEmployeeView()

        fun showAddTaskView()

        fun showErrorAdding()

        fun updateEmployees(employee: EmployeeModel)

        fun updateTasks(task: TaskModel)
    }

    interface UserActionsListener {
        fun addEmployee()

        fun addTaskView()

        fun saveEmployee(employee: EmployeeModel)

        fun saveTask(task: TaskModel)
    }
}