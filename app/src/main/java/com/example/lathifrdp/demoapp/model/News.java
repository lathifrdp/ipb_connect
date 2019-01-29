package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class News {
    @SerializedName("Id")
    private String id;

    @SerializedName("Tanggal")
    private String tanggal;

    @SerializedName("Judul")
    private String judul;

    @SerializedName("Ringkasan")
    private String ringkasan;

    @SerializedName("Image")
    private String image;

    @SerializedName("HitCount")
    private String hitcount;

    @SerializedName("Isi")
    private String isi;

    @SerializedName("Narasumber")
    private String narasumber;

    @SerializedName("Lokasi")
    private String lokasi;

    @SerializedName("Keyword")
    private String keyword;

    public News(String id, String tanggal, String judul, String ringkasan, String image, String hitcount, String isi, String narasumber, String lokasi, String keyword){
        this.id = id;
        this.tanggal = tanggal;
        this.judul = judul;
        this.ringkasan = ringkasan;
        this.image = image;
        this.hitcount = hitcount;
        this.isi = isi;
        this.narasumber = narasumber;
        this.lokasi = lokasi;
        this.keyword = keyword;
    }

    public String getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getRingkasan() {
        return ringkasan;
    }

    public String getHitcount() {
        return hitcount;
    }

    public String getImage() {
        return image;
    }

    public String getIsi() {
        return isi;
    }

    public String getNarasumber() {
        return narasumber;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getLokasi() {
        return lokasi;
    }
}
