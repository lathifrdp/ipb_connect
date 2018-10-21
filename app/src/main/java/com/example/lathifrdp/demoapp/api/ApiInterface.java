package com.example.lathifrdp.demoapp.api;

import com.example.lathifrdp.demoapp.model.StudyProgram;
import com.example.lathifrdp.demoapp.model.User;
import com.example.lathifrdp.demoapp.response.CountResponse;
import com.example.lathifrdp.demoapp.response.EventResponse;
import com.example.lathifrdp.demoapp.response.JobResponse;
import com.example.lathifrdp.demoapp.response.LoginResponse;
import com.example.lathifrdp.demoapp.response.RegisterResponse;
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
                               //@Query("batch") String batch);
                       //@Query("studyProgramId") String studyProgramId,
                       @Query("isVerified") String isVerified);

    @GET("users/profiles/{id}")
    Call<User> getProfile(@Header("Authorization") String token,
                               @Path("id") String id);

    @GET("vacancies")
    Call<JobResponse> getJob(@Header("Authorization") String token,
                             @Query("title") String title,
                             @Query("page") Integer page);

    @GET("events")
    Call<EventResponse> getEvent(@Header("Authorization") String token,
                                 //@Query("title") String title,
                                 @Query("page") Integer page);
}
