package com.example.lathifrdp.demoapp.api;

import android.content.pm.PackageInfo;

import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.model.Event;
import com.example.lathifrdp.demoapp.model.GroupDiscussion;
import com.example.lathifrdp.demoapp.model.Job;
import com.example.lathifrdp.demoapp.model.JobLocation;
import com.example.lathifrdp.demoapp.model.KnowledgeSharing;
import com.example.lathifrdp.demoapp.model.KnowledgeSharingCategory;
import com.example.lathifrdp.demoapp.model.Memory;
import com.example.lathifrdp.demoapp.model.StudyProgram;
import com.example.lathifrdp.demoapp.model.UserProfile;
import com.example.lathifrdp.demoapp.response.CrowdResponse;
import com.example.lathifrdp.demoapp.response.DonationResponse;
import com.example.lathifrdp.demoapp.response.DonaturResponse;
import com.example.lathifrdp.demoapp.response.GetCommentResponse;
import com.example.lathifrdp.demoapp.response.PinResponse;
import com.example.lathifrdp.demoapp.response.PostBookmarkResponse;
import com.example.lathifrdp.demoapp.response.PostCommentResponse;
import com.example.lathifrdp.demoapp.response.CountResponse;
import com.example.lathifrdp.demoapp.response.EventResponse;
import com.example.lathifrdp.demoapp.response.GroupResponse;
import com.example.lathifrdp.demoapp.response.JobResponse;
import com.example.lathifrdp.demoapp.response.LoginResponse;
import com.example.lathifrdp.demoapp.response.MemoriesResponse;
import com.example.lathifrdp.demoapp.response.PostCrowdRequestResponse;
import com.example.lathifrdp.demoapp.response.PostCrowdfundingResponse;
import com.example.lathifrdp.demoapp.response.PostDonasiResponse;
import com.example.lathifrdp.demoapp.response.PostEventResponse;
import com.example.lathifrdp.demoapp.response.PostGroupResponse;
import com.example.lathifrdp.demoapp.response.PostJobResponse;
import com.example.lathifrdp.demoapp.response.PostLikeResponse;
import com.example.lathifrdp.demoapp.response.PostMemoriesResponse;
import com.example.lathifrdp.demoapp.response.PostSharingResponse;
import com.example.lathifrdp.demoapp.response.ProgressResponse;
import com.example.lathifrdp.demoapp.response.RegisterResponse;
import com.example.lathifrdp.demoapp.response.SharingResponse;
import com.example.lathifrdp.demoapp.response.StatusResponse;
import com.example.lathifrdp.demoapp.response.UploadCrowdfundingResponse;
import com.example.lathifrdp.demoapp.response.UserResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("users/login")
    Call<LoginResponse> postLogin(
            @Field("email") String email,
            @Field("password") String password
            );

    @GET("studyprograms/getlist")
    Call<List<StudyProgram>> getProdi();

    @FormUrlEncoded
    @POST("users")
    Call<RegisterResponse> postRegister(
            @Field("fullName") String fullName,
            @Field("gender") String gender,
            @Field("dateOfBirth") String dateOfBirth,
            @Field("email") String email,
            @Field("password") String password,
            @Field("nim") String nim,
            @Field("userType") String userType,
            @Field("batch") String batch,
            @Field("studyProgramId") String studyProgramId,
            @Field("isVerified") Integer isVerified
    );

    @Multipart
    @POST("users/profiles/upload/{id}")
    Call<RegisterResponse> uploadPhoto(
            @Path("id") String id,
            @Part MultipartBody.Part photo);

    @GET("users/count/")
    Call<CountResponse> getCount(@Header("Authorization") String token);

    @GET("users")
    Call<UserResponse> getUser(@Header("Authorization") String token,
                               @Query("fullName") String fullName,
                               @Query("page") Integer page,
                               @Query("batch") String batch,
                       @Query("studyProgramId") String studyId,
                       @Query("isVerified") String isVerified);

    @GET("users/profiles/{id}")
    Call<UserProfile> getProfile(@Header("Authorization") String token,
                                 @Path("id") String id);

    @GET("vacancies")
    Call<JobResponse> getJob(@Header("Authorization") String token,
                             @Query("title") String title,
                             @Query("jobLocationId") String jobLocationId,
                             @Query("page") Integer page);

    @GET("joblocations/getlist")
    Call<List<JobLocation>> getLocation(@Header("Authorization") String token);

    @GET("events")
    Call<EventResponse> getEvent(@Header("Authorization") String token,
                                 //@Query("title") String title,
                                 @Query("page") Integer page);

    @GET("events/{id}")
    Call<Event> getDetailEvent(@Header("Authorization") String token,
                               @Path("id") String id);

    @GET("knowledgesharings")
    Call<SharingResponse> getTerbaru(@Header("Authorization") String token);

    @GET("knowledgesharings/popular")
    Call<SharingResponse> getPopular(@Header("Authorization") String token);

    @GET("knowledgesharings/category/{categoryID}")
    Call<SharingResponse> getTeknologi(@Header("Authorization") String token,
                                       @Path("categoryID") String categoryID);

    @GET("knowledgesharings/category/{categoryID}")
    Call<SharingResponse> getDesain(@Header("Authorization") String token,
                                       @Path("categoryID") String categoryID);

    @GET("knowledgesharings/category/{categoryID}")
    Call<SharingResponse> getBisnis(@Header("Authorization") String token,
                                       @Path("categoryID") String categoryID);

    @GET("knowledgesharings/category/{categoryID}")
    Call<SharingResponse> getKesehatan(@Header("Authorization") String token,
                                       @Path("categoryID") String categoryID);

    @GET("knowledgesharings/category/{categoryID}")
    Call<SharingResponse> getUmum(@Header("Authorization") String token,
                                       @Path("categoryID") String categoryID);

    @GET("groupdiscussions")
    Call<GroupResponse> getGroup(@Header("Authorization") String token,
                                 @Query("page") Integer page);

    @GET("memories")
    Call<MemoriesResponse> getMemories(@Header("Authorization") String token,
                                       @Query("page") Integer page);

    @GET("memories/{id}")
    Call<Memory> getDetailMemory(@Header("Authorization") String token,
                                 @Path("id") String id);

    @FormUrlEncoded
    @POST("memories/comment/{id}")
    Call<PostCommentResponse> postComment(@Header("Authorization") String token,
                                             @Field("value") String value,
                                             @Field("createdBy") String createdBy,
                                             @Path("id") String id);

    @GET("memories/comment/{id}")
    Call<GetCommentResponse> getComment(@Header("Authorization") String token,
                                        @Path("id") String id);

    @Multipart
    @POST("memories")
    Call<PostMemoriesResponse> postMemories(@Header("Authorization") String token,
                                            @Part MultipartBody.Part photo,
                                            @Part("caption") RequestBody caption,
                                            @Part("createdBy") RequestBody createdBy);

    @FormUrlEncoded
    @POST("vacancies")
    Call<PostJobResponse> postJob(
            @Header("Authorization") String token,
            @Field("title") String title,
            @Field("company") String company,
            @Field("jobLocationId") String jobLocationId,
            @Field("address") String address,
            @Field("salaryMin") String salaryMin,
            @Field("salaryMax") String salaryMax,
            @Field("closeDate") String closeDate,
            @Field("companyProfile") String companyProfile,
            @Field("jobQualification") String jobQualification,
            @Field("jobDescription") String jobDescription,
            @Field("email") String email,
            @Field("subject") String subject,
            @Field("file") String file,
            @Field("createdBy") String createdBy
    );

    @GET("vacancies/{id}")
    Call<Job> getDetailVacancy(@Header("Authorization") String token,
                               @Path("id") String id);

    @Multipart
    @POST("events")
    Call<PostEventResponse> postEvent(@Header("Authorization") String token,
                                      @Part("title") RequestBody title,
                                      @Part("place") RequestBody place,
                                      @Part("startDate") RequestBody startDate,
                                      @Part("endDate") RequestBody endDate,
                                      @Part("startTime") RequestBody startTime,
                                      @Part("endTime") RequestBody endTime,
                                      @Part("description") RequestBody description,
                                      @Part("contact") RequestBody contact,
                                      @Part("price") RequestBody price,
                                      @Part MultipartBody.Part picture,
                                      @Part("createdBy") RequestBody createdBy);

    @GET("groupdiscussions/comment/{id}")
    Call<GetCommentResponse> getDiscussion(@Header("Authorization") String token,
                                        @Path("id") String id);

    @GET("groupdiscussions/{id}")
    Call<GroupDiscussion> getDetailGroup(@Header("Authorization") String token,
                                         @Path("id") String id);

    @FormUrlEncoded
    @POST("groupdiscussions/comment/{id}")
    Call<PostCommentResponse> postDiscussion(@Header("Authorization") String token,
                                             @Field("value") String value,
                                             @Field("createdBy") String createdBy,
                                             @Path("id") String id);

    @FormUrlEncoded
    @POST("groupdiscussions")
    Call<PostGroupResponse> postGroup(@Header("Authorization") String token,
                                      @Field("title") String title,
                                      @Field("description") String description,
                                      @Field("createdBy") String createdBy);

    @GET("knowledgesharings/{id}")
    Call<KnowledgeSharing> getDetailKnowledge(@Header("Authorization") String token,
                                              @Path("id") String id);

    @FormUrlEncoded
    @POST("knowledgesharings/comment/{id}")
    Call<PostCommentResponse> postCommentKnowledge(@Header("Authorization") String token,
                                                   @Field("comment") String comment,
                                                   @Field("createdBy") String createdBy,
                                                   @Path("id") String id);

    @GET("knowledgesharings/comment/{id}")
    Call<KnowledgeSharing> getCommentKnowledge(@Header("Authorization") String token,
                                                 @Path("id") String id);

    @FormUrlEncoded
    @POST("knowledgesharings/like/{id}")
    Call<PostLikeResponse> postLikeKnowledge(@Header("Authorization") String token,
                                             @Field("createdBy") String createdBy,
                                             @Path("id") String id);

    @FormUrlEncoded
    @POST("knowledgesharings/unlike/{id}")
    Call<PostLikeResponse> postUnlikeKnowledge(@Header("Authorization") String token,
                                               @Field("createdBy") String createdBy,
                                               @Path("id") String id);

    @FormUrlEncoded
    @POST("knowledgesharings/bookmark/{id}")
    Call<PostBookmarkResponse> postBookmarkKnowledge(@Header("Authorization") String token,
                                                     @Field("createdBy") String createdBy,
                                                     @Path("id") String id);

    @FormUrlEncoded
    @POST("knowledgesharings/unbookmark/{id}")
    Call<PostBookmarkResponse> postUnbookmarkKnowledge(@Header("Authorization") String token,
                                                       @Field("createdBy") String createdBy,
                                                       @Path("id") String id);
    @GET("knowledgesharingcategories")
    Call<List<KnowledgeSharingCategory>> getCategory(@Header("Authorization") String token);

    @Multipart
    @POST("knowledgesharings")
    Call<PostSharingResponse> postKnowledge(@Header("Authorization") String token,
                                            @Part MultipartBody.Part file,
                                            @Part("title") RequestBody title,
                                            @Part("description") RequestBody description,
                                            @Part("category") RequestBody category,
                                            @Part("createdBy") RequestBody createdBy);

    @GET("vacancies")
    Call<JobResponse> getJobPost(@Header("Authorization") String token,
                                 @Query("createdBy") String createdBy,
                                 @Query("page") Integer page);

    @GET("events")
    Call<EventResponse> getEventPost(@Header("Authorization") String token,
                                     @Query("createdBy") String createdBy,
                                     @Query("page") Integer page);

    @FormUrlEncoded
    @POST("memories/like/{id}")
    Call<PostLikeResponse> postLikeMemories(@Header("Authorization") String token,
                                             @Field("createdBy") String createdBy,
                                             @Path("id") String id);

    @FormUrlEncoded
    @POST("memories/unlike/{id}")
    Call<PostLikeResponse> postUnlikeMemories(@Header("Authorization") String token,
                                               @Field("createdBy") String createdBy,
                                               @Path("id") String id);

    @GET("memories")
    Call<MemoriesResponse> getMemoriesPost(@Header("Authorization") String token,
                                           @Query("createdBy") String createdBy,
                                           @Query("page") Integer page);

    @FormUrlEncoded
    @POST("crowdfundings/join")
    Call<PostCrowdRequestResponse> postCrowdRequest(@Header("Authorization") String token,
                                                    @Field("createdBy") String createdBy);

    @GET("crowdfundings/checkstatus/{id}")
    Call<StatusResponse> getStatus(@Header("Authorization") String token,
                                   @Path("id") String id);

    @FormUrlEncoded
    @POST("crowdfundings/login")
    Call<PinResponse> postPin(
            @Header("Authorization") String token,
            @Field("createdBy") String createdBy,
            @Field("key") String key
    );

    @GET("crowdfundings")
    Call<CrowdResponse> getCrowdAlumni(@Header("Authorization") String token,
                                       @Query("page") Integer page,
                                       @Query("verified") String verified);

    @GET("crowdfundings/{id}")
    Call<Crowdfunding> getDetailCrowd(@Header("Authorization") String token,
                                      @Path("id") String id);

    @FormUrlEncoded
    @POST("crowdfundings/list")
    Call<CrowdResponse> getCrowdMhs(
            @Header("Authorization") String token,
            @Query("page") Integer page,
            @Field("creator") String creator
    );

    @GET("crowdfundings")
    Call<CrowdResponse> getCrowdMahasiswa(@Header("Authorization") String token,
                                          @Query("createdBy") String createdBy);

    @Multipart
    @POST("crowdfundings")
    Call<PostCrowdfundingResponse> postCrowd(@Header("Authorization") String token,
                                             @Part MultipartBody.Part file,
                                             @Part("title") RequestBody title,
                                             @Part("description") RequestBody description,
                                             @Part("contactPerson") RequestBody contactPerson,
                                             @Part("location") RequestBody location,
                                             @Part("projectType") RequestBody projectType,
                                             @Part("cost") RequestBody cost,
                                             @Part("deadline") RequestBody deadline,
                                             @Part("createdBy") RequestBody createdBy);

    @Multipart
    @POST("crowdfundings/donate/{id}")
    Call<PostDonasiResponse> postDonasi(@Header("Authorization") String token,
                                        @Part MultipartBody.Part file,
                                        @Path("id") String id,
                                        @Part("createdBy") RequestBody createdBy);

    @FormUrlEncoded
    @POST("crowdfundings/list/donatur")
    Call<DonaturResponse> getDonatur(
            @Header("Authorization") String token,
            @Query("page") Integer page,
            @Field("creator") String creator
    );

    @GET("crowdfundings/donations/{id}")
    Call<DonationResponse> getDonation(@Header("Authorization") String token,
                                       @Path("id") String id);

    @FormUrlEncoded
    @POST("groupdiscussions/like/{id}")
    Call<PostLikeResponse> postLikeGroup(@Header("Authorization") String token,
                                            @Field("createdBy") String createdBy,
                                            @Path("id") String id);

    @FormUrlEncoded
    @POST("groupdiscussions/unlike/{id}")
    Call<PostLikeResponse> postUnlikeGroup(@Header("Authorization") String token,
                                              @Field("createdBy") String createdBy,
                                              @Path("id") String id);

    @Multipart
    @POST("crowdfundings/upload/{id}")
    Call<UploadCrowdfundingResponse> uploadPhotoCrowd(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("crowdfundings/uploadVideo/{id}")
    Call<UploadCrowdfundingResponse> uploadVideoCrowd(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("crowdfundings/update/{id}")
    Call<UploadCrowdfundingResponse> updateProgress(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Part MultipartBody.Part file,
            @Part("description") RequestBody description,
            @Part("createdBy") RequestBody createdBy);

    @GET("crowdfundings/progress/{id}")
    Call<ProgressResponse> getProgress(@Header("Authorization") String token,
                                       @Path("id") String id);
}
