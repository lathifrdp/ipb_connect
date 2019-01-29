package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.News;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {
    @SerializedName("TotalPages")
    private Integer totalpages;

    @SerializedName("Page")
    private Integer page;

    @SerializedName("Data")
    private List<News> news;

    public NewsResponse(Integer totalpages, Integer page){
        this.totalpages = totalpages;
        this.page = page;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getTotalpages() {
        return totalpages;
    }

    public List<News> getNews() {
        return news;
    }
}
