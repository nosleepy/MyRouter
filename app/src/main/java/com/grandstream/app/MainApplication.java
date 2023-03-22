package com.grandstream.app;

import android.app.Application;

import com.grandstream.myrouter.api.MyRouter;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyRouter.getInstance().init(this);
    }
}
