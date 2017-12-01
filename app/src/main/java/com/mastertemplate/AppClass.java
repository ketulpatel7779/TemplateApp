package com.mastertemplate;

import android.app.Application;
import android.content.Context;

import com.mastertemplate.utils.crashreport.ReportHandler;

/**
 * Application class for global initialisation
 */
public class AppClass extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ReportHandler.install(this, "dump.zealousys@gmail.com");
        if (BuildConfig.IS_DEBUG) {

        }
    }
    /**
     * @return Application @{@link Context}
     */
    public static Context getAppContext() {
        return AppClass.context;
    }
}