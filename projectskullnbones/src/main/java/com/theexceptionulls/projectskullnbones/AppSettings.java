package com.theexceptionulls.projectskullnbones;

/**
 * Created by rohithavatapally on 8/27/14.
 */
public class AppSettings {

    private static AppSettings instance;
    public static final String SCAN_ENGINE_READY_INTENT = "com.catalinamarketing.scanengineready";

    private AppSettings() {
    }

    public static AppSettings getInstance(){
        if(instance == null){
            return instance = new AppSettings();
        }else {
            return instance;
        }
    }

}
