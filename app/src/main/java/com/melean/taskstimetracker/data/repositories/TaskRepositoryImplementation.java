package com.melean.taskstimetracker.data.repositories;

import android.support.annotation.NonNull;

import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntityRealmObject;
import com.melean.taskstimetracker.data.models.TaskEntityModel;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.exceptions.RealmException;

public class TaskRepositoryImplementation implements ITaskRepository{
    private RealmDatabase mRealmDatabase;
    private SimpleDateFormat nDateFormatter =
            new SimpleDateFormat("YYYY.MM.dd HH:mm", Locale.getDefault());

    public TaskRepositoryImplementation(RealmDatabase realmDatabase) {
        this.mRealmDatabase = realmDatabase;
    }


    @Override
    public void getAllTasks(@NonNull ITaskRepository.GetAllTasksCallback callback) {
        callback.onTasksLoaded(makeModelList(mRealmDatabase.copyAll(TaskEntityRealmObject.class)));
    }

    @Override
    public void getTask(int taskId, @NonNull ITaskRepository.GetTaskCallback callback) {
        List<TaskEntityRealmObject> results = mRealmDatabase.copyAllByProperty(TaskEntityRealmObject.class, "id", taskId);

        if(results.size() == 1){
            callback.onTaskLoaded(TaskEntityModel.makeFrom(results.get(0)));
        }else{
            if(results.size() > 1){
                throw new RealmException("More than one entity found!");
            }
        }

    }

    @Override
    public void saveTask(@NonNull TaskEntityModel task) {
        mRealmDatabase.add(TaskEntityRealmObject.makeFrom(task));
    }

    @Override
    public void saveAllTasks(@NonNull List<TaskEntityModel> tasks) {
        mRealmDatabase.addAll(makeRealmObjectList(tasks));
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
}
