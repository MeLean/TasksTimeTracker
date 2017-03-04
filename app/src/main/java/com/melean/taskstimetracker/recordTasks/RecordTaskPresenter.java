package com.melean.taskstimetracker.recordTasks;

import com.melean.taskstimetracker.data.repositories.ITaskRepository;

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
    public void startRecording() {
        if (!isRecording) {
            recordTaskView.startTimeCounter();
            isRecording = true;
        } else {
            recordTaskView.showErrorRecordIntend(true);
        }

    }

    @Override
    public void stopRecording() {
        if (isRecording) {
            recordTaskView.stopTimeCounter();
            isRecording = false;

            taskRepository.saveTask(
                    recordTaskView.getEmployeeName(),
                    recordTaskView.getTaskName(),
                    recordTaskView.getSecondsWorked(),
                    recordTaskView.isInterrupted()
            );
        } else {
            recordTaskView.showErrorRecordIntend(false);
        }
    }

    @Override
    public void pickTaskName() {
        if (!isRecording){
            recordTaskView.showTaskNamePiker();
        } else {
            recordTaskView.showErrorRecordIntend(true);
        }

    }

    @Override
    public void pickAnEmployee() {
        if (!isRecording){
            recordTaskView.showEmployeePicker();
        } else {
            recordTaskView.showErrorRecordIntend(true);
        }
    }
}
