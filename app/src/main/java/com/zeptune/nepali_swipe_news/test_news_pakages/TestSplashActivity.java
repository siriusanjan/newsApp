package com.zeptune.nepali_swipe_news.test_news_pakages;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class TestSplashActivity extends AppCompatActivity {
    public static final int SPLASH_TIMEOUT = 100;

    private boolean backPressed, paused;
    private Handler handler;
    private boolean isFirstLaunch = true;

    private Runnable startTask = new Runnable() {
        @Override
        public void run() {
            if (backPressed || paused) return;

            startActivity(new Intent(TestSplashActivity.this, TestMainActivity.class));

            overridePendingTransition(0, 0);
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startActivity(new Intent(TestSplashActivity.this, TestMainActivity.class));

        finish();
        overridePendingTransition(0, 0);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0, 0);
    }
}

