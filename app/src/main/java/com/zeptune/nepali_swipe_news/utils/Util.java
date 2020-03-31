package com.zeptune.nepali_swipe_news.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Util {
    private static final String MyPREFERENCES="local_value_to_check_ads";
    private static final String KEY_TO_CHECK_INTERESTIAL_ADS="check_interestial_ads";
    private  static  final int countNumber=10;


    public  static  Boolean isToShowAds(Context context){
        Boolean isShowAds=false;
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int valueStore = (sharedpreferences.getInt(KEY_TO_CHECK_INTERESTIAL_ADS, 0));
        System.out.println("loading"+ valueStore);
        if (valueStore>=countNumber){
            isShowAds=true;
        }else{
            isShowAds=false;

        }
        return  isShowAds;
    }

    public  static  void updateSharePerfence(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int fetchValue = (sharedpreferences.getInt(KEY_TO_CHECK_INTERESTIAL_ADS, 0));
        fetchValue+=1;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(KEY_TO_CHECK_INTERESTIAL_ADS,fetchValue);
        editor.apply();
    }

    public  static  void setDefaultValueToNotShowAds(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(KEY_TO_CHECK_INTERESTIAL_ADS,0);
        editor.apply();

    }
}