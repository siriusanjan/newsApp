package com.zeptune.nepali_swipe_news.all_interfaces;

import com.zeptune.nepali_swipe_news.adapter.VerticalViewPagerAdapter;

public interface PagingInterface {
    void pageNumber(String pageNumber, int position, Boolean showFromTop,VerticalViewPagerAdapter verticalViewPagerAdapter,String code);
}
