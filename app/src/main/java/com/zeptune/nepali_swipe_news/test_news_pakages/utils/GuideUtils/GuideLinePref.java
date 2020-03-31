package com.zeptune.nepali_swipe_news.test_news_pakages.utils.GuideUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class GuideLinePref {
    private static String GUIDE_LINE_PRE="guide_pref;";
    private static String GUIDE_SWIPE_TO_NEWS_DETAIL="guide_swipe_news_detail;";
    private static String GUIDE_SWIPE_FOR_MORE="guide_swipe_more_news;";
    SharedPreferences guide_pref;
    SharedPreferences.Editor guide_pref_edior;
    Context mContext;

    private GuideLinePref(Context mContext){
        this.mContext = mContext;
        guide_pref= mContext.getSharedPreferences(GUIDE_LINE_PRE, Context.MODE_PRIVATE);
        guide_pref_edior = guide_pref.edit();
    }
    public static GuideLinePref getInstance(Context mContext){
        return new GuideLinePref(mContext);
    }

    public void setGuideSwipeToNewsDetail(boolean isShown){
        guide_pref_edior.putBoolean(GUIDE_SWIPE_TO_NEWS_DETAIL,isShown).apply();
    }
    public boolean getGuideSwipToNewsDetail(){
        return guide_pref.getBoolean(GUIDE_SWIPE_TO_NEWS_DETAIL,false);
    }
    public void setGuideSwipeForMore(boolean isShown){
        guide_pref_edior.putBoolean(GUIDE_SWIPE_FOR_MORE,isShown).apply();
    }

    public boolean getGuideSwipeForMore(){
        return guide_pref.getBoolean(GUIDE_SWIPE_FOR_MORE,false);
    }
}
