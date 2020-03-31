package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries;

public class CountryWiseBarModel {
   public int barYValue;
    public  String countryName;
    public String timeMill;
    public CountryWiseBarModel(int barYValue,String countryName,String timeMill){
        this.barYValue=barYValue;
        this.countryName=countryName;
        this.timeMill=timeMill;
    }

    public int getBarYValue() {
        return barYValue;
    }

    public String getCountryName() {
        return countryName;
    }
}
