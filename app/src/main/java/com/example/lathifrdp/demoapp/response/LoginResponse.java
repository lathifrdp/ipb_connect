package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.User;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse {
//    @SerializedName("item")
//    private List<Login> item;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("token")
    private String token;

    @SerializedName("message")
    private String message;

//    @SerializedName("isSuccess")
//    private boolean isSuccess;

//    @SerializedName("email")
//    private String email;
//
//    @SerializedName("password")
//    private String password;

    @SerializedName("item")
    private User user;

//    @SerializedName("item")
//    private String item;

    //@SerializedName("item")
    //public ArrayList<Item> item;

    public LoginResponse(boolean isSuccess, String token, String message, User user) {
        this.isSuccess = isSuccess;
        this.token = token;
        this.message = message;
        this.user = user;
    }

//    public List<Login> getItem() {
//        return item;
//    }
//
//    public void setItem(List<Login> item) {
//        this.item = item;
//    }
//
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public boolean isSuccess() {
//        return isSuccess;
//    }
//
//    public void setSuccess(boolean success) {
//        isSuccess = success;
//    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public String getItem() {
//        return item;
//    }
//
//    public void setItem(String item) {
//        this.item = item;
//    }

//        public ArrayList<Item> getItem() {
//        return item;
//    }
//
//    public void setItem(ArrayList<Login> item) {
//        this.item = item;
//    }
//
//    public class Item {
//        @SerializedName("_id")
//        @Expose
//        String _id;
//
//        public Item(String _id){
//            this._id = _id;
//        }
//
//        public String getId() {
//            return _id;
//        }
//        public void setId(String _id) {
//            this._id = _id;
//        }
//    }
//private Item[] item;
//
//    public Item[] getItem() {
//        return item;
//    }
}
