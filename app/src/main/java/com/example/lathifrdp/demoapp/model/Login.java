package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Login {
//    @SerializedName("email")
//    private String email;
//    @SerializedName("password")
//    private String password;
    @SerializedName("_id")
    String _id;

//    @SerializedName("item")
//    private List<String> item = new ArrayList<String>();
//    @SerializedName("fullname")
//    private String fullname;
//    @SerializedName("gender")
//    private String gender;
//    @SerializedName("nim")
//    private String nim;
    /*@SerializedName("item")
    private List<String> item = new ArrayList<String>();*/


//    public Login(String _id, List<String> Item){
//        this._id = _id;
//        this.item = item;
//    }

//    public String getEmail() {
//        return email;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getId() {
        return _id;
    }
    public void setId(String _id) {
        this._id = _id;
    }

//    public List<String> getItem() {
//        return item;
//    }
//
//    public void setItem(List<String> item) {
//        this.item = item;
//    }

//    public String getFullname() {
//        return fullname;
//    }
//    public void setFullname(String fullname) {
//        this.fullname = fullname;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getNim() {
//        return nim;
//    }
//    public void setNim(String nim) {
//        this.nim = nim;
//    }

    /*public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }*/
}
