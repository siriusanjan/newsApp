package com.zeptune.nepali_swipe_news.swipe_listner;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class SwipeForWebView implements View.OnTouchListener {
    private final GestureDetector gestureDetector;
    SwipeForWebView.GestureListener swipeForWebView;

    public SwipeForWebView(Context context) {
        swipeForWebView = new SwipeForWebView.GestureListener();
        gestureDetector = new GestureDetector(context, swipeForWebView);
    }

    public void onSwipeLeft() {
    }

    public void onSwipeRight() {
    }

    public void onSwipeDown() {

    }

    public void onSwipeUp() {

    }

    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0) {
                    Log.d("swipingright", "onFling:right ");
                    onSwipeRight();
                } else {
                    Log.d("swipingright", "onFling:left ");
                    onSwipeLeft();
                }
                return true;
            } else {
                return true;
            }




        }
    }

}
