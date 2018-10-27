package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class GroupDiscussion {

    @SerializedName("_id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("totalLike")
    private int totalLike;

    @SerializedName("totalComment")
    private int totalComment;

    @SerializedName("likers")
    private Liker liker;

    @SerializedName("comments")
    private Comment comment;

    @SerializedName("bookmarks")
    private Bookmark bookmark;

    @SerializedName("createdBy")
    private String createdBy;

    @SerializedName("created")
    private String created;

    public GroupDiscussion(String id, String title, String description, int totalLike, int totalComment, String createdBy, String created){
        this.id = id;
        this.title = title;
        this.description = description;
        this.totalLike = totalLike;
        this.totalComment = totalComment;
        this.createdBy = createdBy;
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

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    public void setBookmark(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Liker getLiker() {
        return liker;
    }

    public void setLiker(Liker liker) {
        this.liker = liker;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
