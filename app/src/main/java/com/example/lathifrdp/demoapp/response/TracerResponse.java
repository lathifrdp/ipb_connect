package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.KnowledgeSharing;
import com.example.lathifrdp.demoapp.model.TracerStudy;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TracerResponse {
    @SerializedName("items")
    private List<TracerStudy> tracerStudies;

    @SerializedName("page")
    private String page;

    @SerializedName("total")
    private Integer total;

    @SerializedName("message")
    private String message;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    public TracerResponse(List<TracerStudy> tracerStudies, String page, Integer total){
        this.tracerStudies = tracerStudies;
        this.page = page;
        this.total = total;
    }

    public List<TracerStudy> getTracerStudies() {
        return tracerStudies;
    }

    public void setTracerStudies(List<TracerStudy> tracerStudies) {
        this.tracerStudies = tracerStudies;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
