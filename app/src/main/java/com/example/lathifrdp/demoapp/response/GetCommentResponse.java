package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Comment;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCommentResponse {
    @SerializedName("results")
    private List<Comment> comments;

    @SerializedName("page")
    private Integer page;

    @SerializedName("limit")
    private Integer limit;

    @SerializedName("total")
    private Integer total;

    public GetCommentResponse(List<Comment> comments, Integer page, Integer limit, Integer total){
        this.comments = comments;
        this.page = page;
        this.limit = limit;
        this.total = total;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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
