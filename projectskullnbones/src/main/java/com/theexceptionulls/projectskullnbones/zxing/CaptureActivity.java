package com.theexceptionulls.projectskullnbones.zxing;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.theexceptionulls.projectskullnbones.Constants;
import com.theexceptionulls.projectskullnbones.R;
import com.theexceptionulls.projectskullnbones.zxing.camera.CameraManager;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


public class CaptureActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private static final int TIMEOUT_CANCEL_SCANNING = 45000;
    private Drawable scanLines;
    private Drawable scanLinesFound;
    private ToneGenerator toneGenerator;
    private ImageView scanViewScanPanel;
    private SurfaceView surface;

    public static final String BARCODE_VALUE_KEY = "barcode";
    public static final String SYMBOLOGY_VALUE_KEY = "symbology";
    private int LOYALTY_CARD_POSITION = 0;
    private TextView noCardTextView;

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private Result savedResultToShow;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType,?> decodeHints;
    private String characterSet;

    private boolean hasSurface;

    public Handler getHandler() {
        return handler;
    }

    CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.scan_activity);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Scan Card");

        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
        scanLines = getResources().getDrawable(R.drawable.g_scanlines_bkg);
        scanLinesFound = getResources().getDrawable(R.drawable.g_scanlinesfound_bkg);

        final Intent intent = getIntent();
        LOYALTY_CARD_POSITION = intent.getIntExtra(Constants.LOYALTY_CARD_POSITION, 0);

        scanViewScanPanel = (ImageView) findViewById(R.id.scan_view_scan_panel);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            scanViewScanPanel.setBackground(scanLines);
        } else {
            scanViewScanPanel.setBackgroundDrawable(scanLines);
        }

        hasSurface = false;

        noCardTextView = (TextView) findViewById(R.id.scan_view_no_card_textview);
        noCardTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra(Constants.LOYALTY_CARD_POSITION, LOYALTY_CARD_POSITION);
                setResult(Constants.RESULT_SCAN_NO_BARCODE, intent1);
                finish();
            }
        });

        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // May be initialized in onCreate ... need to dig deep
        cameraManager = new CameraManager(getApplication());

        handler = null;

        surface = (SurfaceView) findViewById(R.id.scan_surface_view);
        SurfaceHolder surfaceHolder = surface.getHolder();

        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the camera.
            surfaceHolder.addCallback(this);
        }
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }

        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.scan_surface_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toneGenerator != null) {
            toneGenerator.release();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_FOCUS:
            case KeyEvent.KEYCODE_CAMERA:
                // Handle these events so they don't launch the Camera app
                return true;
            /*// Use volume up/down to turn on light
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                cameraManager.setTorch(false);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                cameraManager.setTorch(true);
                return true;*/
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public void handleDecode(Result result){

        if (toneGenerator != null) {
            toneGenerator.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            scanViewScanPanel.setBackground(scanLinesFound);
        } else {
            scanViewScanPanel.setBackgroundDrawable(scanLinesFound);
        }

        Intent i = getIntent();
        i.putExtra(BARCODE_VALUE_KEY, result.getText());
        i.putExtra(SYMBOLOGY_VALUE_KEY, result.getBarcodeFormat());
        i.putExtra(Constants.LOYALTY_CARD_POSITION, LOYALTY_CARD_POSITION);
        setResult(RESULT_OK, i);
        finish();

        //un-comment the below line for multi scan
        //restartPreviewAfterDelay(1000);
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a RuntimeException.
            if (handler == null) {
                handler = new com.theexceptionulls.projectskullnbones.zxing.CaptureActivityHandler(this,/* decodeFormats, decodeHints, characterSet,*/ cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Camera Error");
        builder.setMessage("Sorry, the Android camera encountered a problem. You may need to restart the device.");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    // Call this method on handleDecode for multi-scan
    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

}
