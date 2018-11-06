package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class JobLocation {
    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

//    @SerializedName("createdBy")
//    private User createdBy;

    @SerializedName("created")
    private String created;

//    @SerializedName("modifiedBy")
//    private User modifiedBy;

    @SerializedName("modified")
    private String modified;

    public JobLocation(String id,String name){
        this.id = id;
        this.name = name;
//        this.createdBy = createdBy;
//        this.created = created;
//        this.modifiedBy = modifiedBy;
//        this.modified = modified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public User getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(User createdBy) {
//        this.createdBy = createdBy;
//    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

//    public User getModifiedBy() {
//        return modifiedBy;
//    }
//
//    public void setModifiedBy(User modifiedBy) {
//        this.modifiedBy = modifiedBy;
//    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
