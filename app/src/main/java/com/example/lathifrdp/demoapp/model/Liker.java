package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class Liker {
    @SerializedName("createdBy")
    private String createdBy;

    @SerializedName("created")
    private String created;

    @SerializedName("_id")
    private String id;

    public Liker(String createdBy, String created, String id){
        this.createdBy = createdBy;
        this.created = created;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
