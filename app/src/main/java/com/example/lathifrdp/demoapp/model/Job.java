package com.example.lathifrdp.demoapp.model;

import com.google.gson.annotations.SerializedName;

public class Job {
    @SerializedName("_id")
    private String id;

//    @SerializedName("profile")
//    private UserProfile userProfile;

    @SerializedName("createdBy")
    private User user;

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

    @SerializedName("jobQualification")
    private String jobQualification;

    @SerializedName("salaryMax")
    private String salaryMax;

    @SerializedName("salaryMin")
    private String salaryMin;

    @SerializedName("jobLocationId")
    private JobLocation jobLocation;

    @SerializedName("closeDate")
    private String closeDate;

    @SerializedName("file")
    private String file;

    public Job(String id, String address, String company, String companyProfile, String email, String subject, String title, String jobQualification,String jobDescription, String salaryMax, String salaryMin, JobLocation jobLocation, String closeDate, String file){
        this.id = id;
        this.address = address;
        this.company = company;
        this.companyProfile = companyProfile;
        this.email = email;
        this.subject = subject;
        this.title = title;
        this.jobQualification = jobQualification;
        this.jobDescription = jobDescription;
        this.salaryMax = salaryMax;
        this.salaryMin = salaryMin;
        this.jobLocation = jobLocation;
        this.closeDate = closeDate;
        this.file = file;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //    public UserProfile getUserProfile() {
//        return userProfile;
//    }
//
//    public void setUserProfile(UserProfile userProfile) {
//        this.userProfile = userProfile;
//    }

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

    public String getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(String salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(String salaryMin) {
        this.salaryMin = salaryMin;
    }

    public JobLocation getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(JobLocation jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getJobQualification() {
        return jobQualification;
    }

    public void setJobQualification(String jobQualification) {
        this.jobQualification = jobQualification;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
