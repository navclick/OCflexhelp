package com.example.naveed.ocf.Base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.naveed.ocf.Activities.Login;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Helper.ProgressLoader;
import com.example.naveed.ocf.Helper.TokenHelper;
import com.example.naveed.ocf.Models.StatusUpdateRequest;
import com.example.naveed.ocf.Models.StatusUpdateResponse;
import com.example.naveed.ocf.Network.RestClient;
import com.example.naveed.ocf.R;
import com.google.gson.Gson;

public class BaseActivity extends AppCompatActivity{

    //Declarations
    protected ViewDataBinding parentBinding;
    public TokenHelper tokenHelper;
    private ProgressLoader progressLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializations
        tokenHelper = new TokenHelper(this);
    }

    public void OpenActivity(Class activity){
        startActivity(new Intent(this,activity));
    }

    public <S> void OpenActivity(Class<S> activity, Boolean isTokenCheck){
        if(tokenHelper.GetToken() == null || tokenHelper.GetToken() == ""){
            startActivity(new Intent(this, Login.class));
        }else{
            startActivity(new Intent(this, activity));
        }
    }

    public View getView() {
        if (parentBinding != null)
            return parentBinding.getRoot();
        else return null;
    }


    public void showProgress()
    {
        try {
            if (progressLoader == null)
            {
                progressLoader = new ProgressLoader();
            }

            progressLoader.show(getSupportFragmentManager(), Constants.TAG);
        }
        catch (IllegalStateException e)
        {
           // Log.e(TAG, "showProgress:" + e.getMessage());
        }

    }

    public void hideProgress() {
        if (progressLoader != null) {
            try {
                progressLoader.dismissAllowingStateLoss();
            } catch (Exception e) {
                Log.e(Constants.TAG, "hideProgress:" + e.getMessage());
            }
        }
    }


    public static void startActivity(Context context, Class activity) {

        Intent intent = new Intent(context, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);

    }


    public  void UpdateOrderStatus(int orderID,int StatusID, String Reason){

        StatusUpdateRequest requestObj = new StatusUpdateRequest();
        requestObj.setOrderid(orderID);
        requestObj.setStatusid(StatusID);
        requestObj.setReason(Reason);
        showProgress();

        Gson g = new Gson();
        String userJson= g.toJson(requestObj);
        Log.d("test", userJson);
        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).registerUser(requestObj).enqueue(new GeneralCallBack<StatusUpdateResponse>(this) {
            @Override
            public void onSuccess(StatusUpdateResponse response) {
                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d("test", Reslog);


                hideProgress();

                if(!response.getIserror()){

                  //  showMessageDailog(getString(R.string.app_name),Constants.MSG_SERVICE_STATUS_UPDATED);

                }
                else{

                    //showMessageDailog(getString(R.string.app_name),response.getMessage());

                }


            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class

                showMessageDailog(getString(R.string.app_name),throwable.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Failed",
                        Toast.LENGTH_LONG).show();

                hideProgress();
                Log.d("test", "failed");

            }






        });

    }

    public void showMessageDailog(String title, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

       /* builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();



    }

    public void logOut(){

        tokenHelper.removeALL();
        // openActivity(Login.class);
        startActivity(this,Login.class);
    }


}
