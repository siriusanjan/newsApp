package com.zeptune.nepali_swipe_news.test_news_pakages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.FragCountryWise;

public class CoronaActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragCountryWise fragCountryWise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona);
        fragmentManager = getSupportFragmentManager();

        fragCountryWise = new FragCountryWise();

        barCharFragment();
        changeStatusBarColor();
    }

    public void changeStatusBarColor() {
        Window window = getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT > 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        }


    }

    private void barCharFragment() {
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.container_test_main_activity, fragCountryWise).commit();

    }

    @Override
    public void onBackPressed() {
        if (fragCountryWise.mInterstitialAd != null) {
            if (fragCountryWise.mInterstitialAd.isLoaded()) {
                fragCountryWise.mInterstitialAd.show();
            } else {
                super.onBackPressed();

            }
        } else {
            super.onBackPressed();
        }
    }
}
