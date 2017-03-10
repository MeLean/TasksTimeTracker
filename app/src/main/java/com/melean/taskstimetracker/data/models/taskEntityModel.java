package com.melean.taskstimetracker.data.models;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntityRealmObject;


import java.util.Locale;

public class TaskEntityModel {
    public static final int DEFAULT_ID = -1;

    private int id;
    private String employeeName;
    private String taskName;
    private long secondsWorked;
    private boolean isInterrupted;
    private String dateAdded;

    public TaskEntityModel(){
        //empty constructor
    }

    public TaskEntityModel(
            String employeeName, int id, String taskName, long secondsWorked, boolean isInterrupted, String dateAdded) {

        this.id = id;
        this.taskName = taskName;
        this.secondsWorked = secondsWorked;
        this.isInterrupted = isInterrupted;
        this.employeeName = employeeName;
        this.dateAdded = dateAdded;
    }

    public TaskEntityModel(String employeeName, String taskName, long secondsWorked, boolean isInterrupted, String dateAdded) {
        this(employeeName, DEFAULT_ID, taskName, secondsWorked, isInterrupted, dateAdded);
    }


    public static TaskEntityModel makeFrom(@NonNull TaskEntityRealmObject model) {
        TaskEntityModel task = new  TaskEntityModel();
        task.id = model.getId();
        task.employeeName = model.getEmployeeName();
        task.taskName = model.getTaskName();
        task.secondsWorked = model.getSecondsWorked();
        task.isInterrupted = model.isInterrupted();
        task.dateAdded = model.getDateAdded();
        return task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getSecondsWorked() {
        return secondsWorked;
    }

    public void setSecondsWorked(long secondsWorked) {
        this.secondsWorked = secondsWorked;
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }

    public void setInterrupted(boolean interrupted) {
        isInterrupted = interrupted;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        String entityFormat = "employee: %s\ntask: %s done for %d sec.\n%s\nadded date: %s";

        return  String.format(
                Locale.getDefault(),
                entityFormat,
                this.getEmployeeName(),
                this.getTaskName(),
                this.getSecondsWorked(),
                (isInterrupted() ? "is not interrupted" : "is interrupted"),
                this.getDateAdded()
        );
    }
}
