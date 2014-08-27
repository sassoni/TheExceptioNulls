package com.theexceptionulls.projectskullnbones.addcard;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.theexceptionulls.projectskullnbones.Card.CardActivity;
import com.theexceptionulls.projectskullnbones.R;
import com.theexceptionulls.projectskullnbones.ScanActivity;

public class StoreListActivity extends ListActivity {

    private String[] storeList = {
            "A&P",
            "Family Express",
            "Giant",
            "Giant BONUSCARD",
            "Harris Teeter",
            "Kroger",
            "Marsh",
            "Martin's",
            "Safeway",
            "ShopRite",
            "Stop & Shop",
            "Walmart",
            "Weis"
    };
    private int[] storeListThumbnailsID = {
            R.drawable.i_aandp,
            R.drawable.i_familyexpress,
            R.drawable.i_gl,
            R.drawable.i_gc,
            R.drawable.i_harristeeter,
            R.drawable.i_kroger,
            R.drawable.i_marsh,
            R.drawable.i_martins,
            R.drawable.i_safeway,
            R.drawable.i_shoprite,
            R.drawable.i_stopandshop,
            R.drawable.i_walmart,
            R.drawable.i_weis
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        setTitle("Add Card");

        StoreListAdapter storeListAdapter = new StoreListAdapter(this, storeList, storeListThumbnailsID);
        setListAdapter(storeListAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
                startActivityForResult(intent, ScanActivity.SCAN_CARD_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ScanActivity.SCAN_CARD_REQUEST && resultCode == RESULT_OK){
            Intent intent = new Intent(getApplicationContext(), CardActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
