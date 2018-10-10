package com.andrinotech.ustadapp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.andrinotech.ustadapp.data.UserManager;
import com.andrinotech.ustadapp.network.RequestHandler;

public class SampleLifecycleListener implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
//        CommonUtils.showToast("start");
        if (UserManager.getInstance().checkUserIfLoggedIn()) {
            RequestHandler requestHandler = new RequestHandler();
            requestHandler.ChangeStatus("online");
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onDestroy() {
        if (UserManager.getInstance().checkUserIfLoggedIn()) {

            RequestHandler requestHandler = new RequestHandler();
            requestHandler.ChangeStatus("offline");
        }
    }
}
