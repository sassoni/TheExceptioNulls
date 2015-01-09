package com.theexceptionulls.projectskullnbones.Card;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.theexceptionulls.projectskullnbones.AppSettings;
import com.theexceptionulls.projectskullnbones.Constants;
import com.theexceptionulls.projectskullnbones.R;
import com.theexceptionulls.projectskullnbones.webservices.Offers;

import java.io.FileInputStream;
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

        // Thank you message setup
        thankyouMessage = (TextView) findViewById(R.id.offer_notification_thanks);
        Resources res = getResources();
        TypedArray retailerNames = res.obtainTypedArray(R.array.retailer_names);
        String retailerName = retailerNames.getString(retailerId);
        thankyouMessage.setText(Html.fromHtml("Thanks for shopping at <b>" + retailerName + "</b>!"));

        // No savings message for now in the offers
//        savingsMessage = (TextView) findViewById(R.id.offer_notification_savings);
//        if (/*AppSettings.getInstance().isPaymentMethodPaypal()*/retailerId % 2 == 0) {
//            savingsMessage.setText(Html.fromHtml(getString(R.string.savings_paypal)));
//        } else {
//            savingsMessage.setText(Html.fromHtml(getString(R.string.savings_ltc)));
//        }

        /*List<Integer>*/
        int offersListIds = loadOffersListIds();
        List<Offers> offersList = AppSettings.getInstance().getNotificationOffers(2, offersListIds - 1);
        for (Offers offer : offersList) {
            offer.setExpiration("Expires 09/12/2014");
        }
        addNewOffers(offersList, this);

        OffersAdapter offersAdapter = new OffersAdapter(this, offersList);
        listView = (ListView) findViewById(R.id.offer_notification_listview);
        listView.setAdapter(offersAdapter);
    }

    public void addNewOffers(List<Offers> newOffers, Context context) {
        int offersSoFar = 2;
        List<Offers> offersList = null;
        // read old list
        try {
            String fileName = Constants.OFFERS_FILE_PREFIX + cardPosition + cardNumber;
            Log.i("FILENAME1", fileName);
            FileInputStream fileInputStream = openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            offersSoFar = objectInputStream.readInt();
            Log.i("OFFERNOTIFICATION", "read: " + offersSoFar);
            offersList = (List<Offers>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // concatenate
        offersList.addAll(newOffers);
        offersSoFar += newOffers.size();
        if (offersSoFar == 19) {
            offersSoFar = 0;
        }
        // write new list
        if (offersList != null) {
            try {
                String fileName = Constants.OFFERS_FILE_PREFIX + cardPosition + cardNumber;
                Log.i("FILENAME2", fileName);
                FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                Log.i("OFFERNOTIFICATION", "write: " + offersSoFar);
                objectOutputStream.writeInt(offersSoFar);
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

    private /*List<Integer>*/ int loadOffersListIds() {
        int offers = 2;
        List<Integer> offersIdsList = null;
        try {
            String fileName = Constants.OFFERS_FILE_PREFIX + cardPosition + cardNumber;
            Log.i("FILENAME3", fileName);
            FileInputStream fileInputStream = openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            offers = objectInputStream.readInt();
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
        return offers;
    }
}
