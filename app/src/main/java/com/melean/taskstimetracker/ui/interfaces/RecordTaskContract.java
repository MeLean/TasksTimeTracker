package com.melean.taskstimetracker.ui.interfaces;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.data.models.TaskEntityModel;
import com.melean.taskstimetracker.ui.enums.ApplicationError;

import java.util.List;

public interface RecordTaskContract {
    interface View {
        void showTasksList(List<TaskModel> tasksNames);

        void showEmployeesList(List<EmployeeModel> employeesNames);

        void showErrorRecordIntend(ApplicationError applicationError);

        void toggleRecording(boolean isInterrupted);

        TaskEntityModel getTaskModel();
    }

    interface UserActionsListener {
        void loadTasks();

        void loadEmployees();

        void startRecording();

        void stopRecording(boolean isInterrupted);
    }
}
