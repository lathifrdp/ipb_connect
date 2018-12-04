package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {
    @SerializedName("_id")
    private String id;

    @SerializedName("profile")
    private UserProfile userProfile;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("nim")
    private String nim;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("gender")
    private String gender;

    @SerializedName("isVerified")
    private boolean isVerified;

    @SerializedName("batch")
    private String batch;

    @SerializedName("isAdmin")
    private boolean isAdmin;

    @SerializedName("userType")
    private String userType;

    @SerializedName("studyProgramId")
    private StudyProgram studyProgram;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("isCrowdfunding")
    private String crowdfunding;

    public User(String id, String fullName, String nim, String email, String password, String gender, boolean isVerified, String batch, boolean isAdmin, String userType, String dateOfBirth, String crowdfunding) {
        this.id = id;
        this.fullName = fullName;
        this.nim = nim;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.isVerified = isVerified;
        this.batch = batch;
        this.isAdmin = isAdmin;
        this.userType = userType;
        this.dateOfBirth = dateOfBirth;
        this.crowdfunding = crowdfunding;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getBatch() { return batch; }

    public void setBatch(String batch) { this.batch = batch; }

    public boolean isAdmin() { return isAdmin; }

    public void setAdmin(boolean admin) { isAdmin = admin; }

    public String getUserType() { return userType; }

    public void setUserType(String userType) { this.userType = userType; }

    public StudyProgram getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCrowdfunding() {
        return crowdfunding;
    }

    public void setCrowdfunding(String crowdfunding) {
        this.crowdfunding = crowdfunding;
    }
}
