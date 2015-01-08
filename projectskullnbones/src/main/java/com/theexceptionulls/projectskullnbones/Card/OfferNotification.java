package com.theexceptionulls.projectskullnbones.Card;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.theexceptionulls.projectskullnbones.AppSettings;
import com.theexceptionulls.projectskullnbones.CardData;
import com.theexceptionulls.projectskullnbones.Constants;
import com.theexceptionulls.projectskullnbones.R;
import com.theexceptionulls.projectskullnbones.webservices.Offers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avatapally on 12/30/14.
 */
public class OfferNotification extends Activity {

    private TextView thankyouMessage;
    private TextView savingsMessage;
    private ListView listView;
    private int retailerId;
    private String cardNumber;
    private int cardPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_notification);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        retailerId = intent.getIntExtra(Constants.RETAILER_ID, Constants.DEFAULT_RETAILER_ID);
        cardNumber = intent.getStringExtra(Constants.CARD_NUMBER);
        cardPosition = intent.getIntExtra(Constants.CARD_POSITION, Constants.DEFAULT_CARD_POSITION);

        thankyouMessage = (TextView) findViewById(R.id.offer_notification_thanks);
        savingsMessage = (TextView) findViewById(R.id.offer_notification_savings);

        List<Integer> offersListIds = loadOffersListIds();
        List<Offers> offersList = AppSettings.getInstance().getNewRandomOffers(3, offersListIds);
        for (Offers offer : offersList) {
            offer.setExpiration("Expires 09/12/2014");
        }
        addNewOffers(offersList, this);

//        List<Offers> offersList = new ArrayList<>();
//        for (int i =0; i<3 ; i++){
//            Offers offers = new Offers();
//            offers.setId(i);
//            offers.setDescription("On any ONE(1) six pack or larger");
//            offers.setExpiration("Expires 09/12/2014");
//            offers.setHeading("Save $1.50");
//            offersList.add(offers);
//        }

        OffersAdapter offersAdapter = new OffersAdapter(this, offersList);
        listView = (ListView) findViewById(R.id.offer_notification_listview);
        listView.setAdapter(offersAdapter);
    }

    public void addNewOffers(List<Offers> newOffers, Context context) {
        List<Offers> offersList = null;
        // read old list
        try {
            String fileName = Constants.OFFERS_FILE_PREFIX + cardPosition;
            FileInputStream fileInputStream = openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            offersList = (List<Offers>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // concatenate
        offersList.addAll(newOffers);
        // write new list
        if (offersList != null) {
            try {
                String fileName = Constants.OFFERS_FILE_PREFIX + cardPosition;
                FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(offersList);
                fileOutputStream.close();
                objectOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        backToCardActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            backToCardActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void backToCardActivity() {
        Intent intent = new Intent(OfferNotification.this, CardActivity.class);
        intent.putExtra(Constants.INTENT_FROM, Constants.INTENT_FROM_NOTIFICATION);
        intent.putExtra(Constants.RETAILER_ID, retailerId);
        intent.putExtra(Constants.CARD_NUMBER, cardNumber);
        intent.putExtra(Constants.CARD_POSITION, cardPosition);
        startActivity(intent);
        finish();
    }

    private List<Integer> loadOffersListIds() {
        List<Integer> offersIdsList = null;
        try {
            String fileName = Constants.OFFERS_FILE_PREFIX + cardPosition;
            FileInputStream fileInputStream = openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<Offers> offersList = (List<Offers>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            offersIdsList = new ArrayList<>();
            for (Offers offer : offersList) {
                offersIdsList.add(offer.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return offersIdsList;
    }
}
