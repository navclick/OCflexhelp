package com.example.naveed.ocf.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.naveed.ocf.Activities.MyOrdersActivity;
import com.example.naveed.ocf.Activities.OrderDetailsActivity;
import com.example.naveed.ocf.Activities.OrderHistoryDetailsActivity;
import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Models.OrderDetails;
import com.example.naveed.ocf.Models.OrdersResponse;
import com.example.naveed.ocf.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class MyordersHistoryAdaptar extends RecyclerView.Adapter<MyordersHistoryAdaptar.ListViewHolder>{

    private List<OrdersResponse.OdersValue> itemList;
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txt_service_name,txt_service_date,txt_amount;
        public Button btn_view;
        public RatingBar rating_barhis;

        private String mItem;
        private TextView mTextView;
        public ListViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);


            txt_service_name = (TextView) view.findViewById(R.id.txt_service_name);
            txt_amount = (TextView) view.findViewById(R.id.txt_amount);
            txt_service_date = (TextView) view.findViewById(R.id.txt_service_date);
            rating_barhis = (RatingBar) view.findViewById(R.id.rating_barh);

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
                OrderDetails.Message=Order.getAssociateRatingMessage().toString();
                OrderDetails.Rating=Order.getRatingByAssociate().toString();
                BaseActivity.startActivity(view.getContext(), OrderHistoryDetailsActivity.class);
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





    public MyordersHistoryAdaptar(List<OrdersResponse.OdersValue> itemlist) {
        this.itemList = itemlist;

    }



    @Override
    public MyordersHistoryAdaptar.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.myorder_history, parent, false);

        return new MyordersHistoryAdaptar.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyordersHistoryAdaptar.ListViewHolder holder, int position) {
        try {
            OrdersResponse.OdersValue Order = itemList.get(position);



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


            holder.rating_barhis.setMax(5);


            Log.d("test",Order.getServiceName());
            holder.txt_service_name.setText(Order.getServiceName());

            holder.txt_service_date.setText(formattedDate);
            holder.txt_amount.setText("$ "+Order.getAmount().toString());

            Log.d(Constants.TAG,"Ser"+ String.valueOf(Order.getRatingByAssociate()));
            float rating = (float) Order.getRatingByAssociate();
            Log.d(Constants.TAG,String.valueOf(rating));
            holder.rating_barhis.setRating(Float.parseFloat("2.0"));

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