package com.melean.taskstimetracker.ui.interfaces;

import com.melean.taskstimetracker.data.models.EmployeeModel;
import com.melean.taskstimetracker.data.models.TaskModel;

public interface AddDataContract {
    interface View {
        void showAddEmployeeView();

        void showAddTaskView();

        void showErrorAdding();

        void updateEmployees(EmployeeModel employee);

        void updateTasks(TaskModel task);

        void onDataEntered(Object obj);
    }

    interface UserActionsListener {
        void addEmployee();

        void addTaskView();

        void saveEmployee(EmployeeModel employee);

        void saveTask(TaskModel task);
    }
}