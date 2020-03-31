package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.features_gold_silver;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BaseGoldSilverSheet extends BottomSheetDialogFragment {
    private BaseGoldSheetListener baseGoldSheetListener;

    public void setBaseGoldSheetListener(BaseGoldSheetListener baseGoldSheetListener) {
        this.baseGoldSheetListener = baseGoldSheetListener;
    }

    public void performOnNewUpdate() {
        baseGoldSheetListener.onNewUpdate();
    }


    public interface BaseGoldSheetListener {
        void onNewUpdate();
    }
}
