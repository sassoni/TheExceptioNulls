package com.theexceptionulls.projectskullnbones.Card;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.theexceptionulls.projectskullnbones.AppSettings;
import com.theexceptionulls.projectskullnbones.CardData;
import com.theexceptionulls.projectskullnbones.Constants;
import com.theexceptionulls.projectskullnbones.R;
import com.theexceptionulls.projectskullnbones.homescreen.CardsGridActivity;
import com.theexceptionulls.projectskullnbones.homescreen.CardsListManager;

public class CardActivity extends FragmentActivity {

    ViewPager viewPager;
    TabPagerAdapter tabPagerAdapter;
    private static final int TAB_COUNT = 3;
    private String[] tabStrings = new String[]{"Offers", "Savings", "Card"};

    private String intentFrom;
    private int retailerId;
    private String cardNumber;
    private int cardPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);

        Intent intent = getIntent();
        intentFrom = intent.getStringExtra(Constants.INTENT_FROM);
        retailerId = intent.getIntExtra(Constants.RETAILER_ID, Constants.DEFAULT_RETAILER_ID);
        cardNumber = intent.getStringExtra(Constants.CARD_NUMBER);
        String intentFrom = intent.getStringExtra(Constants.INTENT_FROM);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getStringArray(R.array.retailer_names)[retailerId] + " Card");

        if (intentFrom.equals(Constants.INTENT_FROM_REGISTRATION)) {
            Log.i("CARD-REGI", "from registration");
            CardsListManager.getInstance().addNewCard(new CardData(cardNumber, retailerId));
            cardPosition = CardsListManager.getInstance().getCardDataListSize() - 1;
            CardsListManager.getInstance().saveList(this);
            showOptInDialog();

        } else {
            Log.i("CARD-REGI", "not from registration");
            cardPosition = intent.getIntExtra(Constants.CARD_POSITION, Constants.DEFAULT_CARD_POSITION);
            final CardData cardData = CardsListManager.getInstance().getCardDataAtIndex(cardPosition);
            cardNumber = cardData.getCardNumber();
            retailerId = cardData.getRetailerId();
        }

        tabPagerAdapter = new TabPagerAdapter(TAB_COUNT, getSupportFragmentManager(), getIntent().getExtras());
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(tabPagerAdapter);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < 3; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(tabStrings[i])
                            .setTabListener(tabListener));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.card_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (intentFrom.equals(Constants.INTENT_FROM_NOTIFICATION)) {
                Intent intent = new Intent(CardActivity.this, CardsGridActivity.class);
                startActivity(intent);
            }
            finish();
            return true;
        }

        if (id == R.id.action_checkout) {
            notifyForOffers();

            if (areAnyOffersClipped()) {
                notifyForSavings();
                deleteClippedOffers();
            }

            AppSettings.getInstance().switchPaymentMethod();
            setResult(Constants.INTENT_RESULT_FINISH_HOME);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showOptInDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.opt_in_title)
                .setMessage(R.string.opt_in_message)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private OffersFragment getOffersFragment() {
        return (OffersFragment)
                getSupportFragmentManager().findFragmentByTag("android:switcher:" + viewPager.getId() + ":" + tabPagerAdapter.getItemId(0));
    }

    private boolean areAnyOffersClipped() {
        OffersFragment offersFragment = getOffersFragment();
        if (offersFragment != null) {
            return offersFragment.areAnyOffersClipped();
        }
        return false;
    }

    private void deleteClippedOffers() {
        OffersFragment offersFragment = getOffersFragment();
            if (offersFragment != null) {
                offersFragment.deleteClippedOffers(this);
            }
        }

    // ---------- Notifications ---------- //

    private void notifyForSavings() {
        Intent intent = new Intent(); // Notification does nothing for now
        PendingIntent pendingIntent = PendingIntent.getActivity(CardActivity.this, Constants.NOTIFICATION_NEW_SAVINGS, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        String savingsMessage;
        if (retailerId % 2 == 0) {
            savingsMessage = getString(R.string.savings_paypal);
        } else {
            savingsMessage = getString(R.string.savings_ltc);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(CardActivity.this);
        builder.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText(savingsMessage/*"You saved $2.50 at " + getResources().getStringArray(R.array.retailer_names)[retailerId] + "!"*/)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle("Your savings!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(savingsMessage));


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_NEW_SAVINGS, builder.build());

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
    }

    private void notifyForOffers() {
        Intent intent = new Intent(CardActivity.this, OfferNotification.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(getIntent().getExtras());
        intent.putExtra(Constants.CARD_POSITION, cardPosition);
        PendingIntent pendingIntent = PendingIntent.getActivity(CardActivity.this, Constants.NOTIFICATION_NEW_OFFERS, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(CardActivity.this);
        builder.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText("You have new offers! Tap to view them.")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle("New offers!");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_NEW_OFFERS, builder.build());

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
    }

}
