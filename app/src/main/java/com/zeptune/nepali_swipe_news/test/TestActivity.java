package com.zeptune.nepali_swipe_news.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.adapter.VerticalViewPagerAdapter;
import com.zeptune.nepali_swipe_news.all_interfaces.ClickCallBack;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;
import com.zeptune.nepali_swipe_news.all_interfaces.PagingInterface;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_fragments.TestNewsFragment;

public class TestActivity extends AppCompatActivity implements NewsInterface, PagingInterface, ClickCallBack {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        fragmentManager = getSupportFragmentManager();
        TestNewsFragment networkCallerFragment = new TestNewsFragment();
        fragmentManager.beginTransaction().add(R.id.test_container, networkCallerFragment).commit();

    }

    @Override
    public void newsLink(String newsLink, int type) {

    }

    @Override
    public void newImageLink(String imageLink) {

    }

    @Override
    public void swipingDown(Boolean hideLayout) {

    }

    @Override
    public void onRightSwipe(Boolean rightSwipe) {

    }

    @Override
    public void verticalViewpagerPosition(int position) {

    }

    @Override
    public void pageNumber(String pageNumber, int position, Boolean showFromTop, VerticalViewPagerAdapter verticalViewPagerAdapter, String code) {

    }

    @Override
    public void onClickSeeMore(String newsLink) {

    }
}
