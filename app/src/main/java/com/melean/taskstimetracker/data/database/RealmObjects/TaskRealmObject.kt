package com.melean.taskstimetracker.data.database.RealmObjects


import com.melean.taskstimetracker.data.models.TaskModel

import io.realm.RealmObject

open class TaskRealmObject : RealmObject() {
    var taskName: String? = null

    companion object {
        fun from(taskModel: TaskModel): RealmObject {
            val model =  TaskRealmObject()
            model.taskName = taskModel.taskName
            return model
        }
    }
}
