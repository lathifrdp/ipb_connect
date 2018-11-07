package com.example.lathifrdp.demoapp.api;

import com.example.lathifrdp.demoapp.model.Event;
import com.example.lathifrdp.demoapp.model.JobLocation;
import com.example.lathifrdp.demoapp.model.StudyProgram;
import com.example.lathifrdp.demoapp.model.User;
import com.example.lathifrdp.demoapp.model.UserProfile;
import com.example.lathifrdp.demoapp.response.CountResponse;
import com.example.lathifrdp.demoapp.response.EventResponse;
import com.example.lathifrdp.demoapp.response.GroupResponse;
import com.example.lathifrdp.demoapp.response.JobResponse;
import com.example.lathifrdp.demoapp.response.LoginResponse;
import com.example.lathifrdp.demoapp.response.MemoriesResponse;
import com.example.lathifrdp.demoapp.response.RegisterResponse;
import com.example.lathifrdp.demoapp.response.SharingResponse;
import com.example.lathifrdp.demoapp.response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("users/login")
    Call<LoginResponse> loginRequest(
            @Field("email") String email,
            @Field("password") String password
            //@Body LoginResponse loginResponse
            );

    @GET("studyprograms/getlist")
    Call<List<StudyProgram>> getProdi();

    @FormUrlEncoded
    @POST("users")
    Call<RegisterResponse> registerRequest(
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
}
