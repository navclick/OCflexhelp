package com.example.naveed.ocf.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naveed.ocf.Adapters.MyordersActiveAdaptar;
import com.example.naveed.ocf.Adapters.MyordersHistoryAdaptar;
import com.example.naveed.ocf.Adapters.ServiceOrderAdapter;
import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Base.GeneralCallBack;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Models.OrdersResponse;
import com.example.naveed.ocf.Network.RestClient;
import com.example.naveed.ocf.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends BaseActivity implements  NavigationView.OnNavigationItemSelectedListener{

    public List<OrdersResponse.OdersValue> ListActiveOrders = new ArrayList<>();
    public List<OrdersResponse.OdersValue> ListHistoreOrders = new ArrayList<>();



    public RecyclerView recycler_view_myorder_active;

    public MyordersActiveAdaptar mAdapterActiveOrder;

    public RecyclerView recycler_view_myorder_history;

    public MyordersHistoryAdaptar mAdapterHistoryOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order);


        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_myorders);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_myorders);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_myorder);
        navigationView.setNavigationItemSelectedListener(this);


        View header = navigationView.getHeaderView(0);
        TextView t = (TextView) header.findViewById(R.id.txt_main_name);
        TextView tEmail = (TextView) header.findViewById(R.id.txt_email);
        ImageView profile_img= (ImageView) header.findViewById(R.id.img_nav_profile);
        tEmail.setText(tokenHelper.GetUserEmail());

        t.setText(tokenHelper.GetUserName());
        Log.d(Constants.TAG,tokenHelper.GetUserPhoto());
        profile_img.setBackground(getResources().getDrawable(R.drawable.profile_image_border));
        Picasso.with(this).load(tokenHelper.GetUserPhoto()).resize(110, 110).centerCrop().into(profile_img);



        recycler_view_myorder_active = (RecyclerView) findViewById(R.id.recycler_view_myorder_active);

        recycler_view_myorder_history = (RecyclerView) findViewById(R.id.recycler_view_myorder_history);


        mAdapterActiveOrder = new MyordersActiveAdaptar(this.ListActiveOrders);
        mAdapterHistoryOrder = new MyordersHistoryAdaptar(this.ListHistoreOrders);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        RecyclerView.LayoutManager mLayoutManagerHistory = new LinearLayoutManager(getApplicationContext());

        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //recyclerViewAllCat.addItemDecoration(itemDecoration);

        recycler_view_myorder_active.setHasFixedSize(true);
        recycler_view_myorder_active.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recycler_view_myorder_active.setAdapter(this.mAdapterActiveOrder);


        recycler_view_myorder_history.setHasFixedSize(true);
        recycler_view_myorder_history.setLayoutManager(mLayoutManagerHistory);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recycler_view_myorder_history.setAdapter(this.mAdapterHistoryOrder);











        GetActiveOrders();


    }


    public void GetActiveOrders(){

        showProgress();
        //Gson gson = new Gson();
        //String Reslog= gson.toJson(obj);
        Log.d("test", "call");
        Log.d("test", tokenHelper.GetToken());

        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).GetOrders().enqueue(new GeneralCallBack<OrdersResponse>(this) {
            @Override
            public void onSuccess(OrdersResponse response) {
                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d(Constants.TAG, Reslog);
                ListActiveOrders.clear();
                ListHistoreOrders.clear();
                if(!response.getIserror()){

                    List<OrdersResponse.OdersValue> list = response.getValue();
                    for(OrdersResponse.OdersValue obj : list){

                        if(obj.getStatusId() < Constants.ORDER_ACTIVE) {
                            ListActiveOrders.add(obj);
                        }

                        if(obj.getStatusId() == Constants.ORDER_COMPLETED || obj.getStatusId()==Constants.ORDER_CANCELLED_BY_ASSOCIATE || obj.getStatusId()==Constants.ORDER_CANCELLED_BY_CUSTOMER){

                            ListHistoreOrders.add(obj);

                        }
                    }




                    mAdapterActiveOrder.notifyDataSetChanged();
                    mAdapterHistoryOrder.notifyDataSetChanged();

                }

                hideProgress();



            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                hideProgress();
                Log.d(Constants.TAG,"failed");

            }



        });
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_myorders);



        if (id == R.id.menu_myorders) {
            // Handle the camera action
            mDrawerLayout.closeDrawers();
            // openActivityWithFinish(AboutActivity.class);
            BaseActivity.startActivity(this,MyOrdersActivity.class);


        }  else if (id == R.id.menu_profile) {
            mDrawerLayout.closeDrawers();
            // openActivityProfile();
            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());

            BaseActivity.startActivity(this,EditProfileActivity.class);
        }

        else if (id == R.id.menu_payments) {
            mDrawerLayout.closeDrawers();

            BaseActivity.startActivity(this,PaymentActivity.class);
            // openActivity(ShoppingListActivity.class);
            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
        }

        else if (id == R.id.menu_Schedules) {
            mDrawerLayout.closeDrawers();
            // openActivity(AllCatActivity.class);

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
            BaseActivity.startActivity(this, OrderActivity.class);
        }


        else if (id == R.id.menu_logout) {
            mDrawerLayout.closeDrawers();
            // openActivity(AllCatActivity.class);

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
            logOut();
        }
        
        return  true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     /*   getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.menu_cart);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(item);
        i =notifCount.findViewById(R.id.actionbar_notifcation_img);
        tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        //tv.setText("12");
        tv.setText("0");
        //   i.setOnClickListener(this);
        //  tv.setOnClickListener(this);
       */
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onBackPressed() {
        OpenActivity(OrderActivity.class);
        finish();
        //System.exit(0);
    }

}
