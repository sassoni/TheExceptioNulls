package com.theexceptionulls.projectskullnbones.Card;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by rohithavatapally on 8/27/14.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

    private int pagerCount;

    public TabPagerAdapter(int pagerCount, FragmentManager fm) {
        super(fm);
        this.pagerCount = pagerCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new CardFragment();
            case 1: return new OffersFragment();
            case 2: return new SavingsFragment();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return pagerCount;
    }
}
