package com.example.naveed.ocf.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.naveed.ocf.Helper.GeneralHelper;
import com.example.naveed.ocf.Models.Token;
import com.example.naveed.ocf.Network.ApiClient;
import com.example.naveed.ocf.Network.IApiCaller;
import com.example.naveed.ocf.R;
import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Utility.ValidationUtility;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends BaseActivity implements View.OnClickListener{

    // Declarations
    Button btnSignIn, btnSignUp;
    EditText txtEmail, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_login);

        // Implementations
        btnSignIn = (Button) findViewById(R.id.btn_signin);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        // Setting OnClickListners
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.btn_signup:
                OpenActivity(SignUpActivity.class);
                break;

            case R.id.btn_signin:
                if(isValidate()){
                    SignIn();
                }else{
                    break;
                }
                break;
        }
    }

    private boolean isValidate(){
        if(!ValidationUtility.EditTextValidator(txtEmail,txtPassword)){
            GeneralHelper.ShowToast(this, "Email or password can not be empty!");
            return false;
        }else{
            return true;
        }
    }

    private void SignIn(){
        try {

            IApiCaller token = ApiClient.createService(IApiCaller.class);
            String username = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            Call<Token> response = token.GetToken(username,password,"password");


            response.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    Token objToken = response.body();
                    if(objToken == null){
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String err = jObjError.getString("error_description").toString();
                            Log.d("Error", err);
                            Toast.makeText(Login.this, err, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        String access_token = objToken.getAccessToken();
                        boolean isTokenSet = tokenHelper.SetToken(objToken.getAccessToken());
                        if(isTokenSet == true){
                            // TODO: Open main screen if token is set successfully
                            OpenActivity(OrderActivity.class);
                        }
                    }
                }
                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(Login.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
        }
    }
}
