package com.zeptune.nepali_swipe_news.parentview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;

import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;
import com.google.android.material.appbar.AppBarLayout;

public class VerticalViewPager extends ViewPager {

    float x = 0;
    float mStartDragX = 0;
    boolean touch =false;
    private static final float SWIPE_X_MIN_THRESHOLD = 50; // Decide this magical nuber as per your requirement
    AppBarLayout appBarLayout;
    Boolean hide = true;
    NewsInterface newsInterface;

    public VerticalViewPager(Context context) {
        super(context);
        newsInterface=(NewsInterface) context;
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        newsInterface=(NewsInterface) context;
        init();
    }



    private void init() {
        // The majority of the magic happens here
        setPageTransformer(true, new VerticalPageTransformer());
        // The easiest way to get rid of the overscroll drawing that happens on the left and right
        setOverScrollMode(OVER_SCROLL_NEVER);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (getAdapter() != null) {
            if (getCurrentItem() >= 0 || getCurrentItem() < getAdapter().getCount()) {
                swapXY(event);
                final int action = event.getAction();
                switch (action & MotionEventCompat.ACTION_MASK) {
                    case MotionEvent.ACTION_MOVE:

                        break;

                    case MotionEvent.ACTION_UP:
                        mStartDragX = event.getX();
                        if (x < mStartDragX
                                && (mStartDragX - x > SWIPE_X_MIN_THRESHOLD)
                                && getCurrentItem() > 0) {
                            Log.i("VerticalViewPager", "down " + x + " : " + mStartDragX + " : " + (mStartDragX - x));

                            setCurrentItem(getCurrentItem() - 1, true);
                            newsInterface.swipingDown(false);
                            return true;
                        } else if (x > mStartDragX
                                && (x - mStartDragX > SWIPE_X_MIN_THRESHOLD)
                                && getCurrentItem() < getAdapter().getCount() - 1) {
                            Log.i("VerticalViewPager", "up " + x + " : " + mStartDragX + " : " + (x - mStartDragX));
                            mStartDragX = 0;
                            setCurrentItem(getCurrentItem() + 1, true);
                            newsInterface.swipingDown(true);
                            return true;
                        }else {
                            if(touch){
                                newsInterface.swipingDown(false);
                                touch=false;
                            }else {
                                newsInterface.swipingDown(true);
                                touch=true;
                            }

                        }
                        break;
                }
            } else {
                mStartDragX = 0;
            }
            swapXY(event);
            return super.onTouchEvent(swapXY(event));
        }
        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(event));

        switch (event.getAction() & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                x = event.getX();
                break;

        }
        swapXY(event); // return touch coordinates to original reference frame for any child views
        return intercepted;
    }




    /**
     * Swaps the X and Y coordinates of your touch event.
     */
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    private class VerticalPageTransformer implements PageTransformer {
        private float MIN_SCALE = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);


            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                //view.setTranslationX(1);
                view.setScaleX(1);
                view.setScaleY(1);
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);
                view.setTranslationX(-1 * view.getWidth() * position);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                view.setTranslationX(-1 * view.getWidth() * position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
        }
}


