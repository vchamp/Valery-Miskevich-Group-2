package com.epam.memorymanagement;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;

public class ImagesApplication extends Application {

    public static final String TAG = "ImagesApp";

    public ImagesApplication() {
        super();
    }

    @Override public void onCreate() {
        super.onCreate();
        App.init(this);
    }

    public static class App {

        private static AppWrapper instance;

        public static AppWrapper get() {
            return instance;
        }

        static void init(Context context) {
            instance = new AppWrapper(context);
        }
    }

    @Override public void onLowMemory() {
        super.onLowMemory();
        App.get().images().cleanCache();
    }

    @Override public void onTrimMemory(int level) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_COMPLETE
                || level == ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL
                || level == ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            App.get().images().cleanCache();
        }
    }
}
