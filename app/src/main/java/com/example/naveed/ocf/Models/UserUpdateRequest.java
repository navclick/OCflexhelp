package com.example.naveed.ocf.Models;

import com.google.gson.annotations.SerializedName;

public class UserUpdateRequest {



        @SerializedName("FullName")
        public String FullName;

        @SerializedName("AddressOne")
        public String AddressOne;

        @SerializedName("PhoneNumber")
        public String PhoneNumber;

        @SerializedName("Image")
        public String Image;



}
