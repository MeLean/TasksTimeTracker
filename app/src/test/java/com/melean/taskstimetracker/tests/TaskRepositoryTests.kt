package com.melean.taskstimetracker.tests


import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase
import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntityRealmObject
import com.melean.taskstimetracker.data.database.RealmObjects.TaskRealmObject
import com.melean.taskstimetracker.data.models.TaskEntityModel
import com.melean.taskstimetracker.data.models.TaskModel
import com.melean.taskstimetracker.data.repositories.ITaskRepository
import com.melean.taskstimetracker.data.repositories.TaskRepositoryImplementation

import org.junit.Before
import org.junit.Test

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import java.util.ArrayList


class TaskRepositoryTests {

    private var mTaskRepository: ITaskRepository? = null
    private var mTestEntityModelsList: MutableList<TaskEntityModel>? = null
    private var mTestRealmEntitiesList: MutableList<TaskEntityRealmObject>? = null
    private var mTestTasksRealmList: MutableList<TaskRealmObject>? = null

    @Mock
    internal var mMockedRealmDatabase: RealmDatabase? = null

    @Captor
    internal var captorTaskRealmObjectList: ArgumentCaptor<List<TaskRealmObject>>? = null

    @Before
    fun initData() {
        //init mockito
        MockitoAnnotations.initMocks(this)
        mTaskRepository = TaskRepositoryImplementation(mMockedRealmDatabase!!)
        val currentDate = mTaskRepository!!.dateFormatter.format(System.currentTimeMillis())
        mTestEntityModelsList = ArrayList()
        mTestRealmEntitiesList = ArrayList()
        mTestTasksRealmList = ArrayList()

        for (i in 0..3) {
            val model = TaskEntityModel(EMPLOYEE_NAME + " - " + i, i, TASK_NAME + " - " + i, WORKED_TIME, false, currentDate)
            mTestEntityModelsList!!.add(model)
            mTestRealmEntitiesList!!.add(TaskEntityRealmObject.makeFrom(model))
            val task = TaskRealmObject()
            task.taskName = TASK_NAME + " - " + i
            mTestTasksRealmList!!.add(task)
        }

    }

    @Test
    fun onSaveTask_ShouldSaveTask() {
        val entity = mTestEntityModelsList!![0]
        mTaskRepository!!.saveTaskEntity(entity)
        val realmCaptor = ArgumentCaptor.forClass(TaskEntityRealmObject::class.java)
        verify(mMockedRealmDatabase)?.add(realmCaptor.capture())
        assertNotNull(realmCaptor.value)

    }

    @Test
    fun onSaveAllTasks_ShouldSaveTasks() {
        mTaskRepository!!.saveAllTaskEntities(mTestEntityModelsList!!)

        verify<RealmDatabase>(mMockedRealmDatabase).addAll(captorTaskRealmObjectList!!.capture())
        assertNotNull(captorTaskRealmObjectList!!.value)
    }

    @Test
    fun onGetTask_ShouldGetTask() {
        val propertyName = "id"
        val propertyValue = 1
        `when`<List<TaskEntityRealmObject>>(mMockedRealmDatabase!!.copyAllByProperty(TaskEntityRealmObject::class.java, propertyName, propertyValue))
                .thenAnswer {
                    val taskList = ArrayList<TaskEntityRealmObject>()
                    taskList.add(mTestRealmEntitiesList!![1])
                    taskList
                }

        mTaskRepository!!.getTaskEntity(1, object : ITaskRepository.GetTaskEntityCallback {
            override fun onTaskEntityLoaded(taskEntityModel: TaskEntityModel) {
                assertNotNull(taskEntityModel)
            }
        })

        verify<RealmDatabase>(mMockedRealmDatabase).copyAllByProperty(TaskEntityRealmObject::class.java, propertyName, propertyValue)
    }

    @Test
    fun onGetAllTasks_ShouldSaveTasks() {
        `when`<List<TaskEntityRealmObject>>(mMockedRealmDatabase!!.copyAll(TaskEntityRealmObject::class.java))
                .thenAnswer { mTestRealmEntitiesList }

        mTaskRepository!!.getAllTaskEntities(object : ITaskRepository.GetAllTaskEntitiesCallback {
            override fun onTaskEntitiesLoaded(taskEntityModels: List<TaskEntityModel>) {
                assertNotNull(taskEntityModels)
            }
        })

        verify<RealmDatabase>(mMockedRealmDatabase).copyAll(TaskEntityRealmObject::class.java)
    }

    @Test
    fun onGetTasks_ReturnTasks() {
        `when`<List<TaskRealmObject>>(mMockedRealmDatabase!!.copyAll(TaskRealmObject::class.java))
                .thenAnswer { mTestTasksRealmList }

        mTaskRepository!!.getTasks(object : ITaskRepository.LoadTasksCallback {
            override fun onTasksLoaded(tasks: MutableList<TaskModel>) {
                assertNotNull(tasks)
                for (i in tasks.indices) {
                    val expected = mTestTasksRealmList!![i]
                    val (taskName) = tasks[i]

                    assertEquals(expected.taskName, taskName)
                }
            }
        })

        verify<RealmDatabase>(mMockedRealmDatabase).copyAll(TaskRealmObject::class.java)
    }

    companion object {
        private val EMPLOYEE_NAME = "Employ Name"
        private val TASK_NAME = "Task Name"
        private val WORKED_TIME = 14000L
    }
}//empty constructor
