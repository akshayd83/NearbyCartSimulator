package com.akshay.nearbycartsimulator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.akshay.nearbycartsimulator.model.Cart;
import com.akshay.nearbycartsimulator.util.AppUtils;

/**
 * Created by Akshay
 * Splash Screen is on for 2 seconds
 * Displays Number of cart for a given session
 */
public class Splash extends Activity {

     TextView mTitleTextView;
     TextView mCartsCountView;
     Animation fadeAnim;
     int mTotalCarts;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.splash);

        mTitleTextView = (TextView) findViewById(R.id.splash_text);
        mCartsCountView = (TextView) findViewById(R.id.carts_count);
        fadeAnim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade);
        int minCartCount = getCount(R.integer.min_cart_count);
        int maxCartCount = getCount(R.integer.max_cart_count);

        //generate a random number between 5-10
        //This will indicate cartCount for a given session
        mTotalCarts = AppUtils.randInt(minCartCount, maxCartCount);
        final Cart myCarts = (Cart) getApplicationContext();
        myCarts.setCartCount(mTotalCarts);
        mCartsCountView.setText(getString(R.string.carts_count, mTotalCarts));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitleTextView.startAnimation(fadeAnim);

        //Animate for 2 second and go to Home Page
        fadeAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent myIntent = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(myIntent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private int getCount(int resId) {
        return getBaseContext().getResources().getInteger(resId);
    }
}
