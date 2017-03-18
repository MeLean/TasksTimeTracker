package com.melean.taskstimetracker.record_tasks;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.melean.taskstimetracker.R;

public class RecordTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private RecordTaskFragment mFragment;
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
        FloatingActionButton pauseBtn = (FloatingActionButton) findViewById(R.id.fab_pause);
        FloatingActionButton recordBtn = (FloatingActionButton) findViewById(R.id.fab_record);
        if (id == R.id.fab_record) {
            toggleRecording(pauseBtn, recordBtn, false);
        }else if(id == R.id.fab_pause){
            toggleRecording(pauseBtn, recordBtn, true);
        }
    }

    private void toggleRecording(FloatingActionButton pauseBtn, FloatingActionButton fabRecord, boolean isInterupted) {
        String fabRecordContentDescription = fabRecord.getContentDescription().toString();

        if (getString(R.string.start).equals(fabRecordContentDescription)) {
            pauseBtn.setVisibility(View.VISIBLE);
            fabRecord.setImageResource(android.R.drawable.ic_menu_save);
            fabRecord.setContentDescription(getString(R.string.save));
        } else {
            pauseBtn.setVisibility(View.GONE);
            fabRecord.setImageResource(android.R.drawable.ic_media_play);
            fabRecord.setContentDescription(getString(R.string.start));
            mFragment.isTaskInterrupted = isInterupted;
        }
    }

    public void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragment = (RecordTaskFragment) fragmentManager.findFragmentByTag(RecordTaskFragment.TAG);

        if(mFragment == null){
            mFragment = RecordTaskFragment.getNewInstance();
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, mFragment, RecordTaskFragment.TAG);
        transaction.commitAllowingStateLoss();
    }
}
