package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class KnowledgeSharing {
    @SerializedName("_id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("category")
    private KnowledgeSharingCategory category;

    @SerializedName("cover")
    private String cover;

    @SerializedName("file")
    private String file;

    @SerializedName("fileSize")
    private String fileSize;

    @SerializedName("totalSlide")
    private int totalSlide;

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

    public KnowledgeSharing(String id, String title, String description, String cover, String file, String fileSize, int totalSlide, int totalLike, int totalComment, String created, String createdBy) {
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
        this.createdBy = createdBy;
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

    public KnowledgeSharingCategory getCategory() {
        return category;
    }

    public void setCategory(KnowledgeSharingCategory category) {
        this.category = category;
    }

    public Liker getLiker() {
        return liker;
    }

    public void setLiker(Liker liker) {
        this.liker = liker;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    public void setBookmark(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public int getTotalSlide() {
        return totalSlide;
    }

    public void setTotalSlide(int totalSlide) {
        this.totalSlide = totalSlide;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
