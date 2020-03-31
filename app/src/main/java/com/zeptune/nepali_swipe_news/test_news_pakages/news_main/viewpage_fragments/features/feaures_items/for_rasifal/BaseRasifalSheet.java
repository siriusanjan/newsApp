package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.for_rasifal;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BaseRasifalSheet extends BottomSheetDialogFragment {
    private BaseRasifalSheetListener baseRasifalSheetListener;

    public void setBaseRasifalSheetListener(BaseRasifalSheetListener baseRasifalSheetListener) {
        this.baseRasifalSheetListener = baseRasifalSheetListener;
    }

    public void performNewUpdate() {
        baseRasifalSheetListener.onNewUpdate();
    }


    public interface BaseRasifalSheetListener {
        void onNewUpdate();
    }
}
