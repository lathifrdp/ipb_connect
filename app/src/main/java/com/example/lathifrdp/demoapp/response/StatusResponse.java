package com.example.lathifrdp.demoapp.response;

import com.google.gson.annotations.SerializedName;

public class StatusResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("isCrowdfunding")
    private String isCrowdfunding;

    public StatusResponse(boolean isSuccess, String isCrowdfunding){
        this.isSuccess = isSuccess;
        this.isCrowdfunding = isCrowdfunding;
    }

    public String getIsCrowdfunding() {
        return isCrowdfunding;
    }

    public void setIsCrowdfunding(String isCrowdfunding) {
        this.isCrowdfunding = isCrowdfunding;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
