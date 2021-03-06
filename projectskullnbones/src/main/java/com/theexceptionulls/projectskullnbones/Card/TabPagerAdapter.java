package com.theexceptionulls.projectskullnbones.Card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private int pagerCount;
    private Bundle bundle;

    public TabPagerAdapter(int pagerCount, FragmentManager fm, Bundle bundle) {
        super(fm);
        this.pagerCount = pagerCount;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return OffersFragment.newInstance(bundle);
            case 1:
                return HistoryFragment.newInstance(bundle);
            case 2:
                return CardFragment.newInstance(bundle);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pagerCount;
    }
}
