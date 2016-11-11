package com.akshay.nearbycartsimulator;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.akshay.nearbycartsimulator.model.Cart;
import com.akshay.nearbycartsimulator.ui.ItemAdapter;
import com.akshay.nearbycartsimulator.util.AppUtils;
import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class allows the User to Reorder the Cart Positions
 * Allows Random or Custom Ordering
 */

public class CartViewOrderActivity extends AppCompatActivity {

    private ArrayList<Pair<Long, String>> mItemArray;
    private DragListView mDragListView;
    private Switch myOrderSwitch;
    private Button applyOrderButton;
    private Map<Integer,Integer> orderMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize the views
        setContentView(R.layout.activity_cart_view_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.cart_order_toolbar);
        setSupportActionBar(toolbar);
        myOrderSwitch = (Switch) findViewById(R.id.order_switch);
        applyOrderButton = (Button) findViewById(R.id.apply_cart_order);

        //get the globalCartMap
        final Cart globalCartInfo = (Cart) getApplicationContext();
        int count = globalCartInfo.getCartCount();
        orderMap = new LinkedHashMap<>();
        orderMap = globalCartInfo.getCartMap();

        //load the Cart Names
        String[] myCartNames = globalCartInfo.getCartNames();

        //initialize the dragable listview
        mDragListView = (DragListView) findViewById(R.id.drag_list_view);

        //initialize the switch
        myOrderSwitch.setChecked(globalCartInfo.isRandomSelectionEnabled());
        myOrderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDragListView.setDragEnabled(false);
                } else {
                    mDragListView.setDragEnabled(true);
                }
            }
        });


        mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);

        //drag listener for dragable listview
        mDragListView.setDragListListener(new DragListView.DragListListener() {
            @Override
            public void onItemDragStarted(int position) {
                Toast.makeText(mDragListView.getContext(), "Start - position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                if (fromPosition != toPosition) {
                    //Update the local map with new position once the drag is complete
                    int cartNumber = (Integer)(orderMap.keySet().toArray()[fromPosition]);
                    int cartStrength = orderMap.get(cartNumber);
                    orderMap.remove(cartNumber);
                     AppUtils.put((LinkedHashMap) orderMap, toPosition, cartNumber, cartStrength);
                    //show the user all the cart strength as well
                    Toast.makeText(mDragListView.getContext(), "AllPosition: " + orderMap.values().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //load the names and Cart id
        mItemArray = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int cartNumber = (Integer)(orderMap.keySet().toArray()[i]);
            mItemArray.add(new Pair<>(Long.valueOf(cartNumber), myCartNames[cartNumber - 1]));
        }

        applyOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myOrderSwitch.isChecked()) {
                    globalCartInfo.setCartMap(orderMap);
                    globalCartInfo.setRandomSelectionState(false);
                } else {
                    globalCartInfo.setRandomSelectionState(true);
                }
                //close the activity
                finish();
            }
        });

        setupListRecyclerView();
    }

    private void setupListRecyclerView() {
        mDragListView.setLayoutManager(new LinearLayoutManager(this));
        ItemAdapter listAdapter = new ItemAdapter(mItemArray, R.layout.content_cart_view_order, R.id.cart_list_image, true);
        mDragListView.setAdapter(listAdapter, true);
        mDragListView.setDragEnabled(!myOrderSwitch.isChecked());
        mDragListView.setCanDragHorizontally(false);
        mDragListView.setCustomDragItem(new MyDragItem(this, R.layout.content_cart_view_order));
    }

    //Individual Dragable item with Custom Drag View
    private static class MyDragItem extends DragItem {

        public MyDragItem(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindDragView(View clickedView, View dragView) {
            CharSequence text = ((TextView) clickedView.findViewById(R.id.cart_name)).getText();
            ((TextView) dragView.findViewById(R.id.cart_name)).setText(text);
            dragView.setBackgroundColor(ContextCompat.getColor(dragView.getContext(), R.color.white));
        }
    }

}
