package com.example.naveed.ocf.Activities;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Models.OrderDetails;
import com.example.naveed.ocf.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class OrderDetailsActivity extends BaseActivity implements OnMapReadyCallback,View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

public TextView txt_order_status,txt_order_number,txt_order_Price,txt_order_service_name,txt_order_date,txt_order_time,txt_order_customer_address;
public Button btn_active,btn_complete,btn_cancel;

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_order_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ordersdetails);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_orderdetails);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_orderdetails);
        navigationView.setNavigationItemSelectedListener(this);





        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        txt_order_status = (TextView) findViewById(R.id.txt_order_status);
        txt_order_number = (TextView) findViewById(R.id.txt_order_number);
        txt_order_Price = (TextView) findViewById(R.id.txt_order_Price);
        txt_order_service_name = (TextView) findViewById(R.id.txt_order_service_name);
        txt_order_date = (TextView) findViewById(R.id.txt_order_date);
        txt_order_time = (TextView) findViewById(R.id.txt_order_time);

        txt_order_customer_address = (TextView) findViewById(R.id.txt_order_customer_address);


        txt_order_status.setText(Constants.OrderStatus.get(OrderDetails.OrderStatus));
        txt_order_number.setText(String.valueOf(OrderDetails.OrderNumber));
        txt_order_Price.setText(OrderDetails.Price);
        txt_order_service_name.setText(OrderDetails.ServiceName);
        txt_order_date.setText(OrderDetails.ServiceDate);
        txt_order_time.setText(OrderDetails.ServiceTime);
        txt_order_customer_address.setText(OrderDetails.CustomerAddress);

        btn_active = (Button) findViewById(R.id.btn_active);
        btn_complete = (Button) findViewById(R.id.btn_complete);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        setButton(OrderDetails.OrderStatus);
        btn_active.setOnClickListener(this);
        btn_complete.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        LatLng custLocation = getLocationFromAddress(this,OrderDetails.CustomerAddress);

        Log.d(Constants.TAG,"cusLoc"+ String.valueOf(custLocation.latitude));
      //  mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
        float zoomLevel = (float) 15.0;

        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(custLocation);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(OrderDetails.CustomerName);

        // Clears the previously touched position



        mMap.addMarker(markerOptions).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(custLocation,zoomLevel));

    }

    public void setButton(int status){
btn_active.setVisibility(View.GONE);
        btn_complete.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);

        switch (status) {
            case 1:
                btn_active.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);

                break;
            case 2:
                btn_active.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                break;
            case 3:
                btn_complete.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                break;

        }

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_active:

                OrderDetails.OrderStatus=Constants.ORDER_ACTIVE;
                UpdateOrderStatus(OrderDetails.OrderNumber,OrderDetails.OrderStatus,"N/A");
                BaseActivity.startActivity(this, OrderActivity.class);

                break;

            case R.id.btn_complete:

                OrderDetails.OrderStatus=Constants.ORDER_COMPLETED;
                //UpdateOrderStatus(OrderDetails.OrderNumber,OrderDetails.OrderStatus,"N/A");
                BaseActivity.startActivity(this, RateCustomerActivity.class);

                break;

            case R.id.btn_cancel:



                BaseActivity.startActivity(this, CalcelActivity.class);

                break;

        }


    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_orderdetails);



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
        }

        else if (id == R.id.menu_payments) {
            mDrawerLayout.closeDrawers();
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



}
