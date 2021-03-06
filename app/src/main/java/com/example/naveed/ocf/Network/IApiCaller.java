package com.example.naveed.ocf.Network;

import com.example.naveed.ocf.Models.AddLocationRequest;
import com.example.naveed.ocf.Models.AddLocationResponse;
import com.example.naveed.ocf.Models.CustomerService;
import com.example.naveed.ocf.Models.GeneralResponse;
import com.example.naveed.ocf.Models.GetUserResponse;
import com.example.naveed.ocf.Models.OrdersResponse;
import com.example.naveed.ocf.Models.RatingRequest;
import com.example.naveed.ocf.Models.RatingResponse;
import com.example.naveed.ocf.Models.SignUpRequest;
import com.example.naveed.ocf.Models.StatusUpdateRequest;
import com.example.naveed.ocf.Models.StatusUpdateResponse;
import com.example.naveed.ocf.Models.Token;
import com.example.naveed.ocf.Models.UserUpdateRequest;
import com.example.naveed.ocf.Models.UserUpdateResponse;

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


    @GET(EndPoints.GET_ORDERS)
    Call<OrdersResponse> GetOrders();

    @GET(EndPoints.GET_USER_PROFILE)
    Call<GetUserResponse> GetProfile();


    @POST(EndPoints.POST_UPDATE_ORDER)
    Call<StatusUpdateResponse> registerUser(@Body StatusUpdateRequest OrderStatus);


    @POST(EndPoints.POST_USER_PROFILE_UPDATE)
    Call<UserUpdateResponse> updateProfile(@Body UserUpdateRequest profile);

    @POST(EndPoints.POST_RATING)
    Call<RatingResponse> rateCustomer(@Body RatingRequest Rating);


    @POST(EndPoints.POST_LOCATION)
    Call<AddLocationResponse> AddLocation(@Body AddLocationRequest Location);


    // Customer Service ends
}
