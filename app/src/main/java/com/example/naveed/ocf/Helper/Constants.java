package com.example.naveed.ocf.Helper;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    // Network
    public static String BASE_URL = "http://192.168.100.2:82/";
    public final static  long CONNECTION_TIMEOUT = 25;
    public final static int DATA_SUCCESS = 1;
    public final static int DATA_ERROR = 0;

    public final static String TAG = "test";

    // Splash screen
    public static final int SPLASH_TIME_OUT = 3000;


    public static final int ORDER_PENDING = 1;
    public static final int ORDER_ASSIGNED = 2;
    public static final int ORDER_ACTIVE = 3;
    public static final int ORDER_COMPLETED = 4;
    public static final int ORDER_CANCELLED_BY_CUSTOMER = 5;
    public static final int ORDER_CANCELLED_BY_ASSOCIATE = 6;



    public static final Map<Integer, String> OrderStatus = new HashMap<Integer, String>();

    public static void init(){

        OrderStatus.put(1,"Pending");
        OrderStatus.put(2,"Assigned");
        OrderStatus.put(3,"Active");
        OrderStatus.put(4,"Completed");
        OrderStatus.put(5,"Cancelled by Customer");
        OrderStatus.put(6,"Cancelled by Associate");



    }

    public static final String MSG_SERVICE_STATUS_UPDATED= "Service status updated";

    public static final String MSG_SERVICE_STATUS_UPDATE_FAILED= "Service status updatedion failed!";
    public static final String MESSAGE_REQUESTED_PERMISSION_DENIED = "Requested permissions required to continue";




}
