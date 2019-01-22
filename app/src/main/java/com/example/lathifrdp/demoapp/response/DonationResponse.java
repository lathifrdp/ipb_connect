package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.model.Donation;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonationResponse {
//    @SerializedName("items")
//    private List<Donation> donations;
//
//    @SerializedName("isSuccess")
//    private boolean isSuccess;

    @SerializedName("results")
    private List<Donation> donations;

    @SerializedName("page")
    private Integer page;

    @SerializedName("limit")
    private Integer limit;

    @SerializedName("total")
    private Integer total;

//    public DonationResponse(List<Donation> donations, boolean isSuccess){
//        this.donations = donations;
//        this.isSuccess = isSuccess;
//    }

    public DonationResponse(Integer page, Integer total, Integer limit){
        this.limit = limit;
        this.page = page;
        this.total = total;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
