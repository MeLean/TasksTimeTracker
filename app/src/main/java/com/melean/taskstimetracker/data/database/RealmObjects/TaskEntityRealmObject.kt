package com.melean.taskstimetracker.data.database.RealmObjects

import com.melean.taskstimetracker.data.models.TaskEntityModel

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required


open class TaskEntityRealmObject(
        @PrimaryKey
        var id: Int = 0,
        @Required
        var employeeName: String? = null,
        @Index
        @Required
        var taskName: String? = null,
        var secondsWorked: Long = 0,
        var isInterrupted: Boolean = false,
        @Required
        var dateAdded: String? = null
) : RealmObject() {

    constructor() : this(0, null, null,0,false,null)
    companion object {

        fun makeFrom(model: TaskEntityModel): TaskEntityRealmObject {
            val realmObject = TaskEntityRealmObject()
            realmObject.id = model.id
            realmObject.employeeName = model.employeeName
            realmObject.taskName = model.taskName
            realmObject.secondsWorked = model.secondsWorked
            realmObject.isInterrupted = model.isInterrupted
            realmObject.dateAdded = model.dateAdded
            return realmObject
        }
    }

}
