package com.melean.taskstimetracker.recordTasks;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.data.models.TaskEntityModel;

import java.util.List;

public interface RecordTaskContract {
    interface View {
        void loadTasksList(List<TaskModel> tasksNames);

        void loadEmployeesList(List<EmployeeModel> employeesNames);

        void showErrorRecordIntend(boolean isRecording);

        void toggleTimeCounter(boolean isStarted);

        TaskEntityModel getTaskModel();
    }

    interface UserActionsListener {
        void displayTasks();

        void getEmployees();

        void startRecording();

        void stopRecording();


    }
}
