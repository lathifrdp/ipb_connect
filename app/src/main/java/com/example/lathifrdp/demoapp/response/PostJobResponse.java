package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Job;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostJobResponse {
//    @SerializedName("item")
//    private List<Job> job;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    public PostJobResponse(boolean isSuccess, String message){
        this.isSuccess = isSuccess;
        this.message = message;
    }

//    public List<Job> getJob() {
//        return job;
//    }
//
//    public void setJob(List<Job> job) {
//        this.job = job;
//    }

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
