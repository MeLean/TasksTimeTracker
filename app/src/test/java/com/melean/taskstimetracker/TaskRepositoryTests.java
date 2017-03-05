package com.melean.taskstimetracker;


import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase;
import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntity;
import com.melean.taskstimetracker.data.repositories.ITaskRepository;
import com.melean.taskstimetracker.data.repositories.TaskRepositoryImplementation;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
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
    List<TaskEntity> mTestListTaskEntities;

    @Captor
    ArgumentCaptor<List<TaskEntity>> listCaptor;

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
            mTestListTaskEntities.add(new TaskEntity(EMPLOYEE_NAME, i, TASK_NAME, WORKED_TIME, false, currentDate));
        }

    }

    @Test
    public void onMakeTask_ShouldReturnTask() {
        TaskEntity entity =  mTaskRepository.makeTask(EMPLOYEE_NAME, TASK_NAME, WORKED_TIME, false);
        assertNotNull(entity);
    }

    @Test
    public void onSaveTask_ShouldSaveTask() {
        TaskEntity entity = mTestListTaskEntities.get(0);
        mTaskRepository.saveTask(entity);
        verify(mMockedRealmDatabase).add(entity);
    }

/*    @Test (expected = AssertionError.class)
    public void onSaveTask_ShouldThrowException() {
        TaskEntity noEntity = null;
        mTaskRepository.saveTask(noEntity);
        doThrow(new AssertionError()).when(mMockedRealmDatabase).add(noEntity);
    }*/


    @Test
    public void onSaveAllTasks_ShouldSaveTasks() {
        mTaskRepository.saveAllTasks(mTestListTaskEntities);
        verify(mMockedRealmDatabase).addAll(mTestListTaskEntities);
    }

   /*  @Test
    public void onGetTask_ShouldGetTask() {
        ArgumentCaptor<TaskRepositoryImplementation.GetTaskCallback> getTaskCaptor =
                ArgumentCaptor.forClass(TaskRepositoryImplementation.GetTaskCallback.class);
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);

        mTaskRepository.getTask(1, new ITaskRepository.GetTaskCallback() {
            @Override
            public void onTaskLoaded(TaskEntity task) {
                //do nothing
            }
        });

        verify(mMockedRealmDatabase).findAllByProperty(TaskEntity.class, "id", 1);
        when(mMockedRealmDatabase.findAllByProperty(TaskEntity.class, "id", 1).get(0))
                .thenAnswer(new Answer<TaskEntity>() {
            @Override
            public TaskEntity answer(InvocationOnMock invocation) throws Throwable {
                return mTestListTaskEntities.get(1);
            }
        });

        assertEquals(mTestListTaskEntities, getTaskCaptor.getValue());

    }*/

    @Test
    public void onGetAllTasks_ShouldSaveTasks() {
        ArgumentCaptor<ITaskRepository.GetAllTasksCallback> getTasksCaptor =
                ArgumentCaptor.forClass(ITaskRepository.GetAllTasksCallback.class);

        mTaskRepository.getAllTasks(getTasksCaptor.capture());
        getTasksCaptor.capture().onTasksLoaded(listCaptor.capture());
        when(mMockedRealmDatabase.findAll(TaskEntity.class)).thenAnswer(new Answer<List<TaskEntity>>() {
            @Override
            public List<TaskEntity> answer(InvocationOnMock invocation) throws Throwable {
         /*       Object[] args = invocation.getArguments();
                return (List<TaskEntity>) args[0];*/
                return mTestListTaskEntities;
            }
        });

        assertEquals(mTestListTaskEntities, listCaptor.getValue());
    }

}
