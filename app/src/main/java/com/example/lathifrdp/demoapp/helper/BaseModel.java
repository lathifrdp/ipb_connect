package com.example.lathifrdp.demoapp.helper;

public class BaseModel {
    String url;
    String eventUrl;
    String profileUrl;
    String memoryUrl;
    String knowledgeUrl;

    public BaseModel(){
        //this.url = "http://api.ipbconnect.cs.ipb.ac.id/";
        //this.url = "http://172.20.32.178:3501/";
        //this.url = "http://192.168.43.31:3501/";
        this.url = "http://192.168.1.10:3501/";
        //this.url = "http://172.30.48.135:3501/";
        //this.url = "http://192.168.43.88:3501/";
        this.eventUrl = this.url + "uploads/event/";
        this.profileUrl = this.url + "uploads/profile/";
        this.memoryUrl = this.url + "uploads/memory/";
        this.knowledgeUrl = this.url + "uploads/knowledgesharing/";
    }

    public String getUrl() {
        return url;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getMemoryUrl() {
        return memoryUrl;
    }

    public String getKnowledgeUrl() {
        return knowledgeUrl;
    }
}
