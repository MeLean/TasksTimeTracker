package com.melean.taskstimetracker.ui.interfaces

import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.data.models.TaskModel
import com.melean.taskstimetracker.data.models.TaskEntityModel
import com.melean.taskstimetracker.ui.enums.ApplicationError

interface RecordTaskContract {
    interface View {

        val taskModel: TaskEntityModel
        fun showTasksList(tasksNames: MutableList<TaskModel>)

        fun showEmployeesList(employeesNames: MutableList<EmployeeModel>)

        fun showErrorRecordIntend(applicationError: ApplicationError)

        fun toggleRecording(isInterrupted: Boolean)
    }

    interface UserActionsListener {
        fun loadTasks()

        fun loadEmployees()

        fun startRecording()

        fun stopRecording(isInterrupted: Boolean)
    }
}
