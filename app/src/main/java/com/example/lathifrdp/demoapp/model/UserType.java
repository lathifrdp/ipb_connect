package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class UserType {
    @SerializedName("alumni")
    private Integer alumni;

    @SerializedName("mahasiswa")
    private Integer mahasiswa;

    @SerializedName("userType")
    private String userType;

    public UserType(Integer alumni, Integer mahasiswa, String userType){
        this.alumni = alumni;
        this.mahasiswa = mahasiswa;
        this.userType = userType;
    }

    public Integer getAlumni() {
        return alumni;
    }

    public void setAlumni(Integer alumni) {
        this.alumni = alumni;
    }

    public Integer getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Integer mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
