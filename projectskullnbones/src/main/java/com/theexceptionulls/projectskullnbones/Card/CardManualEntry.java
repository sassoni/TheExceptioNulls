package com.theexceptionulls.projectskullnbones.Card;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.theexceptionulls.projectskullnbones.AppSettings;
import com.theexceptionulls.projectskullnbones.Constants;
import com.theexceptionulls.projectskullnbones.R;
import com.theexceptionulls.projectskullnbones.zxing.CaptureActivity;

/**
 * Created by Avatapally on 12/29/14.
 */
public class CardManualEntry extends Activity {

    private LinearLayout linearLayout;
    private ImageView retailerLogo;
    private EditText editText;
    private int retailerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manual_entry);

        Intent intent = getIntent();
        retailerId = intent.getIntExtra(Constants.RETAILER_ID, 0);

        linearLayout = (LinearLayout) findViewById(R.id.card_manual_entry_retailer_logo);
        retailerLogo = (ImageView) findViewById(R.id.card_manual_entry_retailer_logo_imageview);

        retailerLogo.setImageDrawable(AppSettings.getDrawable(this, retailerId));
        linearLayout.setBackgroundColor(Color.parseColor(getResources().getStringArray(R.array.retailer_color_codes)[retailerId]));

        editText = (EditText) findViewById(R.id.card_manual_entry_edittext);
        editText.setOnEditorActionListener(new DoneActionListener());
    }

    private class DoneActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            String credential = v.getText().toString();
            if (null == credential || credential.equals("")){
                Toast.makeText(CardManualEntry.this, "No credential entered", Toast.LENGTH_LONG).show();
                return true;
            }

            Intent intent = getIntent();
            intent.putExtra(CaptureActivity.BARCODE_VALUE_KEY, credential);
            intent.putExtra(Constants.RETAILER_ID, retailerId);
            setResult(Constants.RESULT_MANUAL_ENTRY, intent);
            finish();
            return true;
        }
    }
}
