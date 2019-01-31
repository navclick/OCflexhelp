package com.example.naveed.ocf.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.naveed.ocf.Adapters.ServiceOrderAdapter;
import com.example.naveed.ocf.Base.GeneralCallBack;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Models.OrdersResponse;
import com.example.naveed.ocf.Network.RestClient;
import com.example.naveed.ocf.R;

import com.example.naveed.ocf.Base.BaseActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends BaseActivity {

    public List<OrdersResponse.OdersValue> ListActiveOrders = new ArrayList<>();

    public RecyclerView recyclerViewActiveOrder;

    public ServiceOrderAdapter mAdapterActiveOrder;

    final private int  REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);



        recyclerViewActiveOrder = (RecyclerView) findViewById(R.id.recycler_view_ActiveOrders);

        mAdapterActiveOrder = new ServiceOrderAdapter(this.ListActiveOrders);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //recyclerViewAllCat.addItemDecoration(itemDecoration);

        recyclerViewActiveOrder.setHasFixedSize(true);
        recyclerViewActiveOrder.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewActiveOrder.setAdapter(this.mAdapterActiveOrder);









        GetOrders();
        GetPermissions();


    }

    @TargetApi(Build.VERSION_CODES.M)
    private void GetPermissions(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP){
                List<String> permissionsNeeded = new ArrayList<String>();

                final List<String> permissionsList = new ArrayList<String>();
                if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
                    permissionsNeeded.add("GPS");
                if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
                    permissionsNeeded.add("Location");
                if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE))
                    permissionsNeeded.add("Call");

                if (permissionsList.size() > 0) {
                    if (permissionsNeeded.size() > 0) {


                    }
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                    return;
                }

            }






            return;
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted

                   // setMapForV6();

                } else {
                    // Permission Denied
                    // Toast.makeText(this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                    //       .show();

                    showMessageDailog(getString(R.string.app_name),Constants.MESSAGE_REQUESTED_PERMISSION_DENIED);

                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    public void GetOrders(){

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
                if(!response.getIserror()){

                    List<OrdersResponse.OdersValue> list = response.getValue();
                    for(OrdersResponse.OdersValue obj : list){

                        if(obj.getStatusId() < Constants.ORDER_COMPLETED) {
                            ListActiveOrders.add(obj);
                        }
                    }




                    mAdapterActiveOrder.notifyDataSetChanged();

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



}
