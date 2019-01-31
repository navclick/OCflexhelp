package com.example.naveed.ocf.Network;

import android.util.Base64;
import android.util.Log;

import com.example.naveed.ocf.Helper.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static IApiCaller authRestClient;
    public static String tokenType="Bearer ";
    public static String tokene="";
    static {
        setupClient();
    }

    private static void setupClient() {

        // Todo remove log level while release

        setupRestClient();

    }



    private static   void setupRestClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request newRequest;
                        newRequest = request.newBuilder()
                                .addHeader("Accept","application/json")
                                .addHeader("Authorization ", RestClient.tokenType+RestClient.tokene)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(logging)
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(Constants.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                client(httpClient).
                build();

        authRestClient = retrofit.create(IApiCaller.class);
    }



    private static   void setupRestClientAuth(final String tokenType, final String token) {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request newRequest;
                        newRequest = request.newBuilder()
                                .addHeader("Accept","application/json")
                                .addHeader("Authorization ", tokenType+token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(logging)
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(Constants.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                client(httpClient).
                build();

        authRestClient = retrofit.create(IApiCaller.class);
    }





    public static IApiCaller getAuthAdapter(){
        return authRestClient;
    }

    public static IApiCaller getAuthAdapterToekn(String token){

      String t=  Base64.encodeToString(token.getBytes(), Base64.DEFAULT);

        RestClient.tokene=token;



        Log.d(Constants.TAG, tokenType+ RestClient.tokene);



        return authRestClient;
    }


}