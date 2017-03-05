package com.melean.taskstimetracker.data.repositories;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntity;

import java.text.SimpleDateFormat;
import java.util.List;

public interface ITaskRepository {
    public static final String dateFormat = "YYYY.MM.dd HH:mm";

    TaskEntity makeTask(String employeeName, String taskName, long secondsWorked, boolean isInterrupted);

    interface GetAllTasksCallback {
        void onTasksLoaded(List<TaskEntity> tasks);
    }

    interface GetTaskCallback {
        void onTaskLoaded(TaskEntity task);
    }

    void getAllTasks(@NonNull GetAllTasksCallback callback);

    void getTask(int taskId, @NonNull GetTaskCallback callback);

    void saveTask(@NonNull TaskEntity task);

    void saveAllTasks(List<TaskEntity> tasks);

    SimpleDateFormat getDateFormatter();
}
