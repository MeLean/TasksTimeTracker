package com.melean.taskstimetracker.data.models;

public class TaskModel {
    private String taskName;

    public TaskModel() {
    }

    public TaskModel(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
