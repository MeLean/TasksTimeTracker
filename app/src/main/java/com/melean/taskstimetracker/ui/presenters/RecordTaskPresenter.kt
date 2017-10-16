package com.melean.taskstimetracker.ui.presenters

import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.data.models.TaskModel
import com.melean.taskstimetracker.data.repositories.ITaskRepository
import com.melean.taskstimetracker.ui.enums.ApplicationError
import com.melean.taskstimetracker.ui.interfaces.RecordTaskContract

class RecordTaskPresenter(private val recordTaskView: RecordTaskContract.View, private val taskRepository: ITaskRepository) : RecordTaskContract.UserActionsListener {
    internal var isRecording: Boolean

    init {
        this.isRecording = false
    }


    override fun loadTasks() {
        taskRepository.getTasks(object : ITaskRepository.LoadTasksCallback {
            override fun onTasksLoaded(tasks: MutableList<TaskModel>) {
                recordTaskView.showTasksList(tasks)
            }

        })
    }

    override fun loadEmployees() {
        taskRepository.getEmployees(object : ITaskRepository.LoadEmployeesCallback {
            override fun onEmployeesLoaded(employees: MutableList<EmployeeModel>) {
                recordTaskView.showEmployeesList(employees)
            }
        })
    }

    override fun startRecording() {
        if (!isRecording) {
            recordTaskView.toggleRecording(false)
            isRecording = true
        } else {
            recordTaskView.showErrorRecordIntend(ApplicationError.NOT_PERMITTED_WHILE_RECORDING)
        }

    }

    override fun stopRecording(isInterrupted: Boolean) {
        if (isRecording) {
            recordTaskView.toggleRecording(isInterrupted)
            isRecording = false
            taskRepository.saveTaskEntity(recordTaskView.taskModel)
        } else {
            recordTaskView.showErrorRecordIntend(ApplicationError.PERMITTED_ONLY_WHILE_RECORDING)
        }
    }

    fun setIsRecording(isRecording:Boolean){
        this.isRecording = isRecording;
    }

    fun getIsRecording() : Boolean = isRecording
}
