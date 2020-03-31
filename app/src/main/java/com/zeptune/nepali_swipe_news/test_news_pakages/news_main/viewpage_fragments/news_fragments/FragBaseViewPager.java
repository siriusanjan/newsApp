package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_fragments;

import androidx.fragment.app.Fragment;

public abstract class FragBaseViewPager extends Fragment {
    private OnViewpagerPageChangeListner onViewpagerPageChangeListner;

    public void setUpListener(OnViewpagerPageChangeListner onViewpagerPageChangeListner) {
        this.onViewpagerPageChangeListner = onViewpagerPageChangeListner;
    }

    public void addPageChangeListener() {
        onViewpagerPageChangeListner.onPageChange();
    }

    public void addPageTurnUpListener() {
        onViewpagerPageChangeListner.onPageTurnUp();
    }

    public void addPageTurnDownListener() {
        onViewpagerPageChangeListner.onPageTurnDown();
    }

    void performPageSelection(String newsLink, int pagePosition) {
        onViewpagerPageChangeListner.onPageSelected(newsLink, pagePosition);
        onViewPagerPageSelected(newsLink, pagePosition);

    }
    void performClickFeatures(){
        onViewpagerPageChangeListner.onClickNavFeatures();
    }


    public abstract void onViewPagerPageSelected(String newsLink, int pagePosition);

    public interface OnViewpagerPageChangeListner {
        void onPageChange();

        void onPageTurnUp();

        void onPageTurnDown();

        void onPageSelected(String newsLink, int pagePosition);

        void onClickNavFeatures();
    }


}
