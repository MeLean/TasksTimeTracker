package com.melean.taskstimetracker.data.repositories;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class TaskRepositoryImplementation implements ITaskRepository{
    public static final String dateFormat = "YYYY.MM.dd HH:mm";
    private RealmDatabase mRealmDatabase;

    public TaskRepositoryImplementation(RealmDatabase realmDatabase) {
        this.mRealmDatabase = realmDatabase;
    }

    @Override
    public TaskEntity makeTask(String employeeName, String taskName, long secondsWorked, boolean isInterrupted) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TaskRepositoryImplementation.dateFormat, Locale.getDefault());
        String dateAdded = dateFormat.format(System.currentTimeMillis());
        return new TaskEntity(employeeName, taskName, secondsWorked, isInterrupted, dateAdded);
    }

    @Override
    public void getAllTasks(@NonNull LoadTasksCallback callback) {

    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {

    }

    @Override
    public void saveTask(@NonNull TaskEntity task) {
        mRealmDatabase.add(task);
    }

    @Override
    public void saveAllTasks(List<TaskEntity> tasks) {

    }

    @Override
    public void refreshData() {

    }
}
