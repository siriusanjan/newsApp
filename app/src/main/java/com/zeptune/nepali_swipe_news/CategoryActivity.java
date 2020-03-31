package com.zeptune.nepali_swipe_news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zeptune.nepali_swipe_news.adapter.VerticalViewPagerAdapter;
import com.zeptune.nepali_swipe_news.all_interfaces.ClickCallBack;
import com.zeptune.nepali_swipe_news.all_interfaces.InterfaceFragmentToogle;
import com.zeptune.nepali_swipe_news.all_interfaces.PagingInterface;
import com.zeptune.nepali_swipe_news.all_interfaces.ParentAdapeterNotofier;
import com.zeptune.nepali_swipe_news.models.CategoryDataModel;
import com.zeptune.nepali_swipe_news.adapter.inUse.ParentViewPagerAdapter;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;
import com.google.android.material.tabs.TabLayout;

import static com.zeptune.nepali_swipe_news.MainActivity.MY_PREFS_NAME;

public class CategoryActivity extends AppCompatActivity implements NewsInterface, View.OnClickListener, PagingInterface, ParentAdapeterNotofier, InterfaceFragmentToogle, ClickCallBack {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment categoryFragmentCotainger;
    TabLayout categoryTabLayour;
    ViewPager2 categoryViewpager;
    ParentViewPagerAdapter parentViewPagerAdapter;
    NewsInterface newsInterface;
    Toolbar category_toolbar;
    PagingInterface pagingInterface;
    int verticalViewpagerPosition = 0;

    public void setNewsData(NewsInterface sendData) {     //create setter for interface
        this.newsInterface = sendData;
    }

    public void sendMyCategoryPageNumber(PagingInterface pagingInterface) {     //create setter for interface

        this.pagingInterface = pagingInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryViewpager = findViewById(R.id.categoryViewager);
        category_toolbar = findViewById(R.id.category_toolbar);
        Intent intent = getIntent();
        CategoryDataModel categoryDataModel = (CategoryDataModel) intent.getSerializableExtra("categoryCode");
//        Log.d("ddddddd", "onCreate: " + categoryDataModel.getCode() + categoryDataModel.getName());
        category_toolbar.setTitle(categoryDataModel.getName());
        category_toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        categoryTabLayour = findViewById(R.id.categoryTabLayour);
        category_toolbar.setBackgroundColor(getResources().getColor(R.color.lightbue));
        category_toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_navigate_before_black_24dp));
        setSupportActionBar(category_toolbar);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT > 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.lightbue));
        }
        category_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        parentViewPagerAdapter = new ParentViewPagerAdapter(this, 1, categoryDataModel.getCode());
        categoryViewpager.setAdapter(parentViewPagerAdapter);
        viewpagerCallBack();


    }

    private void viewpagerCallBack() {
        categoryViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        category_toolbar.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        category_toolbar.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    @Override
    public void newsLink(String newsLink, int type) {
        if (newsInterface != null) {
            newsInterface.newsLink(newsLink, type);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (newsInterface != null) {
                    newsInterface.newsLink(newsLink, 1);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (newsInterface != null) {
                                newsInterface.newsLink(newsLink, 1);
                            }
                        }
                    }, 100);
                }
            }
        }, 100);

        //        newsInterface.newsLink(newsLink,1);
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("newsLink", newsLink);
        editor.apply();

    }

    @Override
    public void newImageLink(String imageLink) {

    }

    @Override
    public void swipingDown(Boolean hideLayout) {
//        if (hideLayout) {
//            category_toolbar.setVisibility(View.GONE);
//        } else {
//            category_toolbar.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onRightSwipe(Boolean rightSwipe) {

    }

    @Override
    public void verticalViewpagerPosition(int position) {
        if (verticalViewpagerPosition > position) {
            if (category_toolbar.getVisibility() == View.GONE) {
                category_toolbar.setVisibility(View.VISIBLE);
            }
        } else if (verticalViewpagerPosition < position) {
            if (category_toolbar.getVisibility() == View.VISIBLE) {
                category_toolbar.setVisibility(View.GONE);

            }
        }
        verticalViewpagerPosition = position;
    }

    @Override
    public void onBackPressed() {
        if (categoryViewpager.getCurrentItem() == 1) {
            categoryViewpager.setCurrentItem(0);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void pageNumber(String pageNumber, int position, Boolean showFromTop, VerticalViewPagerAdapter verticalViewPagerAdapter, String code) {
        pagingInterface.pageNumber(pageNumber, position, true, verticalViewPagerAdapter, code);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onClickSeeMore(String newsLink) {
        categoryViewpager.setCurrentItem(1);
    }

    @Override
    public void toogleFragment(int fragmentPosition) {
        Log.d("toogled", "toogleFragment: ");
        categoryViewpager.setCurrentItem(0);


    }

    @Override
    public void notiyUpadate(boolean update) {

    }

}
