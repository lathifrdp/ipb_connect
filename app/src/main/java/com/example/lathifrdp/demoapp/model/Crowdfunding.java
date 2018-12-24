package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Crowdfunding {
    @SerializedName("_id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("contactPerson")
    private String contactPerson;

    @SerializedName("file")
    private String file;

    @SerializedName("location")
    private String location;

    @SerializedName("projectType")
    private String projectType;

    @SerializedName("totalCost")
    private String totalCost;

    @SerializedName("currentCost")
    private String currentCost;

    @SerializedName("donations")
    private List<Donation> donations;

    @SerializedName("isVerified")
    private boolean isVerified;

    @SerializedName("createdBy")
    private User user;

    @SerializedName("created")
    private String created;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    public Crowdfunding(String id, String title, String description, String contactPerson, String file, String location, String projectType, String totalCost, String currentCost, String created) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.contactPerson = contactPerson;
        this.file = file;
        this.location = location;
        this.projectType = projectType;
        this.totalCost = totalCost;
        this.currentCost = currentCost;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(String currentCost) {
        this.currentCost = currentCost;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean isVerified() {
        return isVerified;
    }
}
