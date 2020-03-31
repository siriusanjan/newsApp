package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.serach;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BaseSearchBtmSheetFrag extends BottomSheetDialogFragment {
    private BtmSheetSearchListener btmSheetSearchListener;

    public void setBtmSheetSearchListener(BtmSheetSearchListener btmSheetSearchListener) {
        this.btmSheetSearchListener = btmSheetSearchListener;
    }

    public void performSearchResultClick(int index) {
        btmSheetSearchListener.onSearchResultClick(index);
    }

    public interface BtmSheetSearchListener {
        void onSearchResultClick(int index);
    }
}
