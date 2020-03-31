package com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.currencyDataModel;

import java.util.ArrayList;

public class CurrencyData {
    private ArrayList<CurrencyDatum> fetched_data;
    private String date;

    public String getMyDate() {
        return myDate;
    }

    public void setMyDate(String myDate) {
        this.myDate = myDate;
    }

    private String myDate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<CurrencyDatum> getFetched_data() {
        return fetched_data;
    }

    public void setFetched_data(ArrayList<CurrencyDatum> fetched_data) {
        this.fetched_data = fetched_data;
    }
}
