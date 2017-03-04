package com.melean.taskstimetracker.data.repositories;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntity;

import java.util.List;

public interface ITaskRepository {

    void saveTask(String employeeName, String taskName, long secondsWorked, boolean isInterrupted);

    interface LoadTasksCallback {
        void onNotesLoaded(List<TaskEntity> tasks);
    }

    interface GetTaskCallback {

        void onNoteLoaded(TaskEntity note);
    }

    void getAllTasks(@NonNull LoadTasksCallback callback);

    void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback);

    void saveTask(@NonNull TaskEntity tasks);

    void saveAllTasks(List<TaskEntity> tasks);

    void refreshData();
}
