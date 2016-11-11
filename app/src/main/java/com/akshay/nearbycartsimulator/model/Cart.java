package com.akshay.nearbycartsimulator.model;

import android.app.Application;

import com.akshay.nearbycartsimulator.R;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Akshay
 * Holds the Global Copy of the data.
 * The data can be set across activities
 */
public class Cart extends Application {
    private int cartCount;
    private String[] cartNames;
    private boolean mIsRandomSelection = true;

    private Map<Integer,Integer> mCartMap = new LinkedHashMap<>();

    public int getCartCount() {
        return cartCount;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }

    public void setCartMap(Map<Integer,Integer> cartMap) {
        mCartMap = cartMap;
    }

    public Map<Integer,Integer> getCartMap() {
        return mCartMap;
    }

    public void initCartNames(){

        cartNames =  getResources().getStringArray(R.array.cart_names);
    }

    public String[] getCartNames() {
        return  cartNames;
    }

    public boolean isRandomSelectionEnabled(){
        return mIsRandomSelection;
    }

    public void setRandomSelectionState(boolean isRandomSelection){
        mIsRandomSelection = isRandomSelection;
    }

    public void reset(){
        mCartMap.clear();
        mIsRandomSelection = true;
        cartCount = 0;

    }

}
