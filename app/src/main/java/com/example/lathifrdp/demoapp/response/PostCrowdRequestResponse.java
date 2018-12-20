package com.example.lathifrdp.demoapp.response;

import com.google.gson.annotations.SerializedName;

public class PostCrowdRequestResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("isCrowdfunding")
    private String isCrowdfunding;

    public PostCrowdRequestResponse(boolean isSuccess, String message, String isCrowdfunding){
        this.isSuccess = isSuccess;
        this.message = message;
        this.isCrowdfunding = isCrowdfunding;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getIsCrowdfunding() {
        return isCrowdfunding;
    }

    public void setIsCrowdfunding(String isCrowdfunding) {
        this.isCrowdfunding = isCrowdfunding;
    }
}
