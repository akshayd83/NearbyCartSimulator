package com.akshay.nearbycartsimulator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.akshay.nearbycartsimulator.model.Cart;
import com.akshay.nearbycartsimulator.ui.CirclePageIndicator;
import com.akshay.nearbycartsimulator.util.AppUtils;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.LinkedHashMap;
import java.util.Map;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionPagerAdapter mSectionPagerAdapter;
    private boolean isCartUpdated = true;
    private LinkedHashMap<Integer,Integer> cartMapIndex;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private CirclePageIndicator mCartCountIndicator;
    private Integer mCartCount;
    private Map<Integer, Integer> cartMap = new LinkedHashMap<>();

    Cart myGlobalCartsInfo;

    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.refresh) MaterialRefreshLayout materialRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getGlobalCartInfo();
        myGlobalCartsInfo.initCartNames();
        loadCartStrengthAndDisplayCarts(false);
    }

    @OnClick(R.id.fab)
    public void clickFab() {
        isCartUpdated = false;
        Intent myIntent = new Intent(getBaseContext(), CartViewOrderActivity.class);
        startActivity(myIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //depending on whether the ordering has been set,
        // maintain a Map index of Carts and reload the cart
        if (!isCartUpdated) {
            getGlobalCartInfo();
            if (!myGlobalCartsInfo.isRandomSelectionEnabled()) {
                maintainMapIndex();
                displayCarts(true);
            } else if (myGlobalCartsInfo.isRandomSelectionEnabled()) {
                clearMapIndex();
                loadCartStrengthAndDisplayCarts(true);
            }
        }
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                loadCartStrengthAndDisplayCarts(true);
            }
        });
        materialRefreshLayout.finishRefresh();
    }

    private void startCartRangeTask(AsyncTask asyncTask){
            ((CartRangeTask) asyncTask).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void)null);
    }

   /**
    *
    * Makes Async task call on Executor to do Thread Pooling.
    * Total Number of threads are same as card count
    * Update the map once the values are loaded
    * Use LinkedHashMap to maintain order.
     */
   private void loadCartStrengthAndDisplayCarts(final boolean updateCarts){

       CartRangeTask myCartRangeTask;

       if(updateCarts && myGlobalCartsInfo.isRandomSelectionEnabled()){
           cartMap.clear();
       }

       CartStrengthTaskCallBack cartStrengthTaskCallBack = new CartStrengthTaskCallBack() {
           @Override
           public void onSuccess(Integer cartTaskCount, Integer cartStrength) {

               if (!myGlobalCartsInfo.isRandomSelectionEnabled()) {
                   int index = cartMapIndex.get(cartTaskCount);
                     AppUtils.put((LinkedHashMap) cartMap, index, cartTaskCount, cartStrength);
               } else {
                   cartMap.put(cartTaskCount, cartStrength);
               }

               if ( mCartCount.equals(cartMap.size()) ) {
                   if (myGlobalCartsInfo.isRandomSelectionEnabled()){
                       //sort by strength only if Random Order is selected
                       cartMap = AppUtils.sortByValue(cartMap);
                   }
                   myGlobalCartsInfo.setCartMap(cartMap);
                   displayCarts(updateCarts);
               }
           }
       };

       for(int taskCount  = 1; taskCount <= mCartCount; taskCount ++){
           myCartRangeTask = new CartRangeTask(this, cartStrengthTaskCallBack,taskCount);
           startCartRangeTask(myCartRangeTask);
       }
   }

    /**
     * Initialize the adapter and display the Viewpager
     * @param updateCarts
     */
    private void displayCarts(boolean updateCarts){
        if (!updateCarts) {
            mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(),myGlobalCartsInfo);
            mViewPager.setOffscreenPageLimit(2);
            mViewPager.setAdapter(mSectionPagerAdapter);

            mCartCountIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
            mCartCountIndicator.setViewPager(mViewPager);
        } else {
            mSectionPagerAdapter.setPagerItems(myGlobalCartsInfo);
            mSectionPagerAdapter.notifyDataSetChanged();
            mCartCountIndicator.notifyDataSetChanged();
            mViewPager.invalidate();
            if(!isCartUpdated) {
                isCartUpdated = true;
            }
            this.onResume();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myGlobalCartsInfo.reset();
        cartMap = null;
        cartMapIndex = null;

    }

    //get global Object
    public void getGlobalCartInfo(){
        myGlobalCartsInfo = (Cart) getApplicationContext();
        mCartCount = myGlobalCartsInfo.getCartCount();
    }

    //Maintain map index for Custom Order Settings
    private void maintainMapIndex(){
        if (cartMapIndex == null) {
            cartMapIndex = new LinkedHashMap<>();
        }
        for (int i = 0; i < mCartCount; i++) {
            int cartNumber = (Integer)(cartMap.keySet().toArray()[i]);
            cartMapIndex.put(cartNumber,i);
        }

    }

    //Clear Map Index when no longer needed
    private void clearMapIndex(){
        if(cartMapIndex != null && !cartMapIndex.isEmpty()){
            cartMapIndex = null;
        }
    }

}
