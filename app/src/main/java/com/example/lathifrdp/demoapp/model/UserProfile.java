package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("photo")
    private String photo;

    @SerializedName("address")
    private String address;

    @SerializedName("mobileNumber")
    private String mobileNumber;

    @SerializedName("currentJob")
    private String currentJob;

    public UserProfile(String photo, String address, String mobileNumber, String currentJob) {
        this.photo = photo;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.currentJob = currentJob;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }
}
