package com.melean.taskstimetracker.data.repositories

import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.data.models.TaskEntityModel
import com.melean.taskstimetracker.data.models.TaskModel

import java.text.SimpleDateFormat

import io.realm.RealmObject

interface ITaskRepository {

    val dateFormatter: SimpleDateFormat

    interface GetAllTaskEntitiesCallback {
        fun onTaskEntitiesLoaded(taskEntityModels: List<TaskEntityModel>)
    }

    interface GetTaskEntityCallback {
        fun onTaskEntityLoaded(taskEntityModel: TaskEntityModel)
    }

    interface LoadTasksCallback {
        fun onTasksLoaded(tasks: MutableList<TaskModel>)
    }

    interface LoadEmployeesCallback {
        fun onEmployeesLoaded(employees: MutableList<EmployeeModel>)
    }

    fun saveTaskEntity(task: TaskEntityModel)

    fun saveAllTaskEntities(tasks: MutableList<TaskEntityModel>)

    fun saveEmployee(employeeModel: EmployeeModel)

    fun saveTask(taskModel: TaskModel)

    fun getAllTaskEntities(callback: GetAllTaskEntitiesCallback)

    fun getTaskEntity(taskId: Int, callback: GetTaskEntityCallback)

    fun getTasks(callback: LoadTasksCallback)

    fun getEmployees(callback: LoadEmployeesCallback)
}