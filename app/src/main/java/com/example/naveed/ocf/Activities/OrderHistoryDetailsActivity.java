package com.example.naveed.ocf.Activities;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Models.OrderDetails;
import com.example.naveed.ocf.R;
import com.squareup.picasso.Picasso;

public class OrderHistoryDetailsActivity extends BaseActivity implements  View.OnClickListener, NavigationView.OnNavigationItemSelectedListener  {

    public TextView txt_order_number,txt_service_date,txt_service_time,txt_service_name,txt_service_hrs,txt_amount,txt_msg;
    public RatingBar ratingBar2;
    public Button btn_order_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history);


        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ordershis);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_ordershis);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_orderhiss);
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
        txt_order_number=(TextView) findViewById(R.id.txt_order_number);
        txt_service_date=(TextView) findViewById(R.id.txt_service_date);
        txt_service_time=(TextView) findViewById(R.id.txt_service_time);
        txt_service_name=(TextView) findViewById(R.id.txt_service_name);
        txt_service_hrs=(TextView) findViewById(R.id.txt_service_hrs);
        txt_amount=(TextView) findViewById(R.id.txt_amount);
        txt_msg=(TextView) findViewById(R.id.txt_msg);
        ratingBar2=(RatingBar) findViewById(R.id.ratingBar2);


        btn_order_history=(Button) findViewById(R.id.btn_order_history);



        txt_order_number.setText(String.valueOf(OrderDetails.OrderNumber));
        txt_service_date.setText(OrderDetails.ServiceDate);
        txt_service_time.setText(OrderDetails.ServiceDate);;
        txt_service_name.setText(OrderDetails.ServiceName);;
        txt_service_hrs.setText("2");;

        txt_msg.setText(OrderDetails.Message);
        ratingBar2.setRating(Float.parseFloat(OrderDetails.Rating));
        txt_amount.setText(OrderDetails.Price);;

        btn_order_history.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.btn_order_history:
                BaseActivity.startActivity(this,MyOrdersActivity.class);
                break;


        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_ordershis);

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
