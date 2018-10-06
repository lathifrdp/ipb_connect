package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Count;
import com.google.gson.annotations.SerializedName;

public class CountResponse {
    @SerializedName("results")
    private Count count;

    @SerializedName("total")
    private Integer total;

    public CountResponse(Count count, Integer total){
        this.count = count;
        this.total = total;
    }

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
