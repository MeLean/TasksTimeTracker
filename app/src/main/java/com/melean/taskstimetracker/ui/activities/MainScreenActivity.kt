package com.melean.taskstimetracker.ui.activities

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import com.melean.taskstimetracker.R
import com.melean.taskstimetracker.ui.fragments.MainScreenFragment
import com.melean.taskstimetracker.ui.presenters.RecordTaskPresenter
import com.melean.taskstimetracker.ui.enums.ApplicationError

class MainScreenActivity : AppCompatActivity(), View.OnClickListener {
    private var mFragment: MainScreenFragment? = null

    private val isItemsSelected: Boolean
        get() = null != mFragment!!.selectedTask && null != mFragment!!.selectedEmployee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_task)

        val toolbar = findViewById(R.id.toolbar_record) as Toolbar
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.record_task)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        if (null == savedInstanceState) {
            initFragment()
        }

        findViewById(R.id.fab_record).setOnClickListener(this)
        findViewById(R.id.fab_pause).setOnClickListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(view: View) {
        val id = view.id
        val presenter = mFragment!!.presenter
        if (id == R.id.fab_record) {
            if (presenter!!.isRecording) {
                presenter.stopRecording(false)
            } else {
                if (isItemsSelected) {
                    presenter.startRecording()
                } else {
                    mFragment!!.showErrorRecordIntend(ApplicationError.NOT_FULL_SELECTION)
                }
            }
        } else if (id == R.id.fab_pause) {
            presenter!!.stopRecording(true)
        }
    }

    //todo manage saved instance
    fun initFragment() {
        val fragmentManager = supportFragmentManager
        mFragment = fragmentManager.findFragmentByTag(MainScreenFragment.TAG) as MainScreenFragment?

        if (mFragment == null) {
            mFragment = MainScreenFragment.newInstance
        }

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, mFragment, MainScreenFragment.TAG)
        transaction.commitAllowingStateLoss()
    }
}
