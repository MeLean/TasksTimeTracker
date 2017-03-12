package com.melean.taskstimetracker.data.models;

public class EmployeeModel {
    private String employeeName;

    public EmployeeModel() {
    }

    public EmployeeModel(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
