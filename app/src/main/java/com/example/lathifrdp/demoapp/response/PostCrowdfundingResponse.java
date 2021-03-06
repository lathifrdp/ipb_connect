package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.google.gson.annotations.SerializedName;

public class PostCrowdfundingResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("item")
    private Crowdfunding crowdfunding;

    public PostCrowdfundingResponse(boolean isSuccess, String message){
        this.isSuccess = isSuccess;
        this.message = message;
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

    public Crowdfunding getCrowdfunding() {
        return crowdfunding;
    }

    public void setCrowdfunding(Crowdfunding crowdfunding) {
        this.crowdfunding = crowdfunding;
    }
}
