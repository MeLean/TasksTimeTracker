package com.melean.taskstimetracker.data.models

import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntityRealmObject


import java.util.Locale

class TaskEntityModel {

    var id: Int = 0
    var employeeName: String? = null
    var taskName: String? = null
    var secondsWorked: Long = 0
    var isInterrupted: Boolean = false
    var dateAdded: String? = null

    constructor() {
        //empty constructor
    }

    constructor(
            employeeName: String?, id: Int, taskName: String?, secondsWorked: Long, isInterrupted: Boolean, dateAdded: String) {

        this.id = id
        this.taskName = taskName
        this.secondsWorked = secondsWorked
        this.isInterrupted = isInterrupted
        this.employeeName = employeeName
        this.dateAdded = dateAdded
    }

    constructor(employeeName: String?, taskName: String?, secondsWorked: Long, isInterrupted: Boolean, dateAdded: String) 
            : this(employeeName, DEFAULT_ID, taskName, secondsWorked, isInterrupted, dateAdded) {}

    override fun toString(): String {
        val entityFormat = "employee: %s\ntask: %s done for %d sec.\n%s\nadded date: %s"

        return String.format(
                Locale.getDefault(),
                entityFormat,
                this.employeeName,
                this.taskName,
                this.secondsWorked,
                if (isInterrupted) "is not interrupted" else "is interrupted",
                this.dateAdded
        )
    }

    companion object {
        val DEFAULT_ID = -1


        fun makeFrom(model: TaskEntityRealmObject): TaskEntityModel {
            val task = TaskEntityModel()
            task.id = model.id
            task.employeeName = model.employeeName
            task.taskName = model.taskName
            task.secondsWorked = model.secondsWorked
            task.isInterrupted = model.isInterrupted
            task.dateAdded = model.dateAdded
            return task
        }
    }
}
