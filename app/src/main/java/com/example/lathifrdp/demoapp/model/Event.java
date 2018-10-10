package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

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
}
