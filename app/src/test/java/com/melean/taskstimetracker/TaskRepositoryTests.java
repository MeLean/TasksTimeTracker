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
import static org.powermock.api.mockito.PowerMockito.doAnswer;

import org.mockito.ArgumentCaptor;
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

    @Test
    public void onGetTask_ShouldGetTask() {
        ArgumentCaptor<ITaskRepository.GetTaskCallback> getTaskCaptor =
                ArgumentCaptor.forClass(ITaskRepository.GetTaskCallback.class);
         mTaskRepository.getTask(1, getTaskCaptor.capture());

        //todo
        //when(mMockedRealmDatabase.findAllByProperty(TaskEntity.class, "id",1));

    }


    @Test
    public void onGetAllTasks_ShouldSaveTasks() {
        ArgumentCaptor<ITaskRepository.GetAllTasksCallback> getTasksCaptor =
                ArgumentCaptor.forClass(ITaskRepository.GetAllTasksCallback.class);

        mTaskRepository.getAllTasks(getTasksCaptor.capture());

        //todo
        doAnswer(new Answer() {
            public List<TaskEntity> answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Mock mock = (Mock) invocation.getMock();
                return null;
            }}).when(mMockedRealmDatabase.findAll(TaskEntity.class));
        assertEquals(mTestListTaskEntities, getTasksCaptor.getValue());
    }

}
