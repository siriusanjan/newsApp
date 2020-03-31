package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.for_currency;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BaseCurrencyFragment extends BottomSheetDialogFragment {
    private BaseCurrencySheetListener baseCurrencySheetListener;

    public void setBaseCurrencySheetListener(BaseCurrencySheetListener baseCurrencySheetListener) {
        this.baseCurrencySheetListener = baseCurrencySheetListener;
    }

    public void performOnNewUpdate() {
        baseCurrencySheetListener.onNewUpdate();
    }


    public interface BaseCurrencySheetListener {
        void onNewUpdate();
    }
}
