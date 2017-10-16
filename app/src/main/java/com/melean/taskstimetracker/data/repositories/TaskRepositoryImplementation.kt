package com.melean.taskstimetracker.data.repositories

import com.melean.taskstimetracker.constants.Preferences
import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase
import com.melean.taskstimetracker.data.database.RealmObjects.EmployeeRealmObject
import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntityRealmObject
import com.melean.taskstimetracker.data.database.RealmObjects.TaskRealmObject
import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.data.models.TaskEntityModel
import com.melean.taskstimetracker.data.models.TaskModel

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Locale

import io.realm.RealmObject
import io.realm.exceptions.RealmException

class TaskRepositoryImplementation(private val mRealmDatabase: RealmDatabase) : ITaskRepository {
    override val dateFormatter = SimpleDateFormat(Preferences.ENTITY_DATE_FORMAT, Locale.getDefault())


    override fun getAllTaskEntities(callback: ITaskRepository.GetAllTaskEntitiesCallback) {
        callback.onTaskEntitiesLoaded(makeModelList(mRealmDatabase.copyAll(TaskEntityRealmObject::class.java)))
    }

    override fun getTaskEntity(taskId: Int, callback: ITaskRepository.GetTaskEntityCallback) {
        val results = mRealmDatabase.copyAllByProperty<TaskEntityRealmObject>(TaskEntityRealmObject::class.java, "id", taskId)

        if (results.size == 1) {
            callback.onTaskEntityLoaded(TaskEntityModel.makeFrom(results[0]))
        } else {
            if (results.size > 1) {
                throw RealmException("More than one entity found!")
            }
        }
    }


    override fun saveEmployee(employeeModel: EmployeeModel) {
        mRealmDatabase.add(EmployeeRealmObject.from(employeeModel))
    }

    override fun saveTask(taskModel: TaskModel) {
        mRealmDatabase.add(TaskRealmObject.from(taskModel))
    }

    override fun saveTaskEntity(task: TaskEntityModel) {
        mRealmDatabase.add(TaskEntityRealmObject.makeFrom(task))
    }

    override fun saveAllTaskEntities(tasks: MutableList<TaskEntityModel>) {
        mRealmDatabase.addAll(makeRealmObjectList(tasks))
    }

    override fun getTasks(callback: ITaskRepository.LoadTasksCallback) {
        callback.onTasksLoaded(
                makeTaskModelList(mRealmDatabase.copyAll(TaskRealmObject::class.java))
        )
    }

    override fun getEmployees(callback: ITaskRepository.LoadEmployeesCallback) {
        callback.onEmployeesLoaded(
                makeEmployeeList(mRealmDatabase.copyAll(EmployeeRealmObject::class.java))
        )
    }

    private fun makeRealmObjectList(models: MutableList<TaskEntityModel>): MutableList<TaskEntityRealmObject> {
        val resultList = ArrayList<TaskEntityRealmObject>()
        for (element in models) {
            resultList.add(TaskEntityRealmObject.makeFrom(element))
        }

        return resultList
    }

    private fun makeModelList(realmObjects: MutableList<TaskEntityRealmObject>): MutableList<TaskEntityModel> =
            realmObjects.map { TaskEntityModel.makeFrom(it) } as MutableList<TaskEntityModel>


    private fun makeEmployeeList(employeeRealmObjects: MutableList<EmployeeRealmObject>): MutableList<EmployeeModel> =
            employeeRealmObjects.map { EmployeeModel(it.employeeName) } as MutableList<EmployeeModel>


    private fun makeTaskModelList(realmObjects: MutableList<TaskRealmObject>): MutableList<TaskModel> =
            realmObjects.map { TaskModel(it.taskName.toString()) } as MutableList<TaskModel>
}
