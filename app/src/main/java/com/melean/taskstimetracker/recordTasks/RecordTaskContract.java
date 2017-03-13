package com.melean.taskstimetracker.recordTasks;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.data.models.TaskEntityModel;

import java.util.List;

public interface RecordTaskContract {
    interface View {
        void showTasksList(List<TaskModel> tasksNames);

        void showEmployeesList(List<EmployeeModel> employeesNames);

        void showErrorRecordIntend(boolean isRecording);

        void toggleTimeCounter(boolean isStarted);

        TaskEntityModel getTaskModel();
    }

    interface UserActionsListener {
        void loadTasks();

        void loadEmployees();

        void startRecording();

        void stopRecording();


    }
}
