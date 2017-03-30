package com.melean.taskstimetracker.data.repositories;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.constants.Preferences;
import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase;
import com.melean.taskstimetracker.data.database.RealmObjects.EmployeeRealmObject;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntityRealmObject;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskRealmObject;
import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskEntityModel;
import com.melean.taskstimetracker.data.models.TaskModel;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.exceptions.RealmException;

public class TaskRepositoryImplementation implements ITaskRepository{
    private RealmDatabase mRealmDatabase;
    private SimpleDateFormat nDateFormatter =
            new SimpleDateFormat(Preferences.ENTITY_DATE_FORMAT, Locale.getDefault());

    public TaskRepositoryImplementation(RealmDatabase realmDatabase) {
        this.mRealmDatabase = realmDatabase;
    }


    @Override
    public void getAllTaskEntities(@NonNull GetAllTaskEntitiesCallback callback) {
        callback.onTaskEntitiesLoaded(makeModelList(mRealmDatabase.copyAll(TaskEntityRealmObject.class)));
    }

    @Override
    public void getTaskEntity(int taskId, @NonNull GetTaskEntityCallback callback) {
        List<TaskEntityRealmObject> results = mRealmDatabase.copyAllByProperty(TaskEntityRealmObject.class, "id", taskId);

        if(results.size() == 1){
            callback.onTaskEntityLoaded(TaskEntityModel.makeFrom(results.get(0)));
        }else{
            if(results.size() > 1){
                throw new RealmException("More than one entity found!");
            }
        }
    }


    @Override
    public void saveEmployee(EmployeeModel employeeModel) {
        mRealmDatabase.add(EmployeeRealmObject.from(employeeModel));
    }

    @Override
    public void saveTaskEntity(@NonNull TaskEntityModel task) {
        mRealmDatabase.add(TaskEntityRealmObject.makeFrom(task));
    }

    @Override
    public void saveAllTaskEntities(@NonNull List<TaskEntityModel> tasks) {
        mRealmDatabase.addAll(makeRealmObjectList(tasks));
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        callback.onTasksLoaded(
                makeTaskModelList(mRealmDatabase.copyAll(TaskRealmObject.class))
        );
    }

    @Override
    public void getEmployees(@NonNull LoadEmployeesCallback callback) {
        callback.onEmployeesLoaded(
                makeEmployeeList(mRealmDatabase.copyAll(EmployeeRealmObject.class))
        );
    }

    @Override
    public SimpleDateFormat getDateFormatter() {
        return nDateFormatter;
    }

    private static List<TaskEntityRealmObject> makeRealmObjectList(@NotNull List<TaskEntityModel> models) {
        List<TaskEntityRealmObject> resultList = new ArrayList<>();
        for (TaskEntityModel element : models) {
            resultList.add(TaskEntityRealmObject.makeFrom(element));
        }

        return resultList;
    }

    private static List<TaskEntityModel> makeModelList(@NotNull List<TaskEntityRealmObject> realmObjects) {
        List<TaskEntityModel> resultList = new ArrayList<>();
        for (TaskEntityRealmObject element : realmObjects) {
            resultList.add(TaskEntityModel.makeFrom(element));
        }

        return resultList;
    }

    private List<EmployeeModel> makeEmployeeList(List<EmployeeRealmObject> employeeRealmObjects) {
        List<EmployeeModel> resultList = new ArrayList<>();
        for (EmployeeRealmObject element : employeeRealmObjects) {
            resultList.add(new EmployeeModel(element.getEmployeeName()));
        }

        return resultList;
    }

    private static List<TaskModel> makeTaskModelList(@NotNull List<TaskRealmObject> realmObjects) {
        List<TaskModel> resultList = new ArrayList<>();
        for (TaskRealmObject element : realmObjects) {
            resultList.add(new TaskModel(element.getTaskName()));
        }

        return resultList;
    }
}
