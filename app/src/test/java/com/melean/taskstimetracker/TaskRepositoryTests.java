package com.melean.taskstimetracker;


import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntity;
import com.melean.taskstimetracker.data.repositories.ITaskRepository;
import com.melean.taskstimetracker.data.repositories.TaskRepositoryImplementation;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TaskRepositoryTests {
    ITaskRepository mTaskRepository;

    @Mock
    RealmDatabase mMockedRealmDatabase;

    public TaskRepositoryTests(){

    }

    @Before
    public void initData() {
        //init mockito
        MockitoAnnotations.initMocks(this);
        mTaskRepository = new TaskRepositoryImplementation(mMockedRealmDatabase);
    }

    @Test
    public void onMakeTask_TaskMade() {
        String empName = "Employ Name";
        String taskName = "Task Name";
        Long workedTime = 14000l;

        TaskEntity entity =  mTaskRepository.makeTask(empName, taskName, workedTime, false);
        assertNotNull(entity);
    }

    @Test
    public void onSaveTask_TaskSaved() {
        TaskEntity entity = new TaskEntity();
        mTaskRepository.saveTask(entity);
        verify(mMockedRealmDatabase).add(entity);
    }

}
