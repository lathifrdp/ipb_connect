package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.KnowledgeSharing;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookmarkResponse {
    @SerializedName("items")
    private List<KnowledgeSharing> knowledgeSharings;

    @SerializedName("page")
    private String page;

    @SerializedName("total")
    private String total;

    @SerializedName("message")
    private String message;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    public BookmarkResponse(List<KnowledgeSharing> knowledgeSharings, String page, String total){
        this.knowledgeSharings = knowledgeSharings;
        this.page = page;
        this.total = total;
    }

    public List<KnowledgeSharing> getKnowledgeSharings() {
        return knowledgeSharings;
    }

    public void setKnowledgeSharings(List<KnowledgeSharing> knowledgeSharings) {
        this.knowledgeSharings = knowledgeSharings;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
