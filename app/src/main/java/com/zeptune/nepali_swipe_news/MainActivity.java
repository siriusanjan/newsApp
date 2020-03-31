package com.zeptune.nepali_swipe_news;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.zeptune.nepali_swipe_news.adapter.TestMAinAdapater;
import com.zeptune.nepali_swipe_news.adapter.VerticalViewPagerAdapter;
import com.zeptune.nepali_swipe_news.all_interfaces.ClickCallBack;
import com.zeptune.nepali_swipe_news.all_interfaces.InterfaceFragmentToogle;
import com.zeptune.nepali_swipe_news.all_interfaces.PagingInterface;
import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_detail_fragment.NewsDetailFragment;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsUpdateInterface;
import com.zeptune.nepali_swipe_news.all_interfaces.ParentAdapeterNotofier;
import com.zeptune.nepali_swipe_news.retrofit.retofit.ServiceFactory;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.News_Variabls;
import com.zeptune.nepali_swipe_news.share_preferance.SharePreferenceValues;
import com.zeptune.nepali_swipe_news.sqlite.DatabaseHandler;
import com.zeptune.nepali_swipe_news.utils.GetJsonTobyte;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.zeptune.nepali_swipe_news.utils.my_interface.AnimationInterface;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NewsInterface, View.OnClickListener, PagingInterface, ParentAdapeterNotofier, InterfaceFragmentToogle, ClickCallBack {

    //remote config
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseRemoteConfigSettings configSettings;


    //Variables
    private String mynewsLink;
    private Boolean scrollOnTransi;
    private boolean letsEnd = false;
    private long cacheExpiration = 3600L;
    private int page = 0;
    private int subPageposition;
    private int a = 0;
    private int pageViewChecker = 0;
    private int currentVVPosition = 0;
    public static String MY_PREFS_NAME = "myPrefString";
    private static String MY_PREFS_DATA_NAME = "mydata";
    private static final String TAG = MainActivity.class.getSimpleName();

    //Views
    private MotionLayout motionLayout;
    private ImageView img_gototop;
    private ImageView img_refreshNews;
    private ImageView imgSetting, navigateMenu, imgTowardsMainNews;
    private ViewPager parentViewPager;
    private TabLayout parentTablayout;
    private AppBarLayout appBarLayout;

    //Class
    private SharePreferenceValues sharePreferenceValues;
    DatabaseHandler databaseHandler;
    DataumListModel returnedDAtalistModel;
    ParentAdapeterNotofier parentAdapeterNotofier;
    FragmentManager fragmentManager;
    Bundle bundle = new Bundle();
    NewsDetailFragment newsDetailFragment;
    Fragment fragment;
    TestMAinAdapater parentViewPagerAdapter;
    //interface
    private AnimationInterface animationInterface;
    NewsInterface newsInterface;
    PagingInterface pagingInterface;
    NewsUpdateInterface newsUpdateInterface;


    public void setSendData(NewsUpdateInterface newsUpdateInterface, AnimationInterface animationInterface) {     //create setter for interface

        this.newsUpdateInterface = newsUpdateInterface;
        this.animationInterface = animationInterface;
    }

    public void setNewsLink(NewsInterface newsInterface) {     //create setter for interface
        this.newsInterface = newsInterface;
    }

    public void sendMyPageNumber(PagingInterface pagingInterface, ParentAdapeterNotofier parentAdapeterNotofier) {     //create setter for interface

        this.pagingInterface = pagingInterface;
        this.parentAdapeterNotofier = parentAdapeterNotofier;
    }

    //remote cache expiration;
    public long getCacheExpiration() {
        return cacheExpiration;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);


        setStatusBarColor();
        initui();
        initObj();

        initRemoteConfig();


        imgSetting.setVisibility(View.GONE);

        if (motionLayout.getCurrentState() == -1) {
            motionLayout.setTransition(R.id.expanded, R.id.collapsed);
            motionLayout.transitionToEnd();
            imgSetting.setVisibility(View.GONE);
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        Log.d("mycurrentState", "run: " + motionLayout.getCurrentState());
        splashScreen();
        setUpViewPager();
        setParentViewPagerCallBack();
        setClicklistener();
        getViewPagerPosition();

    }



    private void getViewPagerPosition() {
//        parentViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onViewPagerPageSelected(int position) {
//                super.onViewPagerPageSelected(position);
////                switch (position) {
////                    case 0:
////                        parentTablayout.getTabAt(0);
////                        break;
////                    case 1:
////                        parentTablayout.getTabAt(1);
////                        break;
////                    case 2:
////                        parentTablayout.getTabAt(2);
////                        break;
////
////                }
//            }
//        });
    }

    private void splashScreen() {
        final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_splash);
        ImageView imageView = dialog.findViewById(R.id.splashLog);
        imageView.setBackground(getResources().getDrawable(R.drawable.appicon));
        dialog.setCancelable(true);
        dialog.show();

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                {
                    dialog.dismiss();
                }
            }
        };
        handler.postDelayed(runnable, 6000);

    }

    private void setUpViewPager() {

        parentViewPagerAdapter = new TestMAinAdapater(getSupportFragmentManager(), 0, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        parentViewPager.setAdapter(parentViewPagerAdapter);
        parentTablayout.setTabIndicatorFullWidth(false);
//        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(parentTablayout, parentViewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(TabLayout.Tab tab, int position) {
//                if (position == 0) {
//                    tab.setText("Peek");
//
//                } else if (position == 1) {
//                    tab.setText("News");
//                }
//            }
//        });


//        tabLayoutMediator.attach();
//        parentTablayout.setScrollX(parentTablayout.getWidth());
//        parentTablayout.getTabAt(1).select();
//        ((LinearLayout) parentTablayout.getTabAt(2).view).setVisibility(View.GONE);

//        parentTablayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()) {
//
//                    case 0:
//                        if (motionLayout.getCurrentState() == R.id.touchTohide) {
//                            motionLayout.setTransition(R.id.touchTohide, R.id.expanded);
//                            motionLayout.setTransitionDuration(250);
//                            motionLayout.transitionToEnd();
//                            imgSetting.setVisibility(View.VISIBLE);
//                        } else if (motionLayout.getCurrentState() == R.id.collapsed) {
//                            motionLayout.setTransition(R.id.collapsed, R.id.expanded);
//                            motionLayout.setTransitionDuration(250);
//                            motionLayout.transitionToEnd();
//                            imgSetting.setVisibility(View.GONE);
//                        } else if (motionLayout.getCurrentState() == R.id.showtap) {
//                            motionLayout.setTransition(R.id.showtap, R.id.expanded);
//                            motionLayout.setTransitionDuration(250);
//                            motionLayout.transitionToEnd();
//                            imgSetting.setVisibility(View.VISIBLE);
//                        }
//                        break;
//                    case 1:
////                        Log.d(TAG, "onTabSelected: "+currentVVPosition);
////                        new Handler().postDelayed(new Runnable() {
////                            @Override
////                            public void run() {
//                        if (currentVVPosition != 0) {
//                            if (motionLayout.getCurrentState() == R.id.touchTohide) {
//                                motionLayout.setTransition(R.id.touchTohide, R.id.showtap);
//                                motionLayout.setTransitionDuration(250);
//                                motionLayout.transitionToEnd();
//                                imgSetting.setVisibility(View.GONE);
//                            } else if (motionLayout.getCurrentState() == R.id.expanded) {
//                                motionLayout.setTransition(R.id.expanded, R.id.showtap);
//                                motionLayout.setTransitionDuration(250);
//                                motionLayout.transitionToEnd();
//                                imgSetting.setVisibility(View.GONE);
//                            }
//                        } else {
//
//                            motionLayout.setTransition(R.id.touchTohide, R.id.collapsed);
//                            motionLayout.setTransitionDuration(250);
//                            motionLayout.transitionToEnd();
//                            imgSetting.setVisibility(View.GONE);
//
//                        }
//
//                        //Do something after 100ms
//
////                            }
////                        }, 300);
//
//                        break;
//                    case 2:
////                        new Handler().postDelayed(new Runnable() {
////                            @Override
////                            public void run() {
//                        if (motionLayout.getCurrentState() == R.id.showtap) {
//                            motionLayout.setTransition(R.id.showtap, R.id.touchTohide);
//                            motionLayout.setTransitionDuration(250);
//                            motionLayout.transitionToEnd();
//                            imgSetting.setVisibility(View.GONE);
//                        }
//                        if (motionLayout.getCurrentState() == R.id.collapsed) {
//                            motionLayout.setTransition(R.id.collapsed, R.id.touchTohide);
//                            motionLayout.setTransitionDuration(250);
//                            motionLayout.transitionToEnd();
//                            imgSetting.setVisibility(View.GONE);
//                        }
//
////                            }
////                        }, 200);
//
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    private void setParentViewPagerCallBack() {
//        parentViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onViewPagerPageSelected(int position) {
//                if (position == 0) {
//                    sharePreferenceValues.saveBooleanToPreference(getApplicationContext(), sharePreferenceValues.CATEGORY_SWIPE, false, sharePreferenceValues.SWIPE_PREFERENCE);
//                    if (animationInterface != null) {
//                        animationInterface.removeLeftAnimation(true);
//                    }
//                }
//                if (position == 2) {
//                    sharePreferenceValues.saveBooleanToPreference(getApplicationContext(), sharePreferenceValues.NEWS_DETAIL_SWIPE, false, sharePreferenceValues.SWIPE_PREFERENCE);
//                    if (animationInterface != null) {
//                        animationInterface.removeRightAnimation(true);
//                    }
//                }
//            }
//        });
    }

    private void setStatusBarColor() {
        Window window = getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT > 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        }
    }

    private void setClicklistener() {
        navigateMenu.setOnClickListener(this);
        imgTowardsMainNews.setOnClickListener(this);
        img_refreshNews.setOnClickListener(this);
        img_gototop.setOnClickListener(this);

    }

    private void initui() {
        imgSetting = findViewById(R.id.imgSetting);
        navigateMenu = findViewById(R.id.imgMenu);
        img_gototop = findViewById(R.id.img_gototop);
        img_refreshNews = findViewById(R.id.img_refreshNews);
        imgTowardsMainNews = findViewById(R.id.imgTowardsNews);
        motionLayout = findViewById(R.id.motionLayoutMainNews);
        parentViewPager = findViewById(R.id.parentViewPager);
        parentTablayout = findViewById(R.id.parentTablayout);
    }

    private void initRemoteConfig() {

        configSettings = new FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(3600L).build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        mFirebaseRemoteConfig.fetch(getCacheExpiration()).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mFirebaseRemoteConfig.fetchAndActivate();
                } else {
                    Log.d("FireBaseConfig", "error in intial");
                }
                String serverUrl = mFirebaseRemoteConfig.getString(getString(R.string.firebase_server_key));
                //set baseurl to static variable
                News_Variabls.BASEURL_CATEGORY = serverUrl;
                System.out.println("server name" + String.valueOf(serverUrl));

            }
        });
    }

    private void initObj() {
        databaseHandler = new DatabaseHandler(this);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        newsDetailFragment = new NewsDetailFragment();
        fragmentManager = getSupportFragmentManager();
        sharePreferenceValues = new SharePreferenceValues();

    }


    @SuppressLint("CommitPrefEdits")
    @Override
    public void newsLink(String newsLink, int type) {
        Log.d("zzzzz", "newsLink: " + newsLink);
        if (newsInterface != null) {
            newsInterface.newsLink(newsLink, 1);
        } else {
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
        }
//        newsInterface.newsLink(newsLink,1);
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("newsLink", newsLink);
        editor.apply();
//            newsInterface.newsLink(newsLink);


    }

    @Override
    public void newImageLink(String imageLink) {

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("imageLink", imageLink);
        editor.apply();

    }


    @Override
    public void swipingDown(Boolean hideLayout) {
        scrollOnTransi = hideLayout;
        Log.d("scrollandchange", "swipingDown: scrolled");
        changePage(hideLayout);
    }

    ;

    @Override
    public void onRightSwipe(Boolean rightSwipe) {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String newslink = prefs.getString("newsLink", "No name defined");//"No name defined" is the default value.
        Log.d("zzzzzz", "onRightSwipe: " + prefs.getString("newsLink", ""));
        Log.d("rightSwipe", "onRightSwipe: ");
        if (rightSwipe) {

            bundle.putString("newslink", prefs.getString("newsLink", ""));
            newsDetailFragment.setArguments(bundle);
            fragment = fragmentManager.findFragmentById(R.id.myFragmentContainer);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack("mainNewDetails");
            if (fragment != null) {
                fragmentTransaction.add(R.id.myFragmentContainer, newsDetailFragment);

                fragmentTransaction.commit();

            } else {
                fragmentTransaction.replace(R.id.myFragmentContainer, newsDetailFragment);
                fragmentTransaction.commit();

            }

        } else {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgTowardsNews:
                parentTablayout.getTabAt(1).select();
                break;
            case R.id.imgMenu:
                parentTablayout.getTabAt(0).select();
                break;
            case R.id.img_refreshNews:
                newsUpdateInterface.updateNews(getLatestNews(), false);

                break;
            case R.id.img_gototop:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        motionLayout.setTransition(R.id.touchTohide, R.id.collapsed);
                        motionLayout.setTransitionDuration(200);
                        motionLayout.transitionToEnd();
                        imgSetting.setVisibility(View.GONE);
                    }
                }, 100);

                try {
                    newsUpdateInterface.updateNews(GetJsonTobyte.getDatalisModel(databaseHandler.getAllContacts(0).get(0).getNewsDatumByte()), true);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }

    }


    private DataumListModel getLatestNews() {
        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "",
                "Checking. Please wait...", true);

        dialog.show();
        Call<DataumListModel> latestNewsCall = ServiceFactory.getAllCategory().getLatestNews();
        latestNewsCall.enqueue(new Callback<DataumListModel>() {
            @Override
            public void onResponse(Call<DataumListModel> call, Response<DataumListModel> response) {
                Log.d("ssss", "onResponse: " + call.request().url());

                try {
                    DataumListModel dataumListModel = response.body();
                    DataumListModel fromdataBase = GetJsonTobyte.getDatalisModel(databaseHandler.getAllContacts(0).get(0).getNewsDatumByte());
                    if (TextUtils.equals(fromdataBase.getData().get(0).getTitle().trim(), dataumListModel.getData().get(0).getTitle().trim())) {
                        returnedDAtalistModel = null;
                    } else {
                        returnedDAtalistModel = dataumListModel;
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<DataumListModel> call, Throwable t) {
                Log.d("ssss", "onFailure: " + call.request().url());
                Log.d("ssss", "onFailure: " + t.getMessage());
                Log.d("ssss", "onFailure: " + t.getLocalizedMessage());
                Log.d("ssss", "onFailure: " + t.getCause());
                dialog.dismiss();

            }
        });
        return returnedDAtalistModel;
    }


    public void changePage(Boolean down) {
        if (down) {
            Log.d("layoutstate", "changePage: " + motionLayout.getCurrentState());
            if (motionLayout.getCurrentState() != R.id.touchTohide) {
                motionLayout.setTransition(R.id.showtap, R.id.touchTohide);
                motionLayout.setTransitionDuration(100);
                motionLayout.transitionToEnd();
                imgSetting.setVisibility(View.GONE);
            }

        } else {
            Log.d("layoutstate", "changePage: " + motionLayout.getCurrentState());
            if (motionLayout.getCurrentState() != R.id.showtap) {
                motionLayout.setTransition(R.id.touchTohide, R.id.showtap);
                motionLayout.setTransitionDuration(100);
                motionLayout.transitionToEnd();
                imgSetting.setVisibility(View.GONE);
            }


        }

    }


    @Override
    public void pageNumber(String pageNumber, int position, Boolean showFromTop, VerticalViewPagerAdapter verticalViewPagerAdapter, String code) {
        pagingInterface.pageNumber(pageNumber, subPageposition, true, verticalViewPagerAdapter, code);

    }

    @Override
    public void notiyUpadate(boolean update) {
        if (parentAdapeterNotofier != null) {
            parentAdapeterNotofier.notiyUpadate(update);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void toogleFragment(int fragmentPosition) {
        parentTablayout.getTabAt(fragmentPosition).select();
        parentViewPager.setCurrentItem(fragmentPosition);
    }

    @Override
    public void onClickSeeMore(String newsLink) {
        parentTablayout.getTabAt(2).select();
    }

    @Override
    public void verticalViewpagerPosition(int position) {
        currentVVPosition = position;
    }

    @Override
    public void onBackPressed() {
        if (parentViewPager.getCurrentItem() == 2) {
            parentViewPager.setCurrentItem(1);
        } else if (parentViewPager.getCurrentItem() == 0) {
            parentViewPager.setCurrentItem(1);
        } else if (parentViewPager.getCurrentItem() == 1) {
            if (!letsEnd) {
                Toast.makeText(MainActivity.this, "Press again to exit", Toast.LENGTH_SHORT).show();
                letsEnd = true;
            } else {
                super.onBackPressed();
            }


        }
    }
}
