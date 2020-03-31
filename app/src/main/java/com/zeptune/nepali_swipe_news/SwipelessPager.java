package com.zeptune.nepali_swipe_news;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class SwipelessPager extends ViewPager {

    private Boolean swipeable = true;

    public SwipelessPager(Context context) {
        super(context);
    }

    public SwipelessPager(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swipeable ? super.onInterceptTouchEvent(event) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swipeable ? super.onTouchEvent(event) : false;
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return swipeable ? super.canScrollHorizontally(direction) : false;
    }

    public void setSwipeable(Boolean swipeable){
        //When swipeable = false not work the scroll and when swipeable = true work the scroll
        this.swipeable = swipeable;
    }

    public Boolean getSwipeable() {
        return swipeable;
    }
}