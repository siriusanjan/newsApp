package com.zeptune.nepali_swipe_news.test_news_pakages.loading;

import android.content.Context;
import android.content.SharedPreferences;

public class LastLoadedStorage {
    private static final String LOAD_PREFERENCE_NAME="pref_name";
    private static final String LAST_LOAD_TIME_STAMP="load_time_stamp";

    private SharedPreferences loadPreference;
    private SharedPreferences.Editor loadPreferenceEditor;
    private Context mContext;


    private LastLoadedStorage(Context mContext){
        this.mContext = mContext;
        loadPreference = mContext.getSharedPreferences(LOAD_PREFERENCE_NAME, Context.MODE_PRIVATE);
        loadPreferenceEditor = loadPreference.edit();
    }
    public static LastLoadedStorage getInstance(Context mContext){
        return new LastLoadedStorage(mContext);
    }

    public void setLastLoadTimeStamp(String timeStamp){
        loadPreferenceEditor.putString(LAST_LOAD_TIME_STAMP,timeStamp).apply();
    }

    public String getLastLoadTimeStamp(){
        return loadPreference.getString(LAST_LOAD_TIME_STAMP,"");
    }
}
