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

    @SerializedName("interest")
    private String interest;

    @SerializedName("hobby")
    private String hobby;

    @SerializedName("maritalStatus")
    private String maritalStatus;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    public UserProfile(String photo, String address, String mobileNumber, String currentJob, String interest, String hobby, String maritalStatus, String latitude, String longitude) {
        this.photo = photo;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.currentJob = currentJob;
        this.interest = interest;
        this.hobby = hobby;
        this.maritalStatus = maritalStatus;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
