package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class ProposalImage {
    @SerializedName("_id")
    private String id;

    @SerializedName("file")
    private String file;

    @SerializedName("created")
    private String created;

    public ProposalImage(String id, String file, String created){
        this.id = id;
        this.file = file;
        this.created = created;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
