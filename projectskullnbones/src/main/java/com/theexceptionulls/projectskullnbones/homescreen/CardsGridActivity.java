package com.theexceptionulls.projectskullnbones.homescreen;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.theexceptionulls.projectskullnbones.*;
import com.theexceptionulls.projectskullnbones.Card.CardActivity;
import com.theexceptionulls.projectskullnbones.addcard.StoreListActivity;

public class CardsGridActivity extends Activity {

    private GridView gridview;
    private TextView emptyCardsMessage;
    private CardsGridTileAdapter cardsGridTileAdapter;
    private boolean inCardDeleteMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_grid);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(getResources().getString(R.string.actionbar_home_title));

        emptyCardsMessage = (TextView) findViewById(R.id.activity_cards_grid_empty_cards);
        gridview = (GridView) findViewById(R.id.gridview);
        cardsGridTileAdapter = new CardsGridTileAdapter(CardsGridActivity.this);

        gridview.setAdapter(cardsGridTileAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                CardData cardData = CardsListManager.getInstance().getCardDataAtIndex(position);

                Intent intent = new Intent(CardsGridActivity.this, CardActivity.class);
                intent.putExtra(Constants.INTENT_FROM, Constants.INTENT_FROM_GRID);
                intent.putExtra(Constants.RETAILER_ID, cardData.getRetailerId());
                intent.putExtra(Constants.CARD_NUMBER, cardData.getCardNumber());
                intent.putExtra(Constants.CARD_POSITION, position);
                startActivityForResult(intent, Constants.INTENT_REQUEST_CODE_CARD);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        inCardDeleteMode = false;
        invalidateOptionsMenu();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cardsGridTileAdapter.stopEditing();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.INTENT_REQUEST_CODE_CARD){
            if (resultCode == Constants.INTENT_RESULT_FINISH_HOME){
                finish();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (inCardDeleteMode){
            getMenuInflater().inflate(R.menu.cards_grid_delete_done, menu);
            return true;
        }

        if (CardsListManager.getInstance().getCardDataListSize()>0){
            getMenuInflater().inflate(R.menu.cards_grid, menu);
            emptyCardsMessage.setVisibility(View.GONE);
            gridview.setVisibility(View.VISIBLE);
            cardsGridTileAdapter.notifyDataSetChanged();
        }else
        {
            getMenuInflater().inflate(R.menu.cards_grid_empty, menu);
            emptyCardsMessage.setVisibility(View.VISIBLE);
            gridview.setVisibility(View.GONE);
        }

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
            case R.id.action_delete:
                inCardDeleteMode = true;
                invalidateOptionsMenu();
                cardsGridTileAdapter.startEditing();
                return true;
            case R.id.action_delete_done:
                inCardDeleteMode = false;
                invalidateOptionsMenu();
                cardsGridTileAdapter.stopEditing();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
