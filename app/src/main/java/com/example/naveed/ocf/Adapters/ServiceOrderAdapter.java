package com.example.naveed.ocf.Adapters;

import android.app.Application;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naveed.ocf.Activities.OrderActivity;
import com.example.naveed.ocf.Activities.OrderDetailsActivity;
import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Models.OrderDetails;
import com.example.naveed.ocf.Models.OrdersResponse;
import com.example.naveed.ocf.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class ServiceOrderAdapter extends RecyclerView.Adapter<ServiceOrderAdapter.ListViewHolder>{

    private List<OrdersResponse.OdersValue> itemList;
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txt_service_name,txt_service_time,txt_customer_name,txt_customer_phone,txt_Cust_address,txt_service_Date;
        public LinearLayout layoutBG;

        private String mItem;
        private TextView mTextView;
        public ListViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);


            txt_service_name = (TextView) view.findViewById(R.id.txt_service_name);
            txt_service_time = (TextView) view.findViewById(R.id.txt_service_time);
            txt_customer_name = (TextView) view.findViewById(R.id.txt_customer_name);
            txt_customer_phone = (TextView) view.findViewById(R.id.txt_customer_phone);

            txt_Cust_address = (TextView) view.findViewById(R.id.txt_Cust_address);
            txt_service_Date = (TextView) view.findViewById(R.id.txt_service_Date);
            layoutBG = (LinearLayout) view.findViewById(R.id.layoutbg);

            view.setOnClickListener(this);

        }



        @Override
        public void onClick(View view) {
   Log.d(Constants.TAG,"Click");

    try {
        OrdersResponse.OdersValue Order = itemList.get(getPosition());

        String newString = Order.getStartDate().replaceAll(",", ":");

        newString = newString.replace("/", "");


        //String dateStr = "Wednesday: January 23: 2019  2:15:00 AM";

        DateFormat readFormat = new SimpleDateFormat("EEEE: MMMM dd: yyyy  HH:mm:ss aaa");

        DateFormat writeFormat = new SimpleDateFormat("HH:mm:ss");

        DateFormat writeFormatForDate = new SimpleDateFormat("dd MMM EEE");
        Date date = null;
        date = readFormat.parse(newString);


        String formattedTime = "";
        String formattedDate = "";
        if (date != null) {
            formattedTime = writeFormat.format(date);
            formattedDate = writeFormatForDate.format(date);

        }


        OrderDetails.CustomerAddress = Order.getAddress();
        OrderDetails.CustomerName = Order.getCustomer();
        OrderDetails.CustomerPhone = "";
        OrderDetails.OrderNumber = Order.getId();
        OrderDetails.OrderStatus = Order.getStatusId();
        OrderDetails.Price = "$ " + Order.getAmount().toString();
        OrderDetails.ServiceDate = formattedDate;
        OrderDetails.ServiceTime = formattedTime + " - " + formattedTime;
        OrderDetails.ServiceName = Order.getServiceName();
        BaseActivity.startActivity(view.getContext(), OrderDetailsActivity.class);
    }
    catch (Exception e){



    }

            // Log.d("APITEST:", "onClick " + getPosition() + " " + mItem);
/*

            switch (view.getId()) {
                case R.id.btn_cart_plus:


                    break;

                case R.id.btn_cartitem_minus:
                    Log.d("test","show msg call");
                    //  showMessageDailogNextScreen("test","testing message",Login.class);

                    break;

                case R.id.txt_cartitem_remove:
                    Log.d("test","show msg call");
                    //  showMessageDailogNextScreen("test","testing message",Login.class);
                    int newPosition = getAdapterPosition();
                    Cart.removeCartITem(itemList.get(newPosition).ItemID,view.getContext());
                    itemList.remove(newPosition);
                    notifyItemRemoved(newPosition);
                    notifyItemRangeChanged(newPosition, itemList.size());

                    break;



            }

*/

        }


    }





    public ServiceOrderAdapter(List<OrdersResponse.OdersValue> itemlist) {
        this.itemList = itemlist;

    }



    @Override
    public ServiceOrderAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_active_order, parent, false);

        return new ServiceOrderAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ServiceOrderAdapter.ListViewHolder holder, int position) {
        try {
            OrdersResponse.OdersValue Order = itemList.get(position);
            View view = holder.layoutBG;
            Resources resources = view.getContext().getResources();

           // String f="Wednesday, January 23, 2019 / 2:15:00 AM";
           // String dateStr = "Wednesday: January 23: 2019  2:15:00 AM";

            String newString = Order.getStartDate().replaceAll(",", ":");

            newString=newString.replace("/","");


            //String dateStr = "Wednesday: January 23: 2019  2:15:00 AM";

            DateFormat readFormat = new SimpleDateFormat( "EEEE: MMMM dd: yyyy  HH:mm:ss aaa");

            DateFormat writeFormat = new SimpleDateFormat( "HH:mm:ss");

            DateFormat writeFormatForDate = new SimpleDateFormat( "dd MMM EEE");
            Date date = null;
                date = readFormat.parse( newString );



            String formattedTime = "";
            String formattedDate = "";
            if( date != null ) {
                formattedTime = writeFormat.format( date );
                formattedDate = writeFormatForDate.format( date );

            }


            Log.d(Constants.TAG,formattedTime +" - " + formattedTime);





            holder.txt_service_name.setText(Order.getServiceName());
            holder.txt_service_time.setText(formattedTime +" - " + formattedTime);
            holder.txt_customer_name.setText(Order.getCustomer());

            holder.txt_customer_phone.setText("+923332422328");
            holder.txt_Cust_address.setText(Order.getAddress());
            holder.txt_service_Date.setText(formattedDate);


            if(Order.getStatusId() == Constants.ORDER_ACTIVE) {
                holder.layoutBG.setBackground(resources.getDrawable(R.drawable.rc_grad_order_activity));
            }
            else{
                holder.layoutBG.setBackground(resources.getDrawable(R.drawable.rc_purple_grad_order_activity));


            }

            }
        catch (Exception e){

            Log.d(Constants.TAG,e.getMessage().toString());

        }



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }





}
