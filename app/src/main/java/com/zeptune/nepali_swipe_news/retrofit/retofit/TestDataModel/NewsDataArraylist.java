package com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel;

import java.util.ArrayList;

public class NewsDataArraylist {
    String status;
    String message;

    ArrayList<Data> data;

    public NewsDataArraylist(ArrayList<Data> data,String status,String message ){
        this.data= data;
        this.message=message;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
