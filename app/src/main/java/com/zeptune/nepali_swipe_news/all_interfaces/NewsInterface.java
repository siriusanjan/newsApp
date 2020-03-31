package com.zeptune.nepali_swipe_news.all_interfaces;

public interface NewsInterface {
    void newsLink(String newsLink,int type);
    void newImageLink(String imageLink);
    void swipingDown(Boolean hideLayout);
    void onRightSwipe(Boolean rightSwipe);
    void verticalViewpagerPosition(int position);
}
