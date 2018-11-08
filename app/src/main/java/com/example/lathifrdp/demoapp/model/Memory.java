package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Memory {
    @SerializedName("_id")
    private String id;

    @SerializedName("caption")
    private String caption;

    @SerializedName("photo")
    private String photo;

    @SerializedName("totalLike")
    private String totalLike;

    @SerializedName("likers")
    private List<Liker> liker;

    @SerializedName("comments")
    private List<Comment> comment;

//    @SerializedName("createdBy")
//    private User user;

    @SerializedName("created")
    private String created;

    public Memory(String id, String caption, String photo, String totalLike, String created, List<Comment> comment){
        this.id = id;
        this.caption = caption;
        this.photo = photo;
        this.totalLike = totalLike;
        this.created = created;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(String totalLike) {
        this.totalLike = totalLike;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public List<Liker> getLiker() {
        return liker;
    }

    public void setLiker(List<Liker> liker) {
        this.liker = liker;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
