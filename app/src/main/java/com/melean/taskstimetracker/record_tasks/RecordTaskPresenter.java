package com.melean.taskstimetracker.record_tasks;

import com.melean.taskstimetracker.data.models.EmployeeModel;
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
    public void loadTasks() {
        taskRepository.getTasks(new ITaskRepository.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<TaskModel> tasks) {
                recordTaskView.showTasksList(tasks);
            }
        });
    }

    @Override
    public void loadEmployees() {
        taskRepository.getEmployees(new ITaskRepository.LoadEmployeesCallback() {
            @Override
            public void onEmployeesLoaded(List<EmployeeModel> employees) {
                recordTaskView.showEmployeesList(employees);
            }
        });
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
