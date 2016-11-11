package com.akshay.nearbycartsimulator;

/**
 * Created by Akshay
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.akshay.nearbycartsimulator.model.Cart;
import com.akshay.nearbycartsimulator.ui.CartFragment;

/**
 * A {@link FragmentStatePagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionPagerAdapter extends FragmentStatePagerAdapter {/*
    int mCartCount;
    Map<Integer, Integer> mCartMap = new LinkedHashMap<>();*/
    private Cart myGlobalCartInfo;
    public FragmentManager mFragmentManager;

    public SectionPagerAdapter(FragmentManager fm, Cart globalCartsInfo) {
        super(fm);
        mFragmentManager = fm;
        myGlobalCartInfo =  globalCartsInfo;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a CartFragment
        return CartFragment.newInstance(position, myGlobalCartInfo);
    }

    @Override
    public int getItemPosition(Object item) {
            return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return myGlobalCartInfo.getCartCount();
    }


    public void setPagerItems(Cart globalCartsInfo){
        myGlobalCartInfo = globalCartsInfo;
    }
}