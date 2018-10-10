package com.andrinotech.ustadapp;

import android.app.Application;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.content.SharedPreferences;

import com.andrinotech.ustadapp.data.PreferencesManager;
import com.andrinotech.ustadapp.data.UserManager;

public class UstadApp extends Application implements LifecycleObserver {

    public static PreferencesManager preferencesManager;
    static UstadApp instance;

    public SharedPreferences getPrefs() {
        return prefs;
    }

    private SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        preferencesManager.initializeInstance(this, PreferencesManager.PREF_NAME);
        preferencesManager = PreferencesManager.getInstance();
        prefs = getSharedPreferences(UserManager.PREFS_NAME, Context.MODE_PRIVATE);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new SampleLifecycleListener());

    }

    public static UstadApp getInstance() {
        return instance;
    }

    public static PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }


}
