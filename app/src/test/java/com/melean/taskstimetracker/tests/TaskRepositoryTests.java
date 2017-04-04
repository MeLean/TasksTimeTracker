package com.melean.taskstimetracker.tests;


import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntityRealmObject;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskRealmObject;
import com.melean.taskstimetracker.data.models.TaskEntityModel;
import com.melean.taskstimetracker.data.models.TaskModel;
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

    private ITaskRepository mTaskRepository;
    private List<TaskEntityModel> mTestEntityModelsList;
    private List<TaskEntityRealmObject> mTestRealmEntitiesList;
    private List<TaskRealmObject> mTestTasksRealmList;

    @Mock
    RealmDatabase mMockedRealmDatabase;

    @Captor
    ArgumentCaptor<List<TaskRealmObject>> captorTaskRealmObjectList;

    public TaskRepositoryTests(){
        //empty constructor
    }

    @Before
    public void initData() {
        //init mockito
        MockitoAnnotations.initMocks(this);
        mTaskRepository = new TaskRepositoryImplementation(mMockedRealmDatabase);
        String currentDate = mTaskRepository.getDateFormatter().format(System.currentTimeMillis());
        mTestEntityModelsList = new ArrayList<>();
        mTestRealmEntitiesList = new ArrayList<>();
        mTestTasksRealmList = new ArrayList<>();

        for (int i = 0; i <= 3; i++){
            TaskEntityModel model = new TaskEntityModel(EMPLOYEE_NAME + " - "+ i, i, TASK_NAME + " - "+ i, WORKED_TIME, false, currentDate);
            mTestEntityModelsList.add(model);
            mTestRealmEntitiesList.add(TaskEntityRealmObject.makeFrom(model));
            mTestTasksRealmList.add(new TaskRealmObject( TASK_NAME + " - "+ i));
        }

    }

    @Test
    public void onSaveTask_ShouldSaveTask() {
        TaskEntityModel entity = mTestEntityModelsList.get(0);
        mTaskRepository.saveTaskEntity(entity);
        ArgumentCaptor<TaskEntityRealmObject> realmCaptor = ArgumentCaptor.forClass(TaskEntityRealmObject.class);
        verify(mMockedRealmDatabase).add(realmCaptor.capture());
        assertNotNull(realmCaptor.getValue());

    }

    @Test
    public void onSaveAllTasks_ShouldSaveTasks() {
        mTaskRepository.saveAllTaskEntities(mTestEntityModelsList);

        verify(mMockedRealmDatabase).addAll(captorTaskRealmObjectList.capture());
        assertNotNull(captorTaskRealmObjectList.getValue());
    }

     @Test
    public void onGetTask_ShouldGetTask() {
         String propertyName = "id";
         int propertyValue = 1;
         when(mMockedRealmDatabase.copyAllByProperty(TaskEntityRealmObject.class, propertyName, propertyValue))
                 .thenAnswer(new Answer<List<TaskEntityRealmObject>>() {
                     @Override
                     public List<TaskEntityRealmObject> answer(InvocationOnMock invocation) throws Throwable {
                         List<TaskEntityRealmObject> taskList = new ArrayList<>();
                         taskList.add(mTestRealmEntitiesList.get(1));
                         return taskList;
                     }
                 });

         mTaskRepository.getTaskEntity(1, new ITaskRepository.GetTaskEntityCallback() {
             @Override
             public void onTaskEntityLoaded(TaskEntityModel taskEntityModel) {
                 assertNotNull(taskEntityModel);
             }
        });

         verify(mMockedRealmDatabase).copyAllByProperty(TaskEntityRealmObject.class, propertyName, propertyValue);
    }

    @Test
    public void onGetAllTasks_ShouldSaveTasks() {
        when(mMockedRealmDatabase.copyAll(TaskEntityRealmObject.class))
                .thenAnswer(new Answer<List<TaskEntityRealmObject>>() {
            @Override
            public List<TaskEntityRealmObject> answer(InvocationOnMock invocation) throws Throwable {
                return mTestRealmEntitiesList;
            }
        });

        mTaskRepository.getAllTaskEntities(new ITaskRepository.GetAllTaskEntitiesCallback() {
            @Override
            public void onTaskEntitiesLoaded(List<TaskEntityModel> taskEntityModels) {
                assertNotNull(taskEntityModels);
            }
        });

        verify(mMockedRealmDatabase).copyAll(TaskEntityRealmObject.class);
    }

    @Test
    public void onGetTasks_ReturnTasks() {
        when(mMockedRealmDatabase.copyAll(TaskRealmObject.class))
                .thenAnswer(new Answer<List<TaskRealmObject>>() {
                    @Override
                    public List<TaskRealmObject> answer(InvocationOnMock invocation) throws Throwable {
                        return mTestTasksRealmList;
                    }
                });

        mTaskRepository.getTasks(new ITaskRepository.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<TaskModel> taskModels) {
                assertNotNull(taskModels);
                for (int i=0; i < taskModels.size(); i++){
                    TaskRealmObject expected = mTestTasksRealmList.get(i);
                    TaskModel actual = taskModels.get(i);

                    assertEquals(expected.getTaskName(),actual.getTaskName());
                }
            }
        });

        verify(mMockedRealmDatabase).copyAll(TaskRealmObject.class);
    }
}
