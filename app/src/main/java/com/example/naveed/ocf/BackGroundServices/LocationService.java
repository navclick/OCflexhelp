package com.example.naveed.ocf.BackGroundServices;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;


import com.example.naveed.ocf.Activities.OrderActivity;
import com.example.naveed.ocf.Base.GeneralCallBack;
import com.example.naveed.ocf.Base.GeneralCallBackService;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Helper.TokenHelper;
import com.example.naveed.ocf.Models.AddLocationRequest;
import com.example.naveed.ocf.Models.AddLocationResponse;
import com.example.naveed.ocf.Models.StatusUpdateRequest;
import com.example.naveed.ocf.Models.StatusUpdateResponse;
import com.example.naveed.ocf.Network.RestClient;
import com.example.naveed.ocf.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

public class LocationService extends Service implements LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    public TokenHelper tokenHelper;
    Intent intent;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    String lat, lon;
    public static String str_receiver = "servicetutorial.service.receiver";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        Log.d(Constants.TAG, "Service started!!!!!!!!!!");

        super.onCreate();
        tokenHelper = new TokenHelper(this);
        buildGoogleApiClient();
        intent = new Intent(str_receiver);

    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(Constants.TAG, "LOcation!!!!!!!!!!");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(20000);
        mLocationRequest.setSmallestDisplacement(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
   /* if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

    }*/

        //use if you want location update


        Log.d(Constants.TAG, "Granted");
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


        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());



            Log.d(Constants.TAG,lat);
            Log.d(Constants.TAG,lon);

        }
        else{
            Log.d(Constants.TAG,"location filed");
            //Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

/*@Override
public void onConnected(@Nullable Bundle bundle) {

}*/

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(Constants.TAG,"after 30");

        if(tokenHelper.GetToken()==null || tokenHelper.GetToken()=="") {


        }
        else{

            UpdateLocationOnServer(lat,lon);
        }

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(Constants.TAG,"Connection field");
    }


    public void UpdateLocationOnServer(String Lat, String Lng){

        AddLocationRequest requestObj = new AddLocationRequest();
        requestObj.setLatitude(Lat);
        requestObj.setLongitude(Lng);



        Gson g = new Gson();
        String userJson = g.toJson(requestObj);
        Log.d("test", userJson);
        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).AddLocation(requestObj).enqueue(new GeneralCallBackService<AddLocationResponse>(this) {
            @Override
            public void onSuccess(AddLocationResponse response) {
                Gson gson = new Gson();
                String Reslog = gson.toJson(response);
                Log.d("test", Reslog);




                if (!response.getIserror()) {

                    //  showMessageDailog(getString(R.string.app_name),Constants.MSG_SERVICE_STATUS_UPDATED);

                    Toast.makeText(getApplicationContext(), "Location Updated",
                            Toast.LENGTH_LONG).show();

                } else {

                    //showMessageDailog(getString(R.string.app_name),response.getMessage());

                    Toast.makeText(getApplicationContext(), "Location Not Updated Server Error",
                            Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class


                Toast.makeText(getApplicationContext(), "Failed To Update Location",
                        Toast.LENGTH_LONG).show();


                Log.d("test", "failed");

            }


        });

    }

}
