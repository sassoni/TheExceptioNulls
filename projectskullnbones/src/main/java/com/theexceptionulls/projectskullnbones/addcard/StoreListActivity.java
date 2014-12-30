package com.theexceptionulls.projectskullnbones.addcard;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.theexceptionulls.projectskullnbones.*;
import com.theexceptionulls.projectskullnbones.Card.CardActivity;
import com.theexceptionulls.projectskullnbones.Card.CardManualEntry;
import com.theexceptionulls.projectskullnbones.zxing.CaptureActivity;

public class StoreListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("Select Retailer");
        StoreListAdapter storeListAdapter = new StoreListAdapter(this, getResources().getStringArray(R.array.retailer_names), AppSettings.getStoreListThumbnailsID());
        setListAdapter(storeListAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StoreListActivity.this, CaptureActivity.class);
                intent.putExtra(Constants.RETAILER_ID, position);
                startActivityForResult(intent, Constants.SCAN_CARD_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SCAN_CARD_REQUEST) {
            if (resultCode == RESULT_OK) {
                String barcode = data.getStringExtra(CaptureActivity.BARCODE_VALUE_KEY);
                int retailerId = data.getIntExtra(Constants.RETAILER_ID, Constants.DEFAULT_RETAILER_ID);
                Intent intent = new Intent(StoreListActivity.this, CardActivity.class);
                intent.putExtra(Constants.INTENT_FROM, Constants.INTENT_FROM_REGISTRATION);
                intent.putExtra(Constants.RETAILER_ID, retailerId);
                intent.putExtra(Constants.CARD_NUMBER, barcode);
                startActivity(intent);
                finish();
            }

            if (resultCode == Constants.RESULT_SCAN_NO_BARCODE) {
                int retailerId = data.getIntExtra(Constants.RETAILER_ID, Constants.DEFAULT_RETAILER_ID);
                Intent intent = new Intent(StoreListActivity.this, CardManualEntry.class);
                intent.putExtra(Constants.RETAILER_ID, retailerId);
                startActivityForResult(intent, Constants.SCAN_CARD_REQUEST);
            }

            if (resultCode == Constants.RESULT_MANUAL_ENTRY) {
                String barcode = data.getStringExtra(CaptureActivity.BARCODE_VALUE_KEY);
                int retailerId = data.getIntExtra(Constants.RETAILER_ID, Constants.DEFAULT_RETAILER_ID);
                Intent intent = new Intent(StoreListActivity.this, CardActivity.class);
                intent.putExtra(Constants.INTENT_FROM, Constants.INTENT_FROM_REGISTRATION);
                intent.putExtra(Constants.CARD_NUMBER, barcode);
                intent.putExtra(Constants.RETAILER_ID, retailerId);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
