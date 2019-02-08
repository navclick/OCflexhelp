package com.example.naveed.ocf.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingRequest {

    @SerializedName("OrderId")
    @Expose
    private Integer orderId;
    @SerializedName("Rating")
    @Expose
    private Integer rating;
    @SerializedName("RatingMessage")
    @Expose
    private String ratingMessage;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getRatingMessage() {
        return ratingMessage;
    }

    public void setRatingMessage(String ratingMessage) {
        this.ratingMessage = ratingMessage;
    }
}
