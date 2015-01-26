package com.epam.dziashko.aliaksei.ormapp;

import android.app.Application;

import com.epam.dziashko.aliaksei.ormapp.db.HelperFactory;

public class OrmApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.initHelper(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
