package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class Donation {
    @SerializedName("createdBy")
    private User createdBy;

    @SerializedName("created")
    private String created;

    @SerializedName("_id")
    private String id;

    @SerializedName("isVerified")
    private boolean isVerified;

    @SerializedName("file")
    private String file;

    public Donation(String created, String id, boolean isVerified, String file){
        this.created = created;
        this.id = id;
        this.isVerified = isVerified;
        this.file = file;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
