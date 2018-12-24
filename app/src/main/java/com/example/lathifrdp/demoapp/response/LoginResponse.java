package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.User;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("token")
    private String token;

    @SerializedName("message")
    private String message;

    @SerializedName("item")
    private User user;

    public LoginResponse(boolean isSuccess, String token, String message, User user) {
        this.isSuccess = isSuccess;
        this.token = token;
        this.message = message;
        this.user = user;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
