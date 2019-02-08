package com.example.naveed.ocf.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Base.GeneralCallBack;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Helper.ValidationUtility;
import com.example.naveed.ocf.Models.OrderDetails;
import com.example.naveed.ocf.Models.RatingRequest;
import com.example.naveed.ocf.Models.RatingResponse;
import com.example.naveed.ocf.Models.StatusUpdateRequest;
import com.example.naveed.ocf.Models.StatusUpdateResponse;
import com.example.naveed.ocf.Network.RestClient;
import com.example.naveed.ocf.R;
import com.google.gson.Gson;

public class RateCustomerActivity  extends BaseActivity implements View.OnClickListener {

    RatingBar starts ;
    EditText txt_msg;
    Button btn_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialouge_experince);

        txt_msg= (EditText) findViewById(R.id.txt_msg);
        btn_sub= (Button) findViewById(R.id.btn_sub);
        starts = (RatingBar) findViewById(R.id.ratingBar);
        //txt_msg.setText("N/A");
        starts.setMax(5);
        starts.setRating(1);
        btn_sub.setOnClickListener(this);


    }


    private boolean isValidate() {
        if (!ValidationUtility.edittextValidator(txt_msg)) {
            return false;
        }



        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sub:

                Rate();




        }


    }


    public void Rate() {
    if(txt_msg.getText().toString().isEmpty() || txt_msg.getText().toString().contentEquals("")){

        txt_msg.setText("N/A");

    }
        RatingRequest requestObj = new RatingRequest();
        requestObj.setOrderId(OrderDetails.OrderNumber);
        requestObj.setRating(starts.getNumStars());
        requestObj.setRatingMessage(txt_msg.getText().toString());
        showProgress();

        Gson g = new Gson();
        String userJson = g.toJson(requestObj);
        Log.d("test", userJson);
        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).rateCustomer(requestObj).enqueue(new GeneralCallBack<RatingResponse>(this) {
            @Override
            public void onSuccess(RatingResponse response) {
                Gson gson = new Gson();
                String Reslog = gson.toJson(response);
                Log.d("test", Reslog);


                hideProgress();

                if (!response.getIserror()) {

                    OpenActivity(OrderActivity.class);
                    finish();

                    //  showMessageDailog(getString(R.string.app_name),Constants.MSG_SERVICE_STATUS_UPDATED);

                } else {

                    //showMessageDailog(getString(R.string.app_name),response.getMessage());

                }


            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class

                showMessageDailog(getString(R.string.app_name), throwable.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Failed",
                        Toast.LENGTH_LONG).show();

                hideProgress();
                Log.d("test", "failed");

            }


        });

    }

}
