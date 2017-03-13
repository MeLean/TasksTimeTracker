package com.melean.taskstimetracker.data.repositories;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskEntityModel;
import com.melean.taskstimetracker.data.models.TaskModel;

import java.text.SimpleDateFormat;
import java.util.List;

public interface ITaskRepository {

    interface GetAllTaskEntitiesCallback {
        void onTaskEntitiesLoaded(List<TaskEntityModel> taskEntityModels);
    }

    interface GetTaskEntityCallback {
        void onTaskEntityLoaded(TaskEntityModel taskEntityModel);
    }


    interface LoadTasksCallback {
        void onTasksLoaded(List<TaskModel> tasks);
    }

    interface LoadEmployeesCallback {
        void onEmployeesLoaded(List<EmployeeModel> employees);
    }

    void getAllTaskEntities(@NonNull GetAllTaskEntitiesCallback callback);

    void getTaskEntity(int taskId, @NonNull GetTaskEntityCallback callback);

    void saveTaskEntity(@NonNull TaskEntityModel task);

    void saveAllTaskEntities(List<TaskEntityModel> tasks);

    void getTasks(@NonNull LoadTasksCallback callback);

    void getEmployees(@NonNull LoadEmployeesCallback callback);

    SimpleDateFormat getDateFormatter();
}