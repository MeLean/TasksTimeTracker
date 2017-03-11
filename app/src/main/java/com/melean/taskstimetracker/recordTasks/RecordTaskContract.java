package com.melean.taskstimetracker.recordTasks;

import com.melean.taskstimetracker.data.models.TaskEntityModel;

import java.util.HashMap;
import java.util.List;

public interface RecordTaskContract {
    interface View {
        void loadTasksList(List<String> tasksNames);

        void loadEmployeesList(List<String> employeesNames);

        void showErrorRecordIntend(boolean isRecording);

        void toggleTimeCounter(boolean isStarted);

        TaskEntityModel getTaskModel();
    }

    interface UserActionsListener {
        List<String> getTasksNames();

        List<String> getEmployeeNames();

        void startRecording();

        void stopRecording();


    }
}
