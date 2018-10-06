package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @SerializedName("results")
    private List<User> user;

    @SerializedName("page")
    private Integer page;

    @SerializedName("limit")
    private Integer limit;

    @SerializedName("total")
    private Integer total;

    public UserResponse(List<User> user, Integer page, Integer limit, Integer total){
        this.user = user;
        this.page = page;
        this.limit = limit;
        this.total = total;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
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
