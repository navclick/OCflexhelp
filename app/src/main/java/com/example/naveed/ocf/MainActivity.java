package com.example.naveed.ocf;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.naveed.ocf.Helper.TokenHelper;
import com.example.naveed.ocf.Models.Token;
import com.example.naveed.ocf.Network.ApiClient;
import com.example.naveed.ocf.Network.IApiCaller;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String OCFLEXTOKEN = "OCFlexToken" ;
    public TokenHelper tokenHelper;

    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_login);

        tokenHelper = new TokenHelper(this);

        // Declaration
        btnSignIn = findViewById(R.id.btn_signin);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IApiCaller token = ApiClient.createService(IApiCaller.class);
                Call<Token> response = token.GetToken("admin","Admin123","password");

                response.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        Token objToken = response.body();
                        String access_token = objToken.getAccessToken();
                        boolean isTokenSet = tokenHelper.SetToken(objToken.getAccessToken());

                        if(isTokenSet == true){
                            // TODO: Open main screen if token is set successfully
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Log.d("ApiError",t.getMessage());
                    }
                });

//                final ICustomerService service = ApiClient.createService(ICustomerService.class);
//                Call<CustomerService> response = service.GetCustomerServices();
//
//                response.enqueue(new Callback<CustomerService>() {
//
//                    @Override
//                    public void onResponse(Call<CustomerService> call, Response<CustomerService> response) {
//                        CustomerService serviceList = response.body();
//                        Log.d("success",String.valueOf(serviceList.getCode()));
//                    }
//
//                    @Override
//                    public void onFailure(Call<CustomerService> call, Throwable t) {
//                        Log.d("ApiError",t.getMessage());
//                    }
//                });

            }
        });
    }
}
