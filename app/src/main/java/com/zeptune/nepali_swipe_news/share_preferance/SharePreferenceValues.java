package com.zeptune.nepali_swipe_news.share_preferance;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceValues {
    public final String NEWS_UP_SWIPE = "newsSwipeUp";
    public final String CATEGORY_SWIPE = "categorySwipe";
    public final String NEWS_DETAIL_SWIPE = "newsDetailSwipe";
    public final String SWIPE_PREFERENCE = "swipePreference";
    //rasifal
    public final String RASIFAL_PREFERENCE = "rasifalPreference";
    public final String RASIFAL = "rasifal";
    //goldSilverPrice
    public final String GOLD_SILVER_PREFERENCE = "goldSilverPreference";
    public final String GOLD_SILVER = "goldSilver";

    public void saveBooleanToPreference(Context mContext, String booleanKey, boolean isSwiped, String preferenceName) {
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(booleanKey, isSwiped);
        editor.apply();
    }

    public boolean isSwipedFromPreference(Context mContext, String booleanKey, String preferenceName) {
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(booleanKey, true);
    }

    public void saveStringToPreference(Context mContext, String stringKey, String stringToSave, String preferenceName) {
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(stringKey, stringToSave);
        editor.apply();
    }

    public String getSavedString(Context mContext, String stringKey, String preferenceName) {
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return sharedpreferences.getString(stringKey, null);
    }
}
