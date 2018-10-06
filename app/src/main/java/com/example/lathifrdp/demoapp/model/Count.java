package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class Count {
    @SerializedName("gender")
    private Gender gender;

    @SerializedName("userType")
    private UserType userType;

    public Count(Gender gender, UserType userType){
        this.gender = gender;
        this.userType = userType;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
