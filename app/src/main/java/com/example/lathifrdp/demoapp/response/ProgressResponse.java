package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.model.Progress;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProgressResponse {
    @SerializedName("results")
    private List<Progress> progresses;

    @SerializedName("page")
    private Integer page;

    @SerializedName("limit")
    private Integer limit;

    @SerializedName("total")
    private Integer total;

    public ProgressResponse(Integer page, Integer total, Integer limit){
        this.limit = limit;
        this.page = page;
        this.total = total;
    }

    public List<Progress> getProgresses() {
        return progresses;
    }

    public void setProgresses(List<Progress> progresses) {
        this.progresses = progresses;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
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
