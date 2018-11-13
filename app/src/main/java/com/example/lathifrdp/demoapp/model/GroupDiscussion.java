package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupDiscussion {

    @SerializedName("_id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("totalLike")
    private String totalLike;

    @SerializedName("totalComment")
    private String totalComment;

    @SerializedName("likers")
    private List<Liker> liker;

    @SerializedName("comments")
    private List<Comment> comment;

//    @SerializedName("bookmarks")
//    private Bookmark bookmark;

    @SerializedName("createdBy")
    private User createdBy;

    @SerializedName("created")
    private String created;

    public GroupDiscussion(String id, String title, String description, String totalLike, String totalComment, String created){
        this.id = id;
        this.title = title;
        this.description = description;
        this.totalLike = totalLike;
        this.totalComment = totalComment;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(String totalComment) {
        this.totalComment = totalComment;
    }

    public String getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(String totalLike) {
        this.totalLike = totalLike;
    }

//    public Bookmark getBookmark() {
//        return bookmark;
//    }
//
//    public void setBookmark(Bookmark bookmark) {
//        this.bookmark = bookmark;
//    }


    public List<Liker> getLiker() {
        return liker;
    }

    public void setLiker(List<Liker> liker) {
        this.liker = liker;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
