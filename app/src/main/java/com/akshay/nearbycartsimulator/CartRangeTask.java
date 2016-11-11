package com.akshay.nearbycartsimulator;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.akshay.nearbycartsimulator.util.RestUtils;


/**
 * Created by Akshay on 11/25/2015.
 * This class is responsible to make Asynchronous background call to the API
 */
public class CartRangeTask extends AsyncTask<Void, Void, Integer> {
    private Context mContext;
    private CartStrengthTaskCallBack mCartStrengthTaskCallBack;
    private int mCartTaskCount;
    private AlertDialog alertDialog;

    CartRangeTask(Context context, CartStrengthTaskCallBack cartStrengthTaskCallBack, int cartTaskCount){
       mContext = context;
       mCartStrengthTaskCallBack = cartStrengthTaskCallBack;
        mCartTaskCount = cartTaskCount;
    }

    protected void onPreExecute(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //show an Alert while doing the API call
        builder.setTitle(mContext.getString(R.string.updating_machines));
        builder.setMessage(mContext.getString(R.string.updating_carts));
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params){
       RestUtils restUtils = new RestUtils();
        Integer cartStrength = 0;
        //make API call 3 times for each cart
        for (int strength = 0; strength < 3 ; strength ++) {
            cartStrength += restUtils.generateCartStrength();
        }
        return cartStrength;
    }

    @Override
    protected void onPostExecute(Integer cartStrength) {
        alertDialog.dismiss();
        //take the average cartStrength
        cartStrength = cartStrength / 3;
        Log.d("Cart Strength", cartStrength.toString());
        mCartStrengthTaskCallBack.onSuccess(mCartTaskCount, cartStrength);
    }

}
