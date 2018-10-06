package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class Job {
    @SerializedName("_id")
    private String id;

    @SerializedName("profile")
    private UserProfile userProfile;

    @SerializedName("address")
    private String address;

    @SerializedName("company")
    private String company;

    @SerializedName("companyProfile")
    private String companyProfile;

    @SerializedName("email")
    private String email;

    @SerializedName("subject")
    private String subject;

    @SerializedName("title")
    private String title;

    @SerializedName("jobDescription")
    private String jobDescription;

    @SerializedName("salaryMax")
    private Integer salaryMax;

    @SerializedName("salaryMin")
    private Integer salaryMin;

    public Job(String id, String address, String company, String companyProfile, String email, String subject, String title, String jobDescription, Integer salaryMax, Integer salaryMin){
        this.id = id;
        this.address = address;
        this.company = company;
        this.companyProfile = companyProfile;
        this.email = email;
        this.subject = subject;
        this.title = title;
        this.jobDescription = jobDescription;
        this. salaryMax = salaryMax;
        this.salaryMin = salaryMin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Integer getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Integer salaryMax) {
        this.salaryMax = salaryMax;
    }

    public Integer getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Integer salaryMin) {
        this.salaryMin = salaryMin;
    }
}
