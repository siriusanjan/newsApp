package com.zeptune.nepali_swipe_news.retrofit.retofit;


import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DataRasiFalModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.GoldDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.NewsDataArraylist;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.currencyDataModel.DataCurrencyModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsSercvices {
    @GET("/news")
    Call<NewsDataArraylist> getAllNews();

    @GET("/get-category-type?code=killerups")
    Call<String> getAllCategory();

    @GET("/show-latest-news?code=killerups")
    Call<DataumListModel> getLatestNews();

    @GET("show-category-news/{categoryCode}?code=killerups")
    Call<DataumListModel> getCategoryWiseNews(@Path("categoryCode") String categoryCode);

    @GET("show-latest-news?code=killerups")
    Call<DataumListModel> getLatestNewsPage(@Query("page") String pagenumber);


    @GET("show-category-news/{categoryCode}?code=killerups")
    Call<DataumListModel> getCategoryWiseNewsPage(@Path("categoryCode") String categoryCode, @Query("page") String pagenumber);


    @GET("rashi-today")
    Call<DataRasiFalModel> getRasiFal();

    @GET("gold-today")
    Call<ArrayList<GoldDataModel>> getGoldPrice();

    @GET("rate-today")
    Call<DataCurrencyModel> getCurrencyModel();


}

