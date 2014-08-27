package com.theexceptionulls.projectskullnbones;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.catalinamarketing.mobile.enums.ResponseStatus;
import com.catalinamarketing.scanner.BarcodeScanner;
import com.catalinamarketing.scanner.vo.ScannerDecodeResponse;

/**
 * Created by rohithavatapally on 8/27/14.
 */
public class ScanActivity extends Activity implements Handler.Callback {

    private static final int TIMEOUT_CANCEL_SCANNING = 45000;
    private Drawable scanLines;
    private Drawable scanLinesFound;
    private ToneGenerator toneGenerator;
    private ImageView scanViewScanPanel;
    private SurfaceView surface;

    public static final String BARCODE_VALUE_KEY = "barcode";
    public static final String SYMBOLOGY_VALUE_KEY = "symbology";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Create screen layout
        FrameLayout mainLayout = (FrameLayout)getLayoutInflater().inflate(R.layout.scan_activity, null, false);
        mainLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(mainLayout);

        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
        scanLines = getResources().getDrawable(R.drawable.g_scanlines_bkg);
        scanLinesFound = getResources().getDrawable(R.drawable.g_scanlinesfound_bkg);

        scanViewScanPanel = (ImageView) findViewById(R.id.scan_view_scan_panel);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            scanViewScanPanel.setBackground(scanLines);
        } else {
            scanViewScanPanel.setBackgroundDrawable(scanLines);
        }

        // Get SurfaceView that the scan preview will display on
        // This will be passed into the mCommerce library scan call
        surface = (SurfaceView) findViewById(R.id.scan_surface_view);

        if(!BarcodeScanner.getInstance().isScannerReady()){
            Toast.makeText(getApplicationContext(), "Scanner not ready", Toast.LENGTH_LONG).show();
        }else {
            // Activate the scan engine and begin trying to decode a barcode
            BarcodeScanner.getInstance().startScanner(this, surface, new Handler(this));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!BarcodeScanner.getInstance().isScannerReady()){
            Toast.makeText(getApplicationContext(), "Scanner not loaded", Toast.LENGTH_LONG).show();
        }else {
            // Activate the scan engine and begin trying to decode a barcode
            BarcodeScanner.getInstance().startScanner(this, surface, new Handler(this));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop the scan engine
        BarcodeScanner.getInstance().stopScanner();
    }

    @Override
    public boolean handleMessage(Message msg) {

        if (msg.obj != null && msg.obj instanceof ScannerDecodeResponse){
            ScannerDecodeResponse scanResponse = (ScannerDecodeResponse)msg.obj;
            if(scanResponse.getResponseStatus() == ResponseStatus.SUCCESS){
                // Make a scan beep sound
                if (toneGenerator != null) {
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    scanViewScanPanel.setBackground(scanLinesFound);
                } else {
                    scanViewScanPanel.setBackgroundDrawable(scanLinesFound);
                }

                // Otherwise, return the barcode to to calling activity to process
                Intent i = getIntent();
                i.putExtra(BARCODE_VALUE_KEY, scanResponse.getBarcode());
                i.putExtra(SYMBOLOGY_VALUE_KEY, scanResponse.getSymbology().name());
                setResult(RESULT_OK, i);
                finish();
            }
        }

        return true;
    }
}
