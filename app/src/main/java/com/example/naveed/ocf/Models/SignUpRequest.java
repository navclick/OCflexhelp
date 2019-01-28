package com.example.naveed.ocf.Models;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {
    @SerializedName("Email")
    public String Email;

    @SerializedName("UserName")
    public String UserName;

    @SerializedName("Password")
    public String Password;

    @SerializedName("ConfirmPassword")
    public String ConfirmPassword;

    @SerializedName("FullName")
    public String FullName;

    @SerializedName("Level")
    public int Level;

    @SerializedName("PhoneNumber")
    public String PhoneNumber;

    @SerializedName("AddressOne")
    public String AddressOne;

    @SerializedName("AddressTwo")
    public String AddressTwo;

    @SerializedName("AccountTitle")
    public String AccountTitle;

    @SerializedName("AccountName")
    public String AccountName;

    @SerializedName("BankName")
    public String BankName;

    @SerializedName("Swift")
    public String Swift;
}
