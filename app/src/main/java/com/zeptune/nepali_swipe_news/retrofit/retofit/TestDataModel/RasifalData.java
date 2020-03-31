package com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel;

import java.util.ArrayList;

public class RasifalData {
    private ArrayList<RasiFalDatum> fetched;
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

    public ArrayList<RasiFalDatum> getFetched() {
        return fetched;
    }

    public void setFetched(ArrayList<RasiFalDatum> fetched) {
        this.fetched = fetched;
    }
}
