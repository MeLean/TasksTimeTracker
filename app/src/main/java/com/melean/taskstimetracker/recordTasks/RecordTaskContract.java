package com.melean.taskstimetracker.recordTasks;

import com.melean.taskstimetracker.data.database.RealmObjects.EmployeeRealmObject;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskRealmObject;
import com.melean.taskstimetracker.data.models.TaskEntityModel;

import java.util.List;

public interface RecordTaskContract {
    interface View {
        void loadTasksList(List<TaskRealmObject> tasksNames);

        void loadEmployeesList(List<EmployeeRealmObject> employeesNames);

        void showErrorRecordIntend(boolean isRecording);

        void toggleTimeCounter(boolean isStarted);

        TaskEntityModel getTaskModel();
    }

    interface UserActionsListener {
        List<TaskRealmObject> getTasksNames();

        List<EmployeeRealmObject> getEmployeeNames();

        void startRecording();

        void stopRecording();


    }
}
