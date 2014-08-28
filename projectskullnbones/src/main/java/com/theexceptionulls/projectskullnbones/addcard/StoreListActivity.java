package com.theexceptionulls.projectskullnbones.addcard;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.theexceptionulls.projectskullnbones.*;
import com.theexceptionulls.projectskullnbones.Card.CardActivity;

public class StoreListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        setTitle("Add Card");

        StoreListAdapter storeListAdapter = new StoreListAdapter(this, AppSettings.getInstance().getStoreList(), AppSettings.getInstance().getStoreListThumbnailsID());
        setListAdapter(storeListAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
                intent.putExtra(Constants.LOYALTY_CARD_POSITION, position);
                startActivityForResult(intent, Constants.SCAN_CARD_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SCAN_CARD_REQUEST) {
            if (resultCode == RESULT_OK) {
                int loyaltyCardPosition = data.getIntExtra(Constants.LOYALTY_CARD_POSITION, 0);
                String retailerName = AppSettings.getInstance().getStoreList()[loyaltyCardPosition];
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                intent.putExtra(Constants.INTENT_FROM, Constants.INTENT_FROM_REGISTRATION);
                intent.putExtra(CardData.RETAILER_NAME, retailerName);
                String barcode = data.getStringExtra(ScanActivity.BARCODE_VALUE_KEY);
                intent.putExtra(CardData.CARD_NUMBER, barcode);
                startActivity(intent);
                finish();
            }

            if (resultCode == Constants.RESULT_SCAN_NO_BARCODE) {
                int loyaltyCardPosition = data.getIntExtra(Constants.LOYALTY_CARD_POSITION, 0);
                String retailerName = AppSettings.getInstance().getStoreList()[loyaltyCardPosition];
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                intent.putExtra(Constants.INTENT_FROM, Constants.INTENT_FROM_REGISTRATION);
                intent.putExtra(CardData.RETAILER_NAME, retailerName);
                startActivity(intent);
                finish();
            }
        }
    }

}
