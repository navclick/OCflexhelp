package com.example.naveed.ocf.Activities;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
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

public class OrderDetailsActivity extends BaseActivity implements OnMapReadyCallback,View.OnClickListener {

public TextView txt_order_status,txt_order_number,txt_order_Price,txt_order_service_name,txt_order_date,txt_order_time,txt_order_customer_address;
public Button btn_active,btn_complete,btn_cancel;

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_order_details);


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


        LatLng sydney = new LatLng(41.819950, -74.314740);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

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



}
