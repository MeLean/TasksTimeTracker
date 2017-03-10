package com.melean.taskstimetracker.data.repositories;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.data.models.TaskEntityModel;

import java.text.SimpleDateFormat;
import java.util.List;

public interface ITaskRepository {
    public static final String dateFormat = "YYYY.MM.dd HH:mm";
    
    interface GetAllTasksCallback {
        void onTasksLoaded(List<TaskEntityModel> tasks);
    }

    interface GetTaskCallback {
        void onTaskLoaded(TaskEntityModel task);
    }

    void getAllTasks(@NonNull GetAllTasksCallback callback);

    void getTask(int taskId, @NonNull GetTaskCallback callback);

    void saveTask(@NonNull TaskEntityModel task);

    void saveAllTasks(List<TaskEntityModel> tasks);

    SimpleDateFormat getDateFormatter();
}
