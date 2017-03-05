package com.melean.taskstimetracker.data.repositories;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskRepositoryImplementation implements ITaskRepository{
    private RealmDatabase mRealmDatabase;
    private SimpleDateFormat nDateFormatter =
            new SimpleDateFormat("YYYY.MM.dd HH:mm", Locale.getDefault());

    public TaskRepositoryImplementation(RealmDatabase realmDatabase) {
        this.mRealmDatabase = realmDatabase;
    }

    @Override
    public TaskEntity makeTask(String employeeName, String taskName, long secondsWorked, boolean isInterrupted) {
        String dateAdded = nDateFormatter.format(System.currentTimeMillis());
        return new TaskEntity(employeeName, taskName, secondsWorked, isInterrupted, dateAdded);
    }

    @Override
    public void getAllTasks(@NonNull ITaskRepository.GetAllTasksCallback callback) {
        List<TaskEntity> results = mRealmDatabase.getAll(TaskEntity.class);
        callback.onTasksLoaded(results);
    }

    @Override
    public void getTask(int taskId, @NonNull ITaskRepository.GetTaskCallback callback) {
        TaskEntity result =
                mRealmDatabase.findAllByProperty(TaskEntity.class, "id", taskId).get(0);
        callback.onTaskLoaded(result);
    }

    @Override
    public void saveTask(@NonNull TaskEntity task) {
        mRealmDatabase.add(task);
    }

    @Override
    public void saveAllTasks(@NonNull List<TaskEntity> tasks) {
        mRealmDatabase.addAll(tasks);
    }

    @Override
    public SimpleDateFormat getDateFormatter() {
        return nDateFormatter;
    }
}
