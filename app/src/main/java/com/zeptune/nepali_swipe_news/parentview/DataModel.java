package com.zeptune.nepali_swipe_news.parentview;

public class DataModel {
    String name;
    String Title;
    String Description;

    public DataModel(String name, String Title, String Description){
        this.name=name;
        this.Title=Title;
        this.Description=Description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
