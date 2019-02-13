package com.example.naveed.ocf.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("iserror")
    @Expose
    private Boolean iserror;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("value")
    @Expose
    private ProfileValue value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getIserror() {
        return iserror;
    }

    public void setIserror(Boolean iserror) {
        this.iserror = iserror;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ProfileValue getValue() {
        return value;
    }

    public void setValue(ProfileValue value) {
        this.value = value;
    }


    public class ProfileValue {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("username")
        @Expose
        private Object username;
        @SerializedName("fullName")
        @Expose
        private String fullName;
        @SerializedName("password")
        @Expose
        private Object password;
        @SerializedName("confirmPassword")
        @Expose
        private Object confirmPassword;
        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("companyName")
        @Expose
        private String companyName;
        @SerializedName("addressOne")
        @Expose
        private String addressOne;
        @SerializedName("addressTwo")
        @Expose
        private Object addressTwo;
        @SerializedName("phoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("country")
        @Expose
        private Object country;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("postalCode")
        @Expose
        private String postalCode;
        @SerializedName("joinDate")
        @Expose
        private String joinDate;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("accountTitle")
        @Expose
        private String accountTitle;
        @SerializedName("accountNumber")
        @Expose
        private String accountNumber;
        @SerializedName("bankName")
        @Expose
        private String bankName;
        @SerializedName("swift")
        @Expose
        private String swift;
        @SerializedName("userService")
        @Expose
        private Object userService;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public Object getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(Object confirmPassword) {
            this.confirmPassword = confirmPassword;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getAddressOne() {
            return addressOne;
        }

        public void setAddressOne(String addressOne) {
            this.addressOne = addressOne;
        }

        public Object getAddressTwo() {
            return addressTwo;
        }

        public void setAddressTwo(Object addressTwo) {
            this.addressTwo = addressTwo;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getJoinDate() {
            return joinDate;
        }

        public void setJoinDate(String joinDate) {
            this.joinDate = joinDate;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAccountTitle() {
            return accountTitle;
        }

        public void setAccountTitle(String accountTitle) {
            this.accountTitle = accountTitle;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
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

        public Object getUserService() {
            return userService;
        }

        public void setUserService(Object userService) {
            this.userService = userService;
        }

    }
}
