package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event {
    @SerializedName("_id")
    private String id;

    @SerializedName("contact")
    private String contact;

    @SerializedName("created")
    private String created;

    @SerializedName("description")
    private String description;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("place")
    private String place;

    @SerializedName("price")
    private String price;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("title")
    private String title;

    @SerializedName("picture")
    private String picture;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("createdBy")
    private User user;

    public Event(String id, String contact, String created, String description, String endDate, String endTime, String place, String price, String startDate, String startTime, String title, String picture, String latitude, String longitude){
        this.id = id;
        this.contact = contact;
        this.created = created;
        this.description = description;
        this.endDate = endDate;
        this.endTime = endTime;
        this.place = place;
        this.price = price;
        this.startDate = startDate;
        this.startTime = startTime;
        this.title = title;
        this.picture = picture;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
