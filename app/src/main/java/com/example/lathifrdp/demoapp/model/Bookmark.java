package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class Bookmark {
    @SerializedName("createdBy")
    private User user;

    @SerializedName("created")
    private String created;

    @SerializedName("_id")
    private String id;

    public Bookmark(String created, String id){
        this.created = created;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
