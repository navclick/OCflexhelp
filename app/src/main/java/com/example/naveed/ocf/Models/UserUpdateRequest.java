package com.example.naveed.ocf.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserUpdateRequest {




        @SerializedName("FullName")
        @Expose
        private String fullName;
        @SerializedName("AddressOne")
        @Expose
        private String addressOne;
        @SerializedName("PhoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("AccountNumber")
        @Expose
        private String accountNumber;
        @SerializedName("AccountTitle")
        @Expose
        private String accountTitle;
        @SerializedName("BankName")
        @Expose
        private String bankName;
        @SerializedName("Swift")
        @Expose
        private String swift;

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public String getAddressOne() {
                return addressOne;
        }

        public void setAddressOne(String addressOne) {
                this.addressOne = addressOne;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public String getImage() {
                return image;
        }

        public void setImage(String image) {
                this.image = image;
        }

        public String getAccountNumber() {
                return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
                this.accountNumber = accountNumber;
        }

        public String getAccountTitle() {
                return accountTitle;
        }

        public void setAccountTitle(String accountTitle) {
                this.accountTitle = accountTitle;
        }

        public String getBankName() {
                return bankName;
        }

        public void setBankName(String bankName) {
                this.bankName = bankName;
        }

        public String getSwift() {
                return swift;
        }

        public void setSwift(String swift) {
                this.swift = swift;
        }

}
