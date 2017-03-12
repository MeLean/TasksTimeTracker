package com.melean.taskstimetracker.data.database.RealmObjects;

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
}
