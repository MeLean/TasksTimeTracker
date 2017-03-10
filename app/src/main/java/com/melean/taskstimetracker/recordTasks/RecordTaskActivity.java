package com.melean.taskstimetracker.recordTasks;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.melean.taskstimetracker.R;

public class RecordTaskActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.record_task);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        if (null == savedInstanceState) {
            initFragment(RecordTaskFragment.newInstance());
        }

        findViewById(R.id.fab_record).setOnClickListener(this);
        findViewById(R.id.fab_pause).setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void initFragment(Fragment recordTaskFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_container, recordTaskFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.fab_record) {
            FloatingActionButton fabRecord = (FloatingActionButton) findViewById(R.id.fab_record);
            View pauseBtn = findViewById(R.id.fab_pause);
            String fabRecordContentDescription = fabRecord.getContentDescription().toString();

            if (getString(R.string.start).equals(fabRecordContentDescription)) {
                pauseBtn.setVisibility(View.VISIBLE);
                fabRecord.setImageResource(android.R.drawable.ic_menu_save);
                fabRecord.setContentDescription(getString(R.string.save));
            } else {
                pauseBtn.setVisibility(View.GONE);
                fabRecord.setImageResource(android.R.drawable.ic_media_play);
                fabRecord.setContentDescription(getString(R.string.start));

            }
        }else if(id == R.id.fab_pause){
            Toast.makeText(RecordTaskActivity.this, "Implement pause", Toast.LENGTH_SHORT).show();
        }
    }
}