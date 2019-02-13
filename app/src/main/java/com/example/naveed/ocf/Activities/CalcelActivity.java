package com.example.naveed.ocf.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Helper.ValidationUtility;
import com.example.naveed.ocf.Models.OrderDetails;
import com.example.naveed.ocf.R;
import com.google.android.gms.maps.OnMapReadyCallback;

public class CalcelActivity extends BaseActivity implements  View.OnClickListener {

    EditText txt_reason;
    Button btn_dismiss,btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialouge_cancel_order);



        txt_reason= (EditText) findViewById(R.id.txt_reason);
        btn_dismiss= (Button) findViewById(R.id.btn_dismiss);
        btn_ok= (Button) findViewById(R.id.btn_ok);

        btn_dismiss.setOnClickListener(this);
        btn_ok.setOnClickListener(this);

    }


    private boolean isValidate() {
        if (!ValidationUtility.edittextValidator(txt_reason)) {
            return false;
        }



        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dismiss:

                finish();

                break;

            case R.id.btn_ok:
                if(isValidate()) {
                    OrderDetails.OrderStatus = Constants.ORDER_CANCELLED_BY_ASSOCIATE;
                    UpdateOrderStatus(OrderDetails.OrderNumber, OrderDetails.OrderStatus, txt_reason.getText().toString());
                    BaseActivity.startActivity(this, OrderActivity.class);
                }

                break;



        }


    }
}
