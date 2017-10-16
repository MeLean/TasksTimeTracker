package com.melean.taskstimetracker.data.database.RealmObjects

import com.melean.taskstimetracker.data.models.EmployeeModel

import io.realm.RealmObject

open class EmployeeRealmObject(var employeeName: String) : RealmObject() {
    constructor() : this("")
    companion object {
        fun from(employeeModel: EmployeeModel): RealmObject =
                EmployeeRealmObject(employeeModel.employeeName)
    }
}
