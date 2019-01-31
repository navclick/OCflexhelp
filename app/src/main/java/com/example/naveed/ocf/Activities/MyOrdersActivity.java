package com.example.naveed.ocf.Activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.naveed.ocf.Adapters.MyordersActiveAdaptar;
import com.example.naveed.ocf.Adapters.MyordersHistoryAdaptar;
import com.example.naveed.ocf.Adapters.ServiceOrderAdapter;
import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Base.GeneralCallBack;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Models.OrdersResponse;
import com.example.naveed.ocf.Network.RestClient;
import com.example.naveed.ocf.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends BaseActivity {

    public List<OrdersResponse.OdersValue> ListActiveOrders = new ArrayList<>();
    public List<OrdersResponse.OdersValue> ListHistoreOrders = new ArrayList<>();



    public RecyclerView recycler_view_myorder_active;

    public MyordersActiveAdaptar mAdapterActiveOrder;

    public RecyclerView recycler_view_myorder_history;

    public MyordersHistoryAdaptar mAdapterHistoryOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order);



        recycler_view_myorder_active = (RecyclerView) findViewById(R.id.recycler_view_myorder_active);

        recycler_view_myorder_history = (RecyclerView) findViewById(R.id.recycler_view_myorder_history);


        mAdapterActiveOrder = new MyordersActiveAdaptar(this.ListActiveOrders);
        mAdapterHistoryOrder = new MyordersHistoryAdaptar(this.ListActiveOrders);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        RecyclerView.LayoutManager mLayoutManagerHistory = new LinearLayoutManager(getApplicationContext());

        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //recyclerViewAllCat.addItemDecoration(itemDecoration);

        recycler_view_myorder_active.setHasFixedSize(true);
        recycler_view_myorder_active.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recycler_view_myorder_active.setAdapter(this.mAdapterActiveOrder);


        recycler_view_myorder_history.setHasFixedSize(true);
        recycler_view_myorder_history.setLayoutManager(mLayoutManagerHistory);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recycler_view_myorder_history.setAdapter(this.mAdapterHistoryOrder);











        GetActiveOrders();


    }


    public void GetActiveOrders(){

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

                        if(obj.getStatusId() < Constants.ORDER_ACTIVE) {
                            ListActiveOrders.add(obj);
                        }

                        if(obj.getStatusId() == Constants.ORDER_COMPLETED || obj.getStatusId()==Constants.ORDER_CANCELLED_BY_ASSOCIATE || obj.getStatusId()==Constants.ORDER_CANCELLED_BY_CUSTOMER){

                            ListHistoreOrders.add(obj);

                        }
                    }




                    mAdapterActiveOrder.notifyDataSetChanged();
                    mAdapterHistoryOrder.notifyDataSetChanged();

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
