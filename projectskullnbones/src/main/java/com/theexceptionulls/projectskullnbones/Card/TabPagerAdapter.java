package com.theexceptionulls.projectskullnbones.Card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.theexceptionulls.projectskullnbones.CardData;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private int pagerCount;
    private CardData cardData;
    private Bundle bundle;

    public TabPagerAdapter(int pagerCount, FragmentManager fm, CardData cardData, Bundle bundle) {
        super(fm);
        this.pagerCount = pagerCount;
        this.cardData = cardData;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CardFragment.newInstance(bundle);
            case 1:
                return OffersFragment.newInstance(cardData);
            case 2:
                return SavingsFragment.newInstance(cardData);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pagerCount;
    }
}
