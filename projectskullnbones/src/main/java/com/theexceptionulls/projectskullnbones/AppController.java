package com.theexceptionulls.projectskullnbones;

import android.app.Application;

import com.theexceptionulls.projectskullnbones.homescreen.CardsListManager;

/**
 * Created by Avatapally on 12/30/14.
 */
public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CardsListManager.getInstance().loadCardsList(getApplicationContext());
    }
}
