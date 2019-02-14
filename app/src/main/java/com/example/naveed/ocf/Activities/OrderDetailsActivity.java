package com.example.naveed.ocf.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Models.OrderDetails;
import com.example.naveed.ocf.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class OrderDetailsActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, LocationListener {

    public TextView txt_order_status, txt_order_number, txt_order_Price, txt_order_service_name, txt_order_date, txt_order_time, txt_order_customer_address;
    public Button btn_active, btn_complete, btn_cancel,btn_nav;

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap mMap;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    float zoomLevel = (float) 15.0;
    public  LatLng custLocation;

    public Marker MeMarker = null;

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

        View header = navigationView.getHeaderView(0);
        TextView t = (TextView) header.findViewById(R.id.txt_main_name);
        TextView tEmail = (TextView) header.findViewById(R.id.txt_email);
        ImageView profile_img = (ImageView) header.findViewById(R.id.img_nav_profile);
        tEmail.setText(tokenHelper.GetUserEmail());

        t.setText(tokenHelper.GetUserName());
        Log.d(Constants.TAG, tokenHelper.GetUserPhoto());
        profile_img.setBackground(getResources().getDrawable(R.drawable.profile_image_border));
        Picasso.with(this).load(tokenHelper.GetUserPhoto()).resize(110, 110).centerCrop().into(profile_img);


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
        btn_nav = (Button) findViewById(R.id.btn_nav);



        setButton(OrderDetails.OrderStatus);
        btn_active.setOnClickListener(this);
        btn_complete.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_nav.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            showMessageDailog("MAP", Constants.MESSAGE_REQUESTED_PERMISSION_DENIED);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

         custLocation = getLocationFromAddress(this, OrderDetails.CustomerAddress);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);

        if(custLocation != null) {
            Log.d(Constants.TAG, "cusLoc" + String.valueOf(custLocation.latitude));
            //  mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );


            MarkerOptions markerOptions = new MarkerOptions();

            // Setting the position for the marker
            markerOptions.position(custLocation);

            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(OrderDetails.CustomerName);

            // Clears the previously touched position


            mMap.addMarker(markerOptions).showInfoWindow();

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(custLocation, zoomLevel));
        }
        else{
            custLocation=HAMBURG;
            MarkerOptions markerOptions = new MarkerOptions();

            // Setting the position for the marker
            markerOptions.position(HAMBURG);

            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(OrderDetails.CustomerName);

            mMap.addMarker(markerOptions).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, zoomLevel));

        }




    }
    private void startNav(String name,String Lat,String Long){

/*        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=37.423156,-122.084917 (" + name + ")"));
        startActivity(intent);
*/

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q="+Lat+","+Long+" (" + name + ")"));
        startActivity(intent);


    }
    @Override
    public void onStop(){
        super.onStop();


        locationManager.removeUpdates(this);

    }

    @Override
    public void onLocationChanged(Location location) {
        //txtLat = (TextView) findViewById(R.id.textview1);
        //txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        Log.d(Constants.TAG,"Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

        LatLng MyLocation = new LatLng(location.getLatitude(), location.getLongitude());

       // Toast toast = Toast.makeText(getApplicationContext(),
         //       "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(),
           //     Toast.LENGTH_SHORT);

        //toast.show();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MyLocation));
        try {
            locationManager.removeUpdates(this);
        }
        catch (Exception e){


        }
/*
       if (this.MeMarker == null) {




            this.MeMarker = mMap.addMarker(new MarkerOptions()
                    .position(MyLocation)

                    .title("You")
                    .icon(BitmapDescriptorFactory.fromBitmap(
                            BitmapFactory.decodeResource(getResources(),
                                    R.drawable.pin_rickshaw)))
                    .snippet("You")

            );
            this.MeMarker.showInfoWindow();
           // mMap.addMarker(MeMarker).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(MyLocation));


        } else {
           // Log.i("APITEST:", "set" + String.valueOf(rickshawLocation.latitude) + " " + String.valueOf(rickshawLocation.longitude));
            this.MeMarker.setTitle("You");
            this.MeMarker.setPosition(MyLocation);

            this.MeMarker.setSnippet("You");
            this.MeMarker.showInfoWindow();
            this.animateMarker(this.MeMarker, MyLocation, false);

        }


*/


      // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MyLocation,zoomLevel));
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }


    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
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


                break;

            case R.id.btn_complete:

                OrderDetails.OrderStatus=Constants.ORDER_COMPLETED;
                UpdateOrderStatus(OrderDetails.OrderNumber,OrderDetails.OrderStatus,"N/A");
                BaseActivity.startActivity(this, RateCustomerActivity.class);

                break;

            case R.id.btn_cancel:



                BaseActivity.startActivity(this, CalcelActivity.class);

                break;

            case R.id.btn_nav:



                startNav(OrderDetails.CustomerName,String.valueOf(custLocation.latitude),String.valueOf(custLocation.longitude));

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

        } catch (Exception ex) {

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



}
