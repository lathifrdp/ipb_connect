package com.example.lathifrdp.demoapp.api;

import com.example.lathifrdp.demoapp.model.Event;
import com.example.lathifrdp.demoapp.model.Job;
import com.example.lathifrdp.demoapp.model.JobLocation;
import com.example.lathifrdp.demoapp.model.Memory;
import com.example.lathifrdp.demoapp.model.StudyProgram;
import com.example.lathifrdp.demoapp.model.UserProfile;
import com.example.lathifrdp.demoapp.response.GetCommentResponse;
import com.example.lathifrdp.demoapp.response.PostCommentResponse;
import com.example.lathifrdp.demoapp.response.CountResponse;
import com.example.lathifrdp.demoapp.response.EventResponse;
import com.example.lathifrdp.demoapp.response.GroupResponse;
import com.example.lathifrdp.demoapp.response.JobResponse;
import com.example.lathifrdp.demoapp.response.LoginResponse;
import com.example.lathifrdp.demoapp.response.MemoriesResponse;
import com.example.lathifrdp.demoapp.response.PostEventResponse;
import com.example.lathifrdp.demoapp.response.PostJobResponse;
import com.example.lathifrdp.demoapp.response.PostMemoriesResponse;
import com.example.lathifrdp.demoapp.response.RegisterResponse;
import com.example.lathifrdp.demoapp.response.SharingResponse;
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
    Call<GroupResponse> getGroup(@Header("Authorization") String token);

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
}
