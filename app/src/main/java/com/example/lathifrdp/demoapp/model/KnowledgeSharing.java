package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KnowledgeSharing {
    @SerializedName("_id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

//    @SerializedName("category")
//    private KnowledgeSharingCategory category;

    @SerializedName("cover")
    private String cover;

    @SerializedName("file")
    private String file;

    @SerializedName("fileSize")
    private String fileSize;

    @SerializedName("totalSlide")
    private String totalSlide;

    @SerializedName("totalLike")
    private String totalLike;

    @SerializedName("totalComment")
    private String totalComment;

//    @SerializedName("likers")
//    private Liker liker;
//
    @SerializedName("comments")
    private List<Comment> comment;
//
//    @SerializedName("bookmarks")
//    private Bookmark bookmark;

    @SerializedName("createdBy")
    private User user;

    @SerializedName("created")
    private String created;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    public KnowledgeSharing(String id, String title, String description, String cover, String file, String fileSize, String totalSlide, String totalLike, String totalComment, String created, boolean isSuccess, String message) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cover = cover;
        this.file = file;
        this.fileSize = fileSize;
        this.totalSlide = totalSlide;
        this.totalLike = totalLike;
        this.totalComment = totalComment;
        this.created = created;
        this.isSuccess = isSuccess;
        this.message = message;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

//    public KnowledgeSharingCategory getCategory() {
//        return category;
//    }
//
//    public void setCategory(KnowledgeSharingCategory category) {
//        this.category = category;
//    }
//
//    public Liker getLiker() {
//        return liker;
//    }
//
//    public void setLiker(Liker liker) {
//        this.liker = liker;
//    }
//

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
    //
//    public Bookmark getBookmark() {
//        return bookmark;
//    }
//
//    public void setBookmark(Bookmark bookmark) {
//        this.bookmark = bookmark;
//    }

    public String getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(String totalLike) {
        this.totalLike = totalLike;
    }

    public String getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(String totalComment) {
        this.totalComment = totalComment;
    }

    public String getTotalSlide() {
        return totalSlide;
    }

    public void setTotalSlide(String totalSlide) {
        this.totalSlide = totalSlide;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
