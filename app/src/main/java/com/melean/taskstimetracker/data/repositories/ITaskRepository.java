package com.melean.taskstimetracker.data.repositories;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.data.database.RealmObjects.EmployeeRealmObject;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskRealmObject;
import com.melean.taskstimetracker.data.models.TaskEntityModel;

import java.text.SimpleDateFormat;
import java.util.List;

public interface ITaskRepository {

    interface GetAllTaskEntitiesCallback {
        void onTaskEntitiesLoaded(List<TaskEntityModel> taskEntityModels);
    }

    interface GetTaskEntityCallback {
        void onTaskEntityLoaded(TaskEntityModel taskEntityModel);
    }


    interface GetTasksCallback {
        void onTasksLoaded(List<TaskRealmObject> taskRealmObjects);
    }

    interface GetEmployeesCallback {
        void onEmployeesLoaded(List<EmployeeRealmObject> tasks);
    }

    void getAllTaskEntities(@NonNull GetAllTaskEntitiesCallback callback);

    void getTaskEntity(int taskId, @NonNull GetTaskEntityCallback callback);

    void saveTaskEntity(@NonNull TaskEntityModel task);

    void saveAllTaskEntities(List<TaskEntityModel> tasks);

    void getTasks(@NonNull GetTasksCallback callback);

    void getEmployees(@NonNull GetEmployeesCallback callback);

    SimpleDateFormat getDateFormatter();
}