package com.zeptune.nepali_swipe_news.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zeptune.nepali_swipe_news.FragementHoster;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_detail_fragment.NewsDetailFragment;

public class MainAdaoter extends FragmentPagerAdapter {
    public MainAdaoter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragementHoster();
            case 1:
                return new NewsDetailFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
