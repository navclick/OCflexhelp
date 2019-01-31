package com.example.naveed.ocf.Network;

public class EndPoints {
    public static final String API_PREFIX = "api/";

    public static final String LOGIN = "oauth/token";
    public static final String REGISTER = API_PREFIX + "accounts/create";
    public  static final String GET_CUSTOMER_SERVICES = API_PREFIX + "customerservice/getcustomerservice";

    static final String GET_ORDERS=API_PREFIX+"order/getassignorders";
    static final String POST_UPDATE_ORDER=API_PREFIX+"order/updateorderstatus";
}