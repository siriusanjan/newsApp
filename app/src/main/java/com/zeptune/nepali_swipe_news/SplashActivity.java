package com.zeptune.nepali_swipe_news;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class SplashActivity extends Activity {
    public static final int SPLASH_TIMEOUT = 1000;

    private View rootView;
    private boolean backPressed, paused;

    private Runnable startTask = new Runnable() {
        @Override
        public void run() {
            if (backPressed || paused) return;

            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        rootView = findViewById(R.id.splash_root_);
        new BackgroundSplashTask().execute();
    }

    @Override
    public void onBackPressed() {
        backPressed = true;
        rootView.removeCallbacks(startTask);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        paused = true;
        rootView.removeCallbacks(startTask);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backPressed = false;
        paused = false;
//        rootView.postDelayed(startTask, SPLASH_TIMEOUT);
    }
    private class BackgroundSplashTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Thread.sleep(SPLASH_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    }

