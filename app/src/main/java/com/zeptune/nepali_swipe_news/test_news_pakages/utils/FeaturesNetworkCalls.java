package com.zeptune.nepali_swipe_news.test_news_pakages.utils;

import android.util.Log;

import com.zeptune.nepali_swipe_news.retrofit.retofit.ServiceFactory;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DataRasiFalModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.GoldDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.GoldModelWrapper;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.currencyDataModel.DataCurrencyModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CurrencyDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.DateDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.GoldDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.RasifalDatabaseUtils;
import com.zeptune.nepali_swipe_news.utils.NewsStaticVarialbles;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeaturesNetworkCalls {

    public static void getCurrencyExchangeRate(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum, CurrencyDatabaseUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        Call<DataCurrencyModel> latestRasifal = ServiceFactory.getNewsServices().getCurrencyModel();
        latestRasifal.enqueue((new Callback<DataCurrencyModel>() {
            @Override
            public void onResponse(Call<DataCurrencyModel> call, Response<DataCurrencyModel> response) {
                Log.d("mycurrencyDat", "onResponse: " + call.request().url());
                if (!response.isSuccessful() || response.body() == null || response.errorBody() != null) {
                    onDatabaseChangeListener.onFail("Cannot connect to server");
                    return;
                }
                NewsStaticVarialbles.IS_CURRENC_EXCHANGED_CALLED = true;
                DataCurrencyModel dataRasiFalModel = response.body();
                dataRasiFalModel.getData().setMyDate(DateDatabaseUtils.getDate());
                if (CurrencyDatabaseUtils.isSavedModel(featuresTypesEnum).equals(CurrencyDatabaseUtils.CheckCurrencyDataStatus.ALREADY_SAVED)) {
                    Log.d("mycurrencyDat", "onResponse: to delete " + response.body().getData().getFetched_data().size());

                    CurrencyDatabaseUtils.deleteGoldDatabase(featuresTypesEnum);
                    CurrencyDatabaseUtils.addNewNewsData(dataRasiFalModel, featuresTypesEnum, onDatabaseChangeListener);

                } else {
                    Log.d("mycurrencyDat", "onResponse: to new save " + response.body().getData().getFetched_data().size());

                    CurrencyDatabaseUtils.addNewNewsData(dataRasiFalModel, featuresTypesEnum, onDatabaseChangeListener);
                }

            }

            @Override
            public void onFailure(Call<DataCurrencyModel> call, Throwable t) {
                Log.d("mycurrencyDat", "onfail: " + call.request().url());

                onDatabaseChangeListener.onFail("Failed to make request");
            }
        }));
    }

    public static void getRasifalFromCloud(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum, RasifalDatabaseUtils.OnDatabaseChangeListener onDatabaseChangeListener) {

        Call<DataRasiFalModel> latestRasifal = ServiceFactory.getRasifals().getRasiFal();
        latestRasifal.enqueue((new Callback<DataRasiFalModel>() {
            @Override
            public void onResponse(Call<DataRasiFalModel> call, Response<DataRasiFalModel> response) {
                Log.d("rasifalUrl", "onResponse: " + call.request().url());
                if (!response.isSuccessful() || response.body() == null || response.errorBody() != null) {
                    onDatabaseChangeListener.onFail("Cannot connect to server");
                    return;
                }
                DataRasiFalModel dataRasiFalModel = response.body();
                NewsStaticVarialbles.IS_RASIFAL_CALLED = true;

                dataRasiFalModel.getData().setMyDate(DateDatabaseUtils.getDate());
                if (RasifalDatabaseUtils.isSavedModel(featuresTypesEnum).equals(RasifalDatabaseUtils.CheckRasiDataStatus.ALREADY_SAVED)) {
                    RasifalDatabaseUtils.deleteGoldDatabase(featuresTypesEnum);
                    RasifalDatabaseUtils.addNewNewsData(dataRasiFalModel, featuresTypesEnum, onDatabaseChangeListener);

                } else if (RasifalDatabaseUtils.isSavedModel(featuresTypesEnum).equals(RasifalDatabaseUtils.CheckRasiDataStatus.NOT_SAVED_YET)) {
                    RasifalDatabaseUtils.addNewNewsData(dataRasiFalModel, featuresTypesEnum, onDatabaseChangeListener);
                }

            }

            @Override
            public void onFailure(Call<DataRasiFalModel> call, Throwable t) {
                Log.d("rasifalUrl", "onfail: " + call.request().url());

                onDatabaseChangeListener.onFail("Failed to make request");
            }
        }));
    }

    public static void getGoldPriceFromCloud(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum, GoldDatabaseUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        Log.d("myGoldSilverUrl", "response: out");

        Call<ArrayList<GoldDataModel>> latestRasifal = ServiceFactory.getRasifals().getGoldPrice();
        latestRasifal.enqueue((new Callback<ArrayList<GoldDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GoldDataModel>> call, Response<ArrayList<GoldDataModel>> response) {

                if (!response.isSuccessful() || response.body() == null || response.errorBody() != null) {

                    onDatabaseChangeListener.onFail("Cannot connect to server");
                    return;
                }

                ArrayList<GoldDataModel> goldDataModels = response.body();
                GoldModelWrapper goldModelWrapper = new GoldModelWrapper();
                NewsStaticVarialbles.IS_GOLD_SILVER_CALLED = true;
                goldModelWrapper.goldDataModels = response.body();
                if (GoldDatabaseUtils.isSavedModel(featuresTypesEnum).equals(GoldDatabaseUtils.CheckGoldDataStatus.ALREADY_SAVED)) {
                    GoldDatabaseUtils.deleteGoldDatabase(featuresTypesEnum);
                    GoldDatabaseUtils.addNewNewsData(goldModelWrapper, featuresTypesEnum, onDatabaseChangeListener);
                    ArrayList<GoldDataModel> savemodel = GoldDatabaseUtils.saveGoldDataModel(featuresTypesEnum).goldDataModels;
                } else {
                    GoldDatabaseUtils.addNewNewsData(goldModelWrapper, featuresTypesEnum, onDatabaseChangeListener);
                    ArrayList<GoldDataModel> savemodel = GoldDatabaseUtils.saveGoldDataModel(featuresTypesEnum).goldDataModels;

                }
            }

            @Override
            public void onFailure(Call<ArrayList<GoldDataModel>> call, Throwable t) {
                onDatabaseChangeListener.onFail("Failed to make request");
            }
        }));
    }
}

