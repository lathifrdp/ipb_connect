package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class KnowledgeSharingCategory {

    @SerializedName("name")
    private String name;

    @SerializedName("createdBy")
    private String createdBy;

    @SerializedName("created")
    private String created;

    public KnowledgeSharingCategory(String name, String createdBy, String created){
        this.name = name;
        this.createdBy = createdBy;
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
