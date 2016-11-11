package com.akshay.nearbycartsimulator;

/**
 * Created by Akshay
 * Callback Interface to Return value from AsyncTask to Caller method.
 */
public interface CartStrengthTaskCallBack {
    void onSuccess(Integer CurrentTaskCount, Integer cartStrength);
}
