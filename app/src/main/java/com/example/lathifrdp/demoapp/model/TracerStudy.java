package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class TracerStudy {
    @SerializedName("_id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("formLink")
    private String formLink;

    @SerializedName("isActive")
    private boolean isActive;

    @SerializedName("responseLink")
    private String responseLink;

    @SerializedName("createdBy")
    private User user;

    @SerializedName("created")
    private String created;

    public TracerStudy(String id, String title, String description, String formLink, boolean isActive,String responseLink, String created){
        this.id = id;
        this.title = title;
        this.description = description;
        this.formLink = formLink;
        this.isActive = isActive;
        this.responseLink = responseLink;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreated() {
        return created;
    }

    public String getFormLink() {
        return formLink;
    }

    public User getUser() {
        return user;
    }

    public String getResponseLink() {
        return responseLink;
    }

    public boolean isActive() {
        return isActive;
    }
}
