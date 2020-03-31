package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items;

import androidx.fragment.app.Fragment;

public class BaseFeaturesFragment extends Fragment {
    private FeaturesFragmentListener featuresFragmentListener;
    public void setFeaturesFramentListener(FeaturesFragmentListener featuresFramentListener){
        this.featuresFragmentListener=featuresFramentListener;
    }
    public void performNewsNavPress(){
        featuresFragmentListener.onNewsNavPress();
    }
    public void performHideDialog(){featuresFragmentListener.hideDialog();}
    public void performShowDialog(){featuresFragmentListener.showDialog();}
    public interface FeaturesFragmentListener{
        void onNewsNavPress();
        void showDialog();
        void hideDialog();
    }
}
