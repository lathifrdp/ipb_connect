package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class StudyProgram {
    @SerializedName("_id")
    private String _id;

    @SerializedName("name")
    private String name;

    public StudyProgram(String _id, String name){
        this._id = _id;
        this.name = name;
    }

    public String getFacultyId() {
        return _id;
    }

    public void setFacultyId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
