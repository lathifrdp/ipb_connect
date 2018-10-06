package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.StudyProgram;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    public RegisterResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;

    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
