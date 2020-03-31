package com.zeptune.nepali_swipe_news.date_converter.models;

public class MonthDataModels {
int id;
String monthName;
public MonthDataModels(int id,String monthName){
    this.id=id;
    this.monthName=monthName;
}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
}
