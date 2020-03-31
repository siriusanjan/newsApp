package com.zeptune.nepali_swipe_news.models;

import java.util.ArrayList;

public class DataumListModel  {
    ArrayList<Datum> data;
    public MetaData meta;
    public String newsType;

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public MetaData getMeta() {
        return meta;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }

    public ArrayList<Datum> getData() {
        return data;
    }


    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }
}
