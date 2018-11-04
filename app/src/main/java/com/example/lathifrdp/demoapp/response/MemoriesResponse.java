package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Memory;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemoriesResponse {
    @SerializedName("results")
    private List<Memory> memories;

    @SerializedName("page")
    private Integer page;

    @SerializedName("limit")
    private Integer limit;

    @SerializedName("total")
    private Integer total;

    public MemoriesResponse(List<Memory> memories, Integer page, Integer limit, Integer total){
        this.memories = memories;
        this.page = page;
        this.limit = limit;
        this.total = total;
    }

    public List<Memory> getMemories() {
        return memories;
    }

    public void setMemories(List<Memory> memories) {
        this.memories = memories;
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
