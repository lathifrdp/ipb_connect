package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Job;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobResponse {
    @SerializedName("results")
    private List<Job> job;

    @SerializedName("page")
    private Integer page;

    @SerializedName("limit")
    private Integer limit;

    @SerializedName("total")
    private Integer total;

    public JobResponse(List<Job> job, Integer page, Integer limit, Integer total){
        this.job = job;
        this.page = page;
        this.limit = limit;
        this.total = total;
    }

    public List<Job> getJob() {
        return job;
    }

    public void setJob(List<Job> job) {
        this.job = job;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
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
}
