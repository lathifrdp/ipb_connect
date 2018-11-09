package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Comment {
    @SerializedName("createdBy")
    private User user;

    @SerializedName("_id")
    private String id;

    @SerializedName("value")
    private String value;

    @SerializedName("likers")
    private List<Liker> liker;

    @SerializedName("created")
    private String created;

    public Comment(String id,String value, String created){
        this.value = value;
        this.created = created;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Liker> getLiker() {
        return liker;
    }

    public void setLiker(List<Liker> liker) {
        this.liker = liker;
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
