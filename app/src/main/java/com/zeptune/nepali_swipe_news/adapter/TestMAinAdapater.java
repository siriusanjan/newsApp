package com.zeptune.nepali_swipe_news.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.FeaturesFragment;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;
import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.models.Datum;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_detail_fragment.NewsDetailFragment;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.NewsFragment;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_fragments.TestNewsFragment;

import java.util.ArrayList;

public class TestMAinAdapater extends FragmentStatePagerAdapter {
    private static final int CARD_ITEM_SIZE = 10;
    FragmentActivity fragmentActivity;
    Activity activity;
    int type;

    LayoutInflater mInflater;
    Context mContext;
    int a = 0;
    ArrayList<Integer> linkLoadedList = new ArrayList<>();

    ArrayList<Datum> dataModels = new ArrayList<>();
    //    private final FragmentManager mFragmentManager;
    NewsInterface currrentNewsInterface;
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private static final boolean DEBUG = false;
    Fragment verticalFragment;
    DataumListModel dataumListModel;
    Boolean showFromTop;
    boolean checkNetwork = true;
    int priviousDataSize = 1;
    int currentDataSize;
    String code;
    //class
    TestNewsFragment testNewsFragment;
    NewsDetailFragment newsDetailFragment;
    FeaturesFragment featuresFragment;

    public TestMAinAdapater(@NonNull FragmentManager fragmentActivity, int type,int behavior,TestNewsFragment testNewsFragment,NewsDetailFragment newsDetailFragment,FeaturesFragment featuresFragment) {
        super(fragmentActivity,behavior);
        this.type = type;
        this.testNewsFragment=testNewsFragment;
        this.newsDetailFragment=newsDetailFragment;
        this.featuresFragment=featuresFragment;
    }
    public TestMAinAdapater(@NonNull FragmentManager fragmentActivity, int type,int behavior) {
        super(fragmentActivity,behavior);
        this.type = type;
        this.testNewsFragment=testNewsFragment;
    }

    @Override
    public int getCount() {
        if (type == 0)
            return 3;
        else {
            return 2;
        }    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("adaptercall", "createFragment: " + position);

        switch (type) {

            case 0:
                switch (position) {
                    case 0:
                        return featuresFragment;
                    case 1:
                        return testNewsFragment;
                    case 2:
                        return newsDetailFragment;


                }
                break;
            case 1:
                switch (position){
                    case 0:
                        return NewsFragment.newInstance(2,code);
                    case 1:
                        return new NewsDetailFragment();

                }

        }


        return null;
    }
}

