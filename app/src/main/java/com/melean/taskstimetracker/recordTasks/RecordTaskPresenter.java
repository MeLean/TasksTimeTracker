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

            taskRepository.saveTask(recordTaskView.getTaskModel());
        } else {
            recordTaskView.showErrorRecordIntend(false);
        }
    }
}
