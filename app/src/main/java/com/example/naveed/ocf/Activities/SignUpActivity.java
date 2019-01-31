package com.example.naveed.ocf.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Helper.GeneralHelper;
import com.example.naveed.ocf.Models.GeneralResponse;
import com.example.naveed.ocf.Models.SignUpRequest;
import com.example.naveed.ocf.Network.ApiClient;
import com.example.naveed.ocf.Network.IApiCaller;
import com.example.naveed.ocf.R;
import com.example.naveed.ocf.Utility.ValidationUtility;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity implements View.OnClickListener{

    // Declarations
    Button btnRegister, btnCancel;
    EditText txtEmail, txtFullName, txtPassword, txtConfirmPassword, txtPhoneNumber, txtAddressOne, txtAddressTwo, txtAccountTitle, txtAccountName, txtBankName, txtSwift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Implementations
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        txtEmail = (EditText) findViewById(R.id.txt_email);
        txtFullName = (EditText) findViewById(R.id.txt_fullname);
        txtPassword = (EditText) findViewById(R.id.txt_password);
        txtConfirmPassword = (EditText) findViewById(R.id.txt_confirmpassword);
        txtPhoneNumber = (EditText) findViewById(R.id.txt_phonenumber);
        txtAddressOne = (EditText) findViewById(R.id.txt_addressone);
        txtAddressTwo = (EditText) findViewById(R.id.txt_addresstwo);
        txtAccountTitle = (EditText) findViewById(R.id.txt_accounttitle);
        txtAccountName = (EditText) findViewById(R.id.txt_accountname);
        txtBankName = (EditText) findViewById(R.id.txt_bankname);
        txtSwift = (EditText) findViewById(R.id.txt_swiftcode);

        // Setting onClickListner
        btnRegister.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_cancel:
                OpenActivity(Login.class);
                break;

            case R.id.btn_register:
                if(isValidate()){
                    Register();
                }
                break;
        }
    }

    private boolean isValidate(){
        if(!ValidationUtility.EditTextValidator(txtEmail,txtPassword,txtConfirmPassword)){
            GeneralHelper.ShowToast(this, "Email or password can not be empty!");
            return false;
        }else{
            return true;
        }
    }

    private void Register(){
        try {
            IApiCaller client = ApiClient.createService(IApiCaller.class);
            SignUpRequest request = new SignUpRequest();

            request.Email = txtEmail.getText().toString();
            request.UserName = txtEmail.getText().toString();
            request.Password = txtPassword.getText().toString();
            request.ConfirmPassword = txtConfirmPassword.getText().toString();
            request.FullName = txtFullName.getText().toString();
            request.Level = 4;
            request.PhoneNumber = txtPhoneNumber.getText().toString();
            request.AddressOne = txtAddressOne.getText().toString();
            request.AddressTwo = txtAddressTwo.getText().toString();
            request.AccountTitle = txtAccountTitle.getText().toString();
            request.AccountName = txtAccountName.getText().toString();
            request.BankName = txtBankName.getText().toString();
            request.Swift = txtSwift.getText().toString();

            Call<GeneralResponse> response = client.Register(request);



            response.enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {

                    GeneralResponse objBody = response.body();
                    if(objBody == null){
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Log.d("error",jObjError.getString("error_description"));
                            Toast.makeText(SignUpActivity.this, jObjError.getString("error_description"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(SignUpActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        boolean iserror = objBody.getIserror();
                        if(!iserror){
                            OpenActivity(Login.class);
                        }else{
                            String errormessage = objBody.getMessage();
                            Toast.makeText(SignUpActivity.this, errormessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Log.d("exception",e.getMessage());
        }
    }
}
