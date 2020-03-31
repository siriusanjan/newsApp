package com.zeptune.nepali_swipe_news.test_news_pakages.utils.gson_utils;

import android.util.Log;

import com.google.gson.Gson;
import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DataRasiFalModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DateModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.GoldModelWrapper;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.AffCountryDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.CaseDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseListModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.currencyDataModel.DataCurrencyModel;

public class GesonConverter {


    public static String NewsModelToJson(DataumListModel myObj) {
        Log.d("afterNetCall", "NewsModelToJson: " + myObj.getData().size());
        Gson gson = new Gson();
        String myString = gson.toJson(myObj);
        GesonConverter.NewsJsonModleClass(myString);
        Log.d("afterNetCall", "NewsModelToJson: " + myString);

        return myString;
    }

    public static DataumListModel NewsJsonModleClass(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, DataumListModel.class);
    }


    public static String GoldModelToJson(GoldModelWrapper myObj) {
        Gson gson = new Gson();
        return gson.toJson(myObj);
    }

    public static GoldModelWrapper GoldJsonModleClass(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GoldModelWrapper.class);
    }


    public static String RasiModelToJson(DataRasiFalModel myObj) {
        Gson gson = new Gson();
        return gson.toJson(myObj);
    }

    public static DataRasiFalModel RasiJsonModleClass(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, DataRasiFalModel.class);
    }


    public static String DateModelToJson(DateModel myObj) {
        Gson gson = new Gson();
        return gson.toJson(myObj);
    }

    public static DateModel DateJsonModleClass(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, DateModel.class);
    }


    public static String CoroAffModelToJson(AffCountryDataModel myObj) {
        Gson gson = new Gson();
        return gson.toJson(myObj);
    }

    public static AffCountryDataModel AffCoroJsonToModle(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, AffCountryDataModel.class);
    }


    public static String CoroCaseDataModelToString(CaseDataModel myObj) {
        Gson gson = new Gson();
        return gson.toJson(myObj);
    }

    public static CaseDataModel CoroCaseJsonToDM(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, CaseDataModel.class);
    }

    public static String CoroCountryWiseDMToJson(CountryWiseListModel myObj) {
        Gson gson = new Gson();
        return gson.toJson(myObj);
    }

    public static CountryWiseListModel CoroCountryWiseJsonTODM(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, CountryWiseListModel.class);
    }


    public static String CurrencyExchageDMToJson(DataCurrencyModel myObj) {
        Gson gson = new Gson();
        return gson.toJson(myObj);
    }

    public static DataCurrencyModel CurrencyExchangJsonTODM(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, DataCurrencyModel.class);
    }
}
