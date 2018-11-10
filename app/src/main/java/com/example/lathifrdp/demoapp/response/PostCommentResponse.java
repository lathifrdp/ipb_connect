package com.example.lathifrdp.demoapp.response;

import com.google.gson.annotations.SerializedName;

public class PostCommentResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    public PostCommentResponse(boolean isSuccess, String message){
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
}
