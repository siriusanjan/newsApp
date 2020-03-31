package com.zeptune.nepali_swipe_news.retrofit.retofit;

import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.CategoryCallResponse;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.TestDataModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface CategoryService {
    @GET("category/list")
    @Headers("Accept:application/json")
    Call<CategoryCallResponse> getCategoryList(@Header("Authorization") String bearer_token);

    @GET("/todos/1")
    Call<TestDataModel> testData();


    @POST("category/add")
    @Headers("Accept:application/json")
    @Multipart
    Call<ResponseBody> addCategory(@Header("Authorization") String bearer_token, @Field("category_name") String categoryName, @Field("parent_category_id") String parentCategoryId);


    @POST("category/edit/{categoryID}")
    @Headers("Accept:application/json")
    Call<ResponseBody> editCategory(@Path("categoryID") String categoryID, @Header("Authorization") String bearer_token, @Field("category_name") String categoryName, @Field("parent_category_id") String parentCategoryId);


    @POST("category/delete/{categoryID}")
    @Headers("Accept:application/json")
    Call<ResponseBody> deleteCategory(@Path("categoryID") String categoryID, @Header("Authorization") String bearerToken, @Field("categoryID") String parentCategoryId);


    @GET("category/detail/{categoryID}")
    @Headers("Accept:application/json")
    Call<ResponseBody> getCategoryDetail(@Header("Authorization") String bearerToken, @Path("categoryID") String categoryID);


}
