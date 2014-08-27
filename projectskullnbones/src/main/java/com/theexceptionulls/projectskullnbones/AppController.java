package com.theexceptionulls.projectskullnbones;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.catalinamarketing.mobile.enums.ResponseStatus;
import com.catalinamarketing.scanner.BarcodeScanner;
import com.catalinamarketing.scanner.vo.ScannerEventResponse;

/**
 * Created by rohithavatapally on 8/27/14.
 */
public class AppController extends Application {

    private static Context appContext = null;

    public static void loadBarcodeScanner() {
        BarcodeScanner.getInstance().prepareScanner(getAppContext(), null, new ScanEngineHandler(), getAppContext().getResources().getString(R.string.scanner_license_key));
    }

    public static Context getAppContext() {
        return appContext;
    }

    private static void setAppContext(Context appContext) {
        AppController.appContext = appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setAppContext(getApplicationContext());
    }

    private static class ScanEngineHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null && msg.obj instanceof ScannerEventResponse) {

                ScannerEventResponse response = (ScannerEventResponse) msg.obj;

                if (response.getResponseStatus() == ResponseStatus.SCAN_ENGINE_READY) {
                    Log.i("scan", "scan engine ready");
                    Intent intent = new Intent(AppSettings.SCAN_ENGINE_READY_INTENT);
                    LocalBroadcastManager.getInstance(getAppContext()).sendBroadcast(intent);
                }
            }
        }
    }


}
