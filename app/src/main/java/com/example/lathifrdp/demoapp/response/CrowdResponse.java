package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.model.Event;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CrowdResponse {
    @SerializedName("items")
    private List<Crowdfunding> crowdfunding;

    @SerializedName("page")
    private Integer page;

//    @SerializedName("limit")
//    private Integer limit;

    @SerializedName("total")
    private Integer total;

    public CrowdResponse(List<Crowdfunding> crowdfunding, Integer page, Integer total){
        this.crowdfunding = crowdfunding;
        this.page = page;
        this.total = total;
    }

    public List<Crowdfunding> getCrowdfunding() {
        return crowdfunding;
    }

    public void setCrowdfunding(List<Crowdfunding> crowdfunding) {
        this.crowdfunding = crowdfunding;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
