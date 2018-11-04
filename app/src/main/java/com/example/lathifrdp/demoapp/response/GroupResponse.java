package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.GroupDiscussion;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupResponse {
    @SerializedName("items")
    private List<GroupDiscussion> groupDiscussions;

    @SerializedName("page")
    private String page;

    @SerializedName("total")
    private String total;

    public GroupResponse(List<GroupDiscussion> groupDiscussions, String page, String total){
        this.groupDiscussions = groupDiscussions;
        this.page = page;
        this.total = total;
    }

    public List<GroupDiscussion> getGroupDiscussions() {
        return groupDiscussions;
    }

    public void setGroupDiscussions(List<GroupDiscussion> groupDiscussions) {
        this.groupDiscussions = groupDiscussions;
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
}
