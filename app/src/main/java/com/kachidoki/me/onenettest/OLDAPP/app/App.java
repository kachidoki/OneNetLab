package com.kachidoki.me.onenettest.OLDAPP.app;

import android.app.Application;

/**
 * Created by Frank on 16/8/14.
 */

public class App extends Application {
    private static App instance;

    public static App getInstance(){

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
