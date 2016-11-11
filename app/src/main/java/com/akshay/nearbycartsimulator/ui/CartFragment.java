package com.akshay.nearbycartsimulator.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akshay.nearbycartsimulator.R;
import com.akshay.nearbycartsimulator.model.Cart;
import com.bumptech.glide.Glide;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akshay
 * A placeholder fragment containing a simple Page view for each Cart.
 */
public  class CartFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_CART_NUMBER = "cart_number";
        private static final String ARG_CART_STRENGTH = "cart_strength";

        @BindView(R.id.section_label) TextView textView;
        @BindView(R.id.cart_image) ImageView vendingImageView;
        @BindView(R.id.cart_range) TextView cartRangeView;

        public CartFragment() {}

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CartFragment newInstance(int sectionNumber, Cart globalCartInfo) {
            CartFragment fragment = new CartFragment();
            Bundle args = new Bundle();
            Map<Integer,Integer>mCartMap = globalCartInfo.getCartMap();
            args.putInt(ARG_CART_NUMBER, (Integer)mCartMap.keySet().toArray()[sectionNumber]);
            args.putInt(ARG_CART_STRENGTH, mCartMap.get((mCartMap.keySet().toArray()[sectionNumber])));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, rootView);
            String[] cartNames= getResources().getStringArray(R.array.cart_names);
            textView.setText(cartNames[getArguments().getInt(ARG_CART_NUMBER) - 1]);

            displayCartImage();
            displayCartStrength();
            return rootView;
        }

    /**
     * Load the images Using BitmapFactory
     */
      private void displayCartImage(){
          int id = getResources().getIdentifier("cart" + getArguments().getInt(ARG_CART_NUMBER), "drawable", getContext().getPackageName());

          Glide.with(this)
               .load(id)
               .asBitmap()
               .into(vendingImageView);
      }

       private void displayCartStrength(){
           int signalStrength = getArguments().getInt(ARG_CART_STRENGTH);
           if (signalStrength <= 30) {
               cartRangeView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.weak_signal));
           } else if (signalStrength > 30 && signalStrength < 60 ) {
               cartRangeView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.medium_signal));
           } else {
               cartRangeView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.strong_signal));
               cartRangeView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
           }
           cartRangeView.setText(getString(R.string.signal_strength, signalStrength));
       }

      @Override
      public void onDestroy(){
          super.onDestroy();
      }
}
