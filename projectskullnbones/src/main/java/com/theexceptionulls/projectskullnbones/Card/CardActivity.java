package com.theexceptionulls.projectskullnbones.Card;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.theexceptionulls.projectskullnbones.*;

public class CardActivity extends FragmentActivity {

    ViewPager viewPager;
    TabPagerAdapter tabPagerAdapter;
    private static final int TAB_COUNT = 3;
    private String[] tabStrings = new String[]{"Card", "Offers", "Savings"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

//        Intent intent = getIntent();
//        String intentFrom = intent.getStringExtra(Constants.INTENT_FROM);
//
//        if (intentFrom.equals(Constants.INTENT_FROM_REGISTRATION)) {
//            String cardNumber = intent.getStringExtra(CardData.CARD_NUMBER);
//            String retailerName = intent.getStringExtra(CardData.RETAILER_NAME);
//        } else {
//            bundle.putInt(Constants.LOYALTY_CARD_POSITION, dataBundle.getInt(Constants.LOYALTY_CARD_POSITION));
//        }
//
//
//
        CardData newCardData = new CardData("0123", "345");


        //////////////////////CHECK FOR WHEN BARCODE IS NULL

        tabPagerAdapter = new TabPagerAdapter(TAB_COUNT, getSupportFragmentManager(), newCardData, getIntent().getExtras());
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
