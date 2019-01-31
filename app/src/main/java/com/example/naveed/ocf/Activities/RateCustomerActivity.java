package com.example.naveed.ocf.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Helper.ValidationUtility;
import com.example.naveed.ocf.Models.OrderDetails;
import com.example.naveed.ocf.R;

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
                BaseActivity.startActivity(this, OrderActivity.class);
                finish();





        }


    }

}
