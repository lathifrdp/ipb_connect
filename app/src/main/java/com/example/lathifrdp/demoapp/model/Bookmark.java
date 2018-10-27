package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class Bookmark {
    @SerializedName("createdBy")
    private String createdBy;

    @SerializedName("created")
    private String created;

    public Bookmark(String createdBy, String created){
        this.createdBy = createdBy;
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
