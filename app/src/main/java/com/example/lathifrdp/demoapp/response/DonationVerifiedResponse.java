package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Donation;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonationVerifiedResponse {
    @SerializedName("items")
    private List<Donation> donations;

    @SerializedName("isSuccess")
    private boolean isSuccess;



    public DonationVerifiedResponse(List<Donation> donations, boolean isSuccess){
        this.donations = donations;
        this.isSuccess = isSuccess;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
