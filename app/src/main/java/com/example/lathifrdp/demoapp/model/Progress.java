package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class Progress {
    @SerializedName("_id")
    private String id;

    @SerializedName("file")
    private String file;

    @SerializedName("description")
    private String description;

    @SerializedName("createdBy")
    private User user;

    @SerializedName("created")
    private String created;

    public Progress(String id, String file, String description, String created){
        this.id = id;
        this.file = file;
        this.description = description;
        this.created = created;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
