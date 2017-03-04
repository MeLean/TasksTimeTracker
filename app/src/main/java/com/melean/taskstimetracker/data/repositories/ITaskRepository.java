package com.melean.taskstimetracker.data.repositories;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntity;

import java.util.List;

public interface ITaskRepository {
    public static final String dateFormat = "YYYY.MM.dd HH:mm";

    TaskEntity makeTask(String employeeName, String taskName, long secondsWorked, boolean isInterrupted);

    interface LoadTasksCallback {
        void onNotesLoaded(List<TaskEntity> tasks);
    }

    interface GetTaskCallback {
        void onNoteLoaded(TaskEntity note);
    }

    void getAllTasks(@NonNull LoadTasksCallback callback);

    void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback);

    void saveTask(@NonNull TaskEntity task);

    void saveAllTasks(List<TaskEntity> tasks);

    void refreshData();
}
