package com.theexceptionulls.projectskullnbones.Card;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import com.theexceptionulls.projectskullnbones.*;
import com.theexceptionulls.projectskullnbones.homescreen.CardsGridActivity;

public class CardActivity extends FragmentActivity {

    ViewPager viewPager;
    TabPagerAdapter tabPagerAdapter;
    private static final int TAB_COUNT = 3;
    private String[] tabStrings = new String[]{"Card", "Offers", "Savings"};
    private String intentFrom;
    private int retailerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        intentFrom = intent.getStringExtra(Constants.INTENT_FROM);
        retailerId = intent.getIntExtra(Constants.RETAILER_ID, Constants.DEFAULT_RETAILER_ID);

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
            if (intentFrom.equals(Constants.INTENT_FROM_NOTIFICATION)){
                Intent intent = new Intent(CardActivity.this, CardsGridActivity.class);
                startActivity(intent);
            }
            finish();
            return true;
        }

        if (id == R.id.action_checkout){
            buildNotification();
            setResult(Constants.INTENT_RESULT_FINISH_HOME);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildNotification(){

        Intent intent = new Intent(CardActivity.this, OfferNotification.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(getIntent().getExtras());

        PendingIntent pendingIntent = PendingIntent.getActivity(CardActivity.this, Constants.NOTIFICATION_NEW_OFFERS, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(CardActivity.this);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentText("You saved $2.50. Tap to check your new offers");
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setContentTitle("New Offers!");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("You saved $2.50 at "+getResources().getStringArray(R.array.retailer_names)[retailerId]+". Tap to check out your new offers!"));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_NEW_OFFERS, builder.build());

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
    }

}
