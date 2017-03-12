package com.melean.taskstimetracker.data.database.RealmObjects;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.data.models.TaskEntityModel;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class TaskEntityRealmObject extends RealmObject {

    @PrimaryKey
    private int _id;
    @Required
    private String employeeName;
    @Index
    @Required
    private String taskName;
    private long secondsWorked;
    private boolean isInterrupted;
    @Required
    private String dateAdded;



    public TaskEntityRealmObject(){
    }


    public static TaskEntityRealmObject makeFrom(@NonNull TaskEntityModel model) {
        TaskEntityRealmObject realmObject = new  TaskEntityRealmObject();
        realmObject._id = model.getId();
        realmObject.employeeName = model.getEmployeeName();
        realmObject.taskName = model.getTaskName();
        realmObject.secondsWorked = model.getSecondsWorked();
        realmObject.isInterrupted = model.isInterrupted();
        realmObject.dateAdded = model.getDateAdded();
        return realmObject;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employee) {
        this.employeeName = employee;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
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

    public void setIsInterrupted(boolean isInterrupted) {
        this.isInterrupted = isInterrupted;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

}
