package com.example.naveed.ocf.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusUpdateRequest {

    @SerializedName("orderid")
    @Expose
    private Integer orderid;
    @SerializedName("statusid")
    @Expose
    private Integer statusid;
    @SerializedName("Reason")
    @Expose
    private String reason;

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
