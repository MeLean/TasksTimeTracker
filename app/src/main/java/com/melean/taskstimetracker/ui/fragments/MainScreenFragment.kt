package com.melean.taskstimetracker.ui.fragments


import android.os.SystemClock
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.TextView

import com.melean.taskstimetracker.R
import com.melean.taskstimetracker.constants.Preferences
import com.melean.taskstimetracker.data.database.RealmDatabase.RealmDatabase
import com.melean.taskstimetracker.data.models.EmployeeModel
import com.melean.taskstimetracker.data.models.TaskEntityModel
import com.melean.taskstimetracker.data.models.TaskModel
import com.melean.taskstimetracker.data.repositories.TaskRepositoryImplementation
import com.melean.taskstimetracker.ui.fragments.dialogs.AddEmployeeDialogFragment
import com.melean.taskstimetracker.ui.interfaces.RecordTaskContract
import com.melean.taskstimetracker.ui.presenters.RecordTaskPresenter
import com.melean.taskstimetracker.ui.enums.ApplicationError
import com.melean.taskstimetracker.ui.adapters.BaseRecyclerAdapter
import com.melean.taskstimetracker.ui.adapters.EmployeeAdapter
import com.melean.taskstimetracker.ui.adapters.TasksAdapter
import com.melean.taskstimetracker.ui.fragments.dialogs.ErrorDialogFragment

import java.text.SimpleDateFormat
import java.util.Locale

class MainScreenFragment : Fragment(), RecordTaskContract.View, View.OnClickListener {

    var presenter: RecordTaskPresenter? = null
        private set
    private var mTimer: Chronometer? = null
    private var mTasksRecycler: RecyclerView? = null
    private var mEmployeesRecycler: RecyclerView? = null
    private var mTaskAdapter: BaseRecyclerAdapter<*>? = null
    private var mEmployeeAdapter: BaseRecyclerAdapter<*>? = null
    private var mNoTasks: TextView? = null
    private var mNoEmployees: TextView? = null
    private var mSecondsWorked: Long = 0
    private var isTaskInterrupted = false

    override val taskModel: TaskEntityModel
        get() {
            val dateFormat = SimpleDateFormat(Preferences.ENTITY_DATE_FORMAT, Locale.getDefault())

            val selectedTaskName: String? = getSelectedElementString(mTasksRecycler)

            val selectedEmployeeName: String? = getSelectedElementString(mEmployeesRecycler)

            return TaskEntityModel(
                    selectedEmployeeName,
                    selectedTaskName,
                    mSecondsWorked,
                    isTaskInterrupted,
                    dateFormat.format(System.currentTimeMillis())
            )
        }

    val selectedTask: View?
        get() = if (mTaskAdapter != null) {
            mTaskAdapter!!.lastSelectedView
        } else null

    val selectedEmployee: View?
        get() = if (mEmployeeAdapter != null) {
            mEmployeeAdapter!!.lastSelectedView
        } else null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_record_task, container, false)
        val repository = TaskRepositoryImplementation(RealmDatabase(context))
        presenter = RecordTaskPresenter(this, repository)
        mTimer = view.findViewById(R.id.timer) as Chronometer
        mTasksRecycler = view.findViewById(R.id.tasks_list) as RecyclerView
        mEmployeesRecycler = view.findViewById(R.id.employees_list) as RecyclerView
        mNoTasks = view.findViewById(R.id.no_tasks) as TextView
        mNoEmployees = view.findViewById(R.id.no_employees) as TextView
        presenter!!.loadTasks()
        presenter!!.loadEmployees()
        view.findViewById(R.id.btn_add_employee).setOnClickListener(this)
        return view
    }

    override fun showTasksList(tasksNames: MutableList<TaskModel>) {
        if (tasksNames.isNotEmpty()) {
            mTaskAdapter = TasksAdapter(context, tasksNames)
            manageRecycler(mTasksRecycler, mTaskAdapter as BaseRecyclerAdapter, mNoTasks)
        }
    }

    override fun showEmployeesList(employeesNames: MutableList<EmployeeModel>) {
        if (employeesNames.isNotEmpty()) {
            mEmployeeAdapter = EmployeeAdapter(context, employeesNames)
            manageRecycler(mEmployeesRecycler, mEmployeeAdapter as BaseRecyclerAdapter, mNoEmployees)
        }
    }

    override fun showErrorRecordIntend(applicationError: ApplicationError) {
        val message: String = when (applicationError) {
            ApplicationError.NOT_FULL_SELECTION -> getString(R.string.error_not_full_selection)
            ApplicationError.NOT_PERMITTED_WHILE_RECORDING -> getString(R.string.error_not_permitted_until_recording)
            ApplicationError.PERMITTED_ONLY_WHILE_RECORDING -> getString(R.string.error_permitted_only_while_recording)
            else -> getString(R.string.error_unknown)
        }

        val dialog = ErrorDialogFragment.newInstance(message)
        dialog.show(activity.supportFragmentManager, ErrorDialogFragment.TAG)
    }

    override fun toggleRecording(isInterrupted: Boolean) {
        val fabRecord = activity.findViewById(R.id.fab_record) as FloatingActionButton
        val pauseBtn = activity.findViewById(R.id.fab_pause) as FloatingActionButton
        val fabRecordContentDescription = fabRecord.contentDescription.toString()

        if (getString(R.string.start) == fabRecordContentDescription) {
            startTimer(mTimer)
            pauseBtn.visibility = View.VISIBLE
            fabRecord.setImageResource(android.R.drawable.ic_menu_save)
            fabRecord.contentDescription = getString(R.string.save)
        } else {
            stopTimer(mTimer)
            pauseBtn.visibility = View.GONE
            fabRecord.setImageResource(android.R.drawable.ic_media_play)
            fabRecord.contentDescription = getString(R.string.start)
            isTaskInterrupted = isInterrupted
        }
    }

    private fun getSelectedElementString(
            recyclerView: RecyclerView?): String? {

        val adapter = recyclerView!!.adapter as BaseRecyclerAdapter<*>
        val view = adapter.lastSelectedView
        var id = -1
        if (adapter is TasksAdapter) {
            id = R.id.task_name
        } else if (adapter is EmployeeAdapter) {
            id = R.id.employee_name
        }

        val name : TextView? = view!!.findViewById(id) as TextView
        return name?.text.toString();
    }

    override fun onPause() {
        super.onPause()
        this.retainInstance = true
    }

    private fun manageRecycler(recyclerView: RecyclerView?,
                               adapter: RecyclerView.Adapter<*>,
                               noItemsView: TextView?) {

        recyclerView!!.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context.applicationContext)
        recyclerView.itemAnimator = DefaultItemAnimator()
        noItemsView!!.visibility = View.GONE
    }

    internal fun toggleRecording(pauseBtn: FloatingActionButton, fabRecord: FloatingActionButton, isInterrupted: Boolean) {

    }

    private fun startTimer(timer: Chronometer?) {
        timer!!.base = SystemClock.elapsedRealtime()
        timer.start()
    }

    private fun stopTimer(timer: Chronometer?) {
        timer!!.stop()
        // convert in second from milliseconds
        mSecondsWorked = (SystemClock.elapsedRealtime() - timer.base) / 1000
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.btn_add_employee) {
            val dialog = AddEmployeeDialogFragment()
            dialog.show(activity.supportFragmentManager, AddEmployeeDialogFragment.TAG)
        }
    }

    companion object {
        val TAG = "com.melean.taskstimetracker.recordTasks.recordtaskfragment"

        val newInstance: MainScreenFragment
            get() = MainScreenFragment()
    }
}// Required empty public constructor
