package com.melean.taskstimetracker.data.database.RealmObjects;

import com.melean.taskstimetracker.data.models.EmployeeModel;

import io.realm.RealmObject;

public class EmployeeRealmObject extends RealmObject{
    private String employeeName;

    public EmployeeRealmObject() {
    }

    public EmployeeRealmObject(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public static RealmObject from(EmployeeModel employeeModel) {
        EmployeeRealmObject object = new  EmployeeRealmObject();
        object.employeeName = employeeModel.getEmployeeName();
        return object;
    }
}
