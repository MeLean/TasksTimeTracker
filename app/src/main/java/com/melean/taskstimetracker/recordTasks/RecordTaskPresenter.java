package com.melean.taskstimetracker.recordTasks;

import com.melean.taskstimetracker.data.database.RealmObjects.TaskRealmObject;
import com.melean.taskstimetracker.data.models.TaskModel;
import com.melean.taskstimetracker.data.repositories.ITaskRepository;

import java.util.List;

public class RecordTaskPresenter implements RecordTaskContract.UserActionsListener {
    private RecordTaskContract.View recordTaskView;
    private ITaskRepository taskRepository;
    public boolean isRecording;

    public RecordTaskPresenter(RecordTaskContract.View recordTaskView, ITaskRepository taskRepository) {
        this.recordTaskView = recordTaskView;
        this.taskRepository = taskRepository;
        this.isRecording = false;
    }


    @Override
    public void displayTasks() {
        taskRepository.getTasks(new ITaskRepository.GetTasksCallback() {
            @Override
            public void onTasksLoaded(List<TaskModel> tasks) {
                recordTaskView.loadTasksList(tasks);
            }
        });
    }

    @Override
    public void getEmployees() {

    }

    @Override
    public void startRecording() {
        if (!isRecording) {
            recordTaskView.toggleTimeCounter(true);
            isRecording = true;
        } else {
            recordTaskView.showErrorRecordIntend(true);
        }

    }

    @Override
    public void stopRecording() {
        if (isRecording) {
            recordTaskView.toggleTimeCounter(false);
            isRecording = false;
            taskRepository.saveTaskEntity(recordTaskView.getTaskModel());
        } else {
            recordTaskView.showErrorRecordIntend(false);
        }
    }
}
