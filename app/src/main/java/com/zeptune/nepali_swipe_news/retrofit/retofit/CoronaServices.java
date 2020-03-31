package com.zeptune.nepali_swipe_news.retrofit.retofit;

import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.AffCountryDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.CaseDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseListModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoronaServices {
//    https://news.apso.cf/by-country
//    https://news.apso.cf/world-stat
//    https://news.apso.cf/affected-country


    @GET("affected-country")
    Call<AffCountryDataModel> getAllAffectedCountry();

    @GET("world-stat")
    Call<CaseDataModel> casesTillToday();

    @GET("by-country")
    Call<CountryWiseListModel> getCountryWiseState();
}
