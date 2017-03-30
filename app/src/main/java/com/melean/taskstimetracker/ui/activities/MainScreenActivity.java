package com.melean.taskstimetracker.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.melean.taskstimetracker.R;
import com.melean.taskstimetracker.ui.fragments.MainScreenFragment;
import com.melean.taskstimetracker.ui.presenters.RecordTaskPresenter;
import com.melean.taskstimetracker.ui.enums.ApplicationError;

public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private MainScreenFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_record);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.record_task);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        if (null == savedInstanceState) {
            initFragment();
        }

        findViewById(R.id.fab_record).setOnClickListener(this);
        findViewById(R.id.fab_pause).setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        RecordTaskPresenter presenter = mFragment.getPresenter();
        if (id == R.id.fab_record) {
            if(presenter.isRecording){
                presenter.stopRecording(false);
            }else{
                if (isItemsSelected()){
                    presenter.startRecording();
                }else{
                    mFragment.showErrorRecordIntend(ApplicationError.NOT_FULL_SELECTION);
                }
            }
        }else if(id == R.id.fab_pause){
            presenter.stopRecording(true);
        }
    }

    private boolean isItemsSelected() {
        return null != mFragment.getSelectedTask()  &&
            null != mFragment.getSelectedEmployee();
    }

    //todo manage saved instance
    public void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragment = (MainScreenFragment) fragmentManager.findFragmentByTag(MainScreenFragment.TAG);

        if(mFragment == null){
            mFragment = MainScreenFragment.getNewInstance();
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, mFragment, MainScreenFragment.TAG);
        transaction.commitAllowingStateLoss();
    }
}