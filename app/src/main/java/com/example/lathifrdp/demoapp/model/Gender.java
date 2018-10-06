package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class Gender {
    @SerializedName("l")
    private Integer laki;

    @SerializedName("p")
    private Integer perempuan;

    public Gender(Integer laki, Integer perempuan){
        this.laki = laki;
        this.perempuan = perempuan;
    }

    public Integer getLaki() {
        return laki;
    }

    public void setLaki(Integer laki) {
        this.laki = laki;
    }

    public Integer getPerempuan() {
        return perempuan;
    }

    public void setPerempuan(Integer perempuan) {
        this.perempuan = perempuan;
    }
}
