package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.serach;

public class SearchResultModel {
    public String countryName;
    public int index;
    public String death;
    public String total_cases;
    public String total_recover;


    public SearchResultModel(String countryName, String death,String total_cases,String total_recover,int index) {
        this.countryName = countryName;
        this.index = index;
        this.death=death;
        this.total_cases=total_cases;
        this.total_recover=total_recover;

    }
}
