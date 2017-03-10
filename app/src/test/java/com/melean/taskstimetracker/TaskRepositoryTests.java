package com.melean.taskstimetracker;


import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntityRealmObject;
import com.melean.taskstimetracker.data.models.TaskEntityModel;
import com.melean.taskstimetracker.data.repositories.ITaskRepository;
import com.melean.taskstimetracker.data.repositories.TaskRepositoryImplementation;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;


public class TaskRepositoryTests {
    private static final String EMPLOYEE_NAME = "Employ Name";
    private static final String TASK_NAME = "Task Name";
    private static final Long WORKED_TIME = 14000L;

    ITaskRepository mTaskRepository;
    List<TaskEntityModel> mTestListTaskEntities;

    @Captor
    ArgumentCaptor<List<TaskEntityRealmObject>> listCaptor;

    @Mock
    RealmDatabase mMockedRealmDatabase;

    public TaskRepositoryTests(){
        //empty constructor
    }

    @Before
    public void initData() {
        //init mockito
        MockitoAnnotations.initMocks(this);
        mTaskRepository = new TaskRepositoryImplementation(mMockedRealmDatabase);
        String currentDate = mTaskRepository.getDateFormatter().format(System.currentTimeMillis());
        mTestListTaskEntities = new ArrayList<>();
        for (int i = 0; i <= 3; i++){
            TaskEntityModel model = new TaskEntityModel(EMPLOYEE_NAME, i, TASK_NAME, WORKED_TIME, false, currentDate);
            mTestListTaskEntities.add(model);
        }

    }

    @Test
    public void onSaveTask_ShouldSaveTask() {
        TaskEntityModel entity = mTestListTaskEntities.get(0);
        mTaskRepository.saveTask(entity);
        ArgumentCaptor<TaskEntityRealmObject> realmCaptor = ArgumentCaptor.forClass(TaskEntityRealmObject.class);
        verify(mMockedRealmDatabase).add(realmCaptor.capture());
        assertNotNull(realmCaptor.getValue());

    }

/*    @Test (expected = AssertionError.class)
    public void onSaveTask_ShouldThrowException() {
        TaskEntityRealmObject noEntity = null;
        mTaskRepository.saveTask(noEntity);
        doThrow(new AssertionError()).when(mMockedRealmDatabase).add(noEntity);
    }*/


    @Test
    public void onSaveAllTasks_ShouldSaveTasks() {
        mTaskRepository.saveAllTasks(mTestListTaskEntities);
        //verify(mMockedRealmDatabase).addAll(mTestListTaskEntities);
    }

     @Test
    public void onGetTask_ShouldGetTask() {
         when(mMockedRealmDatabase.copyAllByProperty(TaskEntityRealmObject.class, "id", 1))
                 .thenAnswer(new Answer<List<TaskEntityRealmObject>>() {
                     @Override
                     public List<TaskEntityRealmObject> answer(InvocationOnMock invocation) throws Throwable {
                         List<TaskEntityRealmObject> taskList = new ArrayList<>();
                         taskList.add(TaskEntityRealmObject.makeFrom(mTestListTaskEntities.get(1)));
                         return taskList;
                     }
                 });

         mTaskRepository.getTask(1, new ITaskRepository.GetTaskCallback() {
             @Override
             public void onTaskLoaded(TaskEntityModel task) {
                 assertEquals(mTestListTaskEntities.get(1), task);
             }
        });
    }

    @Test
    public void onGetAllTasks_ShouldSaveTasks() {
        when(mMockedRealmDatabase.copyAll(TaskEntityRealmObject.class)).thenAnswer(new Answer<List<TaskEntityModel>>() {
            @Override
            public List<TaskEntityModel> answer(InvocationOnMock invocation) throws Throwable {
                return mTestListTaskEntities;
            }
        });

        mTaskRepository.getAllTasks(new ITaskRepository.GetAllTasksCallback() {
            @Override
            public void onTasksLoaded(List<TaskEntityModel> tasks) {
                assertEquals(mTestListTaskEntities, tasks);
            }
        });
    }
}
