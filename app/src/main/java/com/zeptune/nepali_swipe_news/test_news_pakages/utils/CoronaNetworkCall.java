package com.zeptune.nepali_swipe_news.test_news_pakages.utils;

import android.util.Log;

import com.zeptune.nepali_swipe_news.retrofit.retofit.ServiceFactory;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.AffCountryDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.CaseDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseListModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroAffCountryDbUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCaseDbUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCountryWiseDatabaseUtils;
import com.zeptune.nepali_swipe_news.utils.NewsStaticVarialbles;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoronaNetworkCall {

    public static void getAffectedcountry(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, CoroAffCountryDbUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        Call<AffCountryDataModel> latestNewsCall = ServiceFactory.getCoronaServices().getAllAffectedCountry();
        latestNewsCall.enqueue(new Callback<AffCountryDataModel>() {
            @Override
            public void onResponse(Call<AffCountryDataModel> call, Response<AffCountryDataModel> response) {
                if (!response.isSuccessful() || response.body() == null || response.errorBody() != null) {
                    onDatabaseChangeListener.onFail("Cannot connect to server");
                    return;
                }
                AffCountryDataModel affCountryDataModel = response.body();
                if (CoroAffCountryDbUtils.isSavedModel(newsOrCoronaTypesEnum).equals(CoroAffCountryDbUtils.CheckCoroAffConDbStatus.ALREADY_SAVED)) {
                    CoroAffCountryDbUtils.deleteGoldDatabase(newsOrCoronaTypesEnum);
                    CoroAffCountryDbUtils.addNewNewsData(affCountryDataModel, newsOrCoronaTypesEnum, onDatabaseChangeListener);

                } else if (CoroAffCountryDbUtils.isSavedModel(newsOrCoronaTypesEnum).equals(CoroAffCountryDbUtils.CheckCoroAffConDbStatus.NOT_SAVED_YET)) {
                    CoroAffCountryDbUtils.addNewNewsData(affCountryDataModel, newsOrCoronaTypesEnum, onDatabaseChangeListener);
                }
            }

            @Override
            public void onFailure(Call<AffCountryDataModel> call, Throwable t) {
                onDatabaseChangeListener.onFail("Failed to make request");


            }
        });

    }

    public static void getContryWiseCoronaState(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, CoroCountryWiseDatabaseUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        Call<CountryWiseListModel> latestNewsCall = ServiceFactory.getCoronaServices().getCountryWiseState();
        latestNewsCall.enqueue(new Callback<CountryWiseListModel>() {
            @Override
            public void onResponse(Call<CountryWiseListModel> call, Response<CountryWiseListModel> response) {
                if (!response.isSuccessful() || response.body() == null || response.errorBody() != null) {
                    onDatabaseChangeListener.onFail("Cannot connect to server");

                    return;
                }
                CountryWiseListModel countryWiseListModel = response.body();
                NewsStaticVarialbles.IS_CORONA_COUNTRY_WISE_CASE_CALLED = true;

                if (CoroCountryWiseDatabaseUtils.isSavedModel(newsOrCoronaTypesEnum).equals(CoroCountryWiseDatabaseUtils.CheckCoroCountWiseDBStatus.ALREADY_SAVED)) {
                    CoroCountryWiseDatabaseUtils.deleteGoldDatabase(newsOrCoronaTypesEnum);
                    CoroCountryWiseDatabaseUtils.addNewNewsData(countryWiseListModel, newsOrCoronaTypesEnum, onDatabaseChangeListener);

                } else {
                    CoroCountryWiseDatabaseUtils.addNewNewsData(countryWiseListModel, newsOrCoronaTypesEnum, onDatabaseChangeListener);

                }


            }

            @Override
            public void onFailure(Call<CountryWiseListModel> call, Throwable t) {
                Log.d("mydata", "onFail");

                onDatabaseChangeListener.onFail("Failed to make request");


            }


        });

    }

    public static void getWorldCoronaStatus(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, CoroCaseDbUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        Call<CaseDataModel> latestNewsCall = ServiceFactory.getCoronaServices().casesTillToday();
        latestNewsCall.enqueue(new Callback<CaseDataModel>() {
            @Override
            public void onResponse(Call<CaseDataModel> call, Response<CaseDataModel> response) {
                if (!response.isSuccessful() || response.body() == null || response.errorBody() != null) {
                    onDatabaseChangeListener.onFail("Cannot connect to server");

                    return;
                }
                CaseDataModel coroCaseDbUtils = response.body();
                NewsStaticVarialbles.IS_CORONA_WORLD_CASE_CALLED = true;

                if (CoroCaseDbUtils.isSavedModel(newsOrCoronaTypesEnum).equals(CoroCaseDbUtils.CheckCoroDbStatus.ALREADY_SAVED)) {
                    CoroCaseDbUtils.deleteGoldDatabase(newsOrCoronaTypesEnum);
                    CoroCaseDbUtils.addNewNewsData(coroCaseDbUtils, newsOrCoronaTypesEnum, onDatabaseChangeListener);

                } else {
                    CoroCaseDbUtils.addNewNewsData(coroCaseDbUtils, newsOrCoronaTypesEnum, onDatabaseChangeListener);
                }

            }

            @Override
            public void onFailure(Call<CaseDataModel> call, Throwable t) {
                onDatabaseChangeListener.onFail("Failed to make request");


            }
        });

    }

}
