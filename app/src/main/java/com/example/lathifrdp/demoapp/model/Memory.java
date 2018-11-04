package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class Memory {
    @SerializedName("_id")
    private String id;

    @SerializedName("caption")
    private String caption;

    @SerializedName("photo")
    private String photo;

    @SerializedName("totalLike")
    private String totalLike;

//    @SerializedName("likers")
//    private Liker liker;

//    @SerializedName("comments")
//    private Comment comment;

//    @SerializedName("createdBy")
//    private User createdBy;

    @SerializedName("created")
    private String created;

    public Memory(String id, String caption, String photo, String totalLike, User createdBy, String created){
        this.id = id;
        this.caption = caption;
        this.photo = photo;
        this.totalLike = totalLike;
        //this.createdBy = createdBy;
        this.created = created;
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

//    public Liker getLiker() {
//        return liker;
//    }
//
//    public void setLiker(Liker liker) {
//        this.liker = liker;
//    }
//
//    public Comment getComment() {
//        return comment;
//    }
//
//    public void setComment(Comment comment) {
//        this.comment = comment;
//    }

//    public User getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(User createdBy) {
//        this.createdBy = createdBy;
//    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
