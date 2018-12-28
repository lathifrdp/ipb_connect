package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.model.Donation;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonationResponse {
    @SerializedName("items")
    private List<Donation> donations;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    public DonationResponse(List<Donation> donations, boolean isSuccess){
        this.donations = donations;
        this.isSuccess = isSuccess;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
