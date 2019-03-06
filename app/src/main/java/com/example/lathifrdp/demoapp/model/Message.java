package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message {
    @SerializedName("_id")
    private String id;

    @SerializedName("message")
    private String message;

    @SerializedName("photos")
    private List<String> photos;

    @SerializedName("sender")
    private User sender;

    @SerializedName("created")
    private String created;

    public Message(String id, String message, String created){
        this.id = id;
        this.message = message;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public User getSender() {
        return sender;
    }
}
