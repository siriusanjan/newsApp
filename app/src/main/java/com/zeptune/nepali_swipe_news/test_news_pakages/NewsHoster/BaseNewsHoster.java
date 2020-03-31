package com.zeptune.nepali_swipe_news.test_news_pakages.NewsHoster;

import androidx.fragment.app.Fragment;

public abstract class BaseNewsHoster extends Fragment {

    private NewsHosterListeners newsHosterListeners;

    public void setNewsHosterListeners(NewsHosterListeners newsHosterListeners) {
        this.newsHosterListeners = newsHosterListeners;
    }


    public void performbackPress(int position) {
        newsHosterListeners.onBackPress(position);
    }

    public interface NewsHosterListeners {
        void onBackPress(int currentPagerPosition);
    }

    public abstract void setPosition(int position);
}
