package com.melean.taskstimetracker.recordTasks;

import com.melean.taskstimetracker.data.models.TaskEntityModel;

import java.util.HashMap;

public interface RecordTaskContract {
    interface View {

        void showErrorRecordIntend(boolean isRecording);

        void toggleTimeCounter(boolean isStarted);

        TaskEntityModel getTaskModel();
    }

    interface UserActionsListener {

        void startRecording();

        void stopRecording();
    }
}
