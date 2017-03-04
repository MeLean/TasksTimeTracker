package com.melean.taskstimetracker;

import android.app.Application;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TasksTimeTracker extends Application {
    Realm mRealm;
    @Override
    public void onCreate() {
        super.onCreate();
    }
}