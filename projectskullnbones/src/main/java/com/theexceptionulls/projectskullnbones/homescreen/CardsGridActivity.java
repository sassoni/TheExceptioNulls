package com.theexceptionulls.projectskullnbones.homescreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.theexceptionulls.projectskullnbones.*;
import com.theexceptionulls.projectskullnbones.Card.CardActivity;
import com.theexceptionulls.projectskullnbones.addcard.StoreListActivity;

public class CardsGridActivity extends Activity {

    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_grid);

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new CardsGridTileAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(CardsGridActivity.this, CardActivity.class);

                String retailerName = AppSettings.getInstance().getStoreList()[position];
                intent.putExtra(Constants.INTENT_FROM, Constants.INTENT_FROM_GRID);
                intent.putExtra(Constants.LOYALTY_CARD_POSITION, position);
//                intent.putExtra(CardData.RETAILER_NAME, retailerName);

                startActivity(intent);
            }
        });

        AppController.loadBarcodeScanner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gridview.invalidateViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cards_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(this, StoreListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
