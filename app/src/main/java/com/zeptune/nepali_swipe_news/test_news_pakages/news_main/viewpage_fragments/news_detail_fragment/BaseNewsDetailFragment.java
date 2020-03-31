package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_detail_fragment;

import androidx.fragment.app.Fragment;

public class BaseNewsDetailFragment extends Fragment {
    private NDFListener ndfListener;

    public void setNdfListener(NDFListener ndfListener) {
        this.ndfListener = ndfListener;
    }

    protected void performBackPress() {
        ndfListener.onBackPress();
    }

    public interface NDFListener {
        void onBackPress();
    }
}
