package com.melean.taskstimetracker.data.database.RealmObjects;



import com.melean.taskstimetracker.data.models.TaskModel;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.Required;

public class TaskRealmObject extends RealmObject{

    @Required
    @Index
    private String taskName;


    public TaskRealmObject() {
    }

    public TaskRealmObject(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public static RealmObject from(TaskModel taskModel) {
        TaskRealmObject object = new  TaskRealmObject();
        object.taskName = taskModel.getTaskName();
        return object;
    }
}
