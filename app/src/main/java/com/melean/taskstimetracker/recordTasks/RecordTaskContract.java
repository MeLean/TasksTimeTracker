package com.melean.taskstimetracker.recordTasks;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntity;

public interface RecordTaskContract {
    interface View {

        void showTaskPane();

        void showErrorRecordIntend(boolean isRecording);

        void startTimeCounter();

        void stopTimeCounter();

        void showTaskNamePiker();

        void showEmployeePicker();

        String getEmployeeName();

        String getTaskName();

        long getSecondsWorked();

        boolean isInterrupted();
    }

    interface UserActionsListener {

        void startRecording();

        void stopRecording();

        void pickTaskName();

        void pickAnEmployee();
    }
}
