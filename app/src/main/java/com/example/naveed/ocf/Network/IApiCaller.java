package com.example.naveed.ocf.Network;

import com.example.naveed.ocf.Models.CustomerService;
import com.example.naveed.ocf.Models.GeneralResponse;
import com.example.naveed.ocf.Models.SignUpRequest;
import com.example.naveed.ocf.Models.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IApiCaller {

    // Login starts
    @FormUrlEncoded
    @POST(EndPoints.LOGIN)
    Call<Token> GetToken(@Field("username") String username,
                         @Field("password") String password,
                         @Field("grant_type") String grant_type);
    // Login ends

    // Register starts here
    @POST(EndPoints.REGISTER)
    Call<GeneralResponse> Register(@Body SignUpRequest signup);
    // Register ends here

    // Customer Service starts
    @GET(EndPoints.GET_CUSTOMER_SERVICES)
    Call<CustomerService> GetCustomerServices();
    // Customer Service ends
}
