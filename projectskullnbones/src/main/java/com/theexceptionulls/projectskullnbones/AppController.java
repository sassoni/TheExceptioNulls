package com.theexceptionulls.projectskullnbones;

import android.app.Application;

/**
 * Created by rohithavatapally on 8/27/14.
 */
public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppSettings.getInstance();
    }

}
