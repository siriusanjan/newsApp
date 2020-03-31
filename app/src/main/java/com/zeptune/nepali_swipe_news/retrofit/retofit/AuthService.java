package com.zeptune.nepali_swipe_news.retrofit.retofit;

import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.AuthResponse;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.TestDataModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface AuthService {

    @POST("login-social")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<AuthResponse> loginSocial(@Field("provider") String provider, @Field("auth_code") String authorizationCode);

    Call<TestDataModel> testData();


    @POST("login")
    @FormUrlEncoded
    Call<AuthResponse> loginSimple(@Field("email") String email, @Field("password") String password);

    @POST("register")
    @FormUrlEncoded
    Call<AuthResponse> registerUser(@Field("name") String name, @Field("email") String email, @Field("password") String password, @Field("c_password") String confirmingPassword);

    @GET("user/register-mobile")
    @Headers("Accept:application/json")
    Call<AuthResponse> registerMobile(@Header("Authorization") String bearer_token);

    @POST("reset-password")
    @Multipart
    Call<AuthResponse> recover(@Part("data") String data);
}
