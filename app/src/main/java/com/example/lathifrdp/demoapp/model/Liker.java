package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class Liker {
    @SerializedName("createdBy")
    private User createdBy;

    @SerializedName("created")
    private String created;

    @SerializedName("_id")
    private String id;

    public Liker(String created, String id){
        this.created = created;
        this.id = id;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
