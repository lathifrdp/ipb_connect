package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("value")
    private String value;

    @SerializedName("likers")
    private Liker liker;

    @SerializedName("createdBy")
    private String createdBy;

    @SerializedName("created")
    private String created;

    public Comment(String value, String createdBy, String created){
        this.value = value;
        this.createdBy = createdBy;
        this.created = created;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Liker getLiker() {
        return liker;
    }

    public void setLiker(Liker liker) {
        this.liker = liker;
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
