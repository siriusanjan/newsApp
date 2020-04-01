package com.zeptune.nepali_swipe_news.test_news_pakages.NewsHoster;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.zeptune.nepali_swipe_news.BuildConfig;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.adapter.TestMAinAdapater;
import com.zeptune.nepali_swipe_news.date_converter.converter.ConvertAdToBs;
import com.zeptune.nepali_swipe_news.date_converter.interface_date.NepaliDateInterface;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DateModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.FeaturesFragment;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.BaseFeaturesFragment;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_detail_fragment.BaseNewsDetailFragment;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_fragments.FragBaseViewPager;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_detail_fragment.NewsDetailFragment;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_fragments.TestNewsFragment;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.DateDatabaseUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewsHoster extends BaseNewsHoster {
    private View view;
    private ViewPager viewPagerMain;
    private TestNewsFragment testNewsFragment;
    private NewsDetailFragment newsDetailFragment;
    private FeaturesFragment featuresFragment;
    private DateFormat dayFormat, monthFormat;
    private Date date;
    private ConvertAdToBs convertAdToBs;
    private TestMAinAdapater testMAinAdapater;
    private FrameLayout features_loading_screen;
    private InterstitialAd mInterstitialAd;
    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_hoster, container, false);
        initObj();
        return view;
    }

    private void initObj() {

        viewPagerMain = view.findViewById(R.id.viewPagerMain);
        features_loading_screen = view.findViewById(R.id.features_loading_screen);
        convertAdToBs = new ConvertAdToBs();
        testNewsFragment = new TestNewsFragment();
        newsDetailFragment = new NewsDetailFragment();
        featuresFragment = new FeaturesFragment();
        date = new Date();
        dayFormat = new SimpleDateFormat("dd");
        monthFormat = new SimpleDateFormat("MM");
//        setTodaydate();
        setUpViepager();
        setTestNewFragmentListener();
        setViewpagerListener();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void setUpViewPagerBase(NewsHosterListeners newsHosterListeners) {
        super.setNewsHosterListeners(newsHosterListeners);
    }

    private void setViewpagerListener() {
        viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                NewsHoster.super.performbackPress(position);
                if (position == 2) {
//                    setUpIntrestialAd();
                    testNewsFragment.setSwipeToLeftUI(false);
                    newsDetailFragment.loadUrl();
                } else if (position == 1) {
//                    if (mInterstitialAd != null) {
//                        if (mInterstitialAd.isLoaded()) {
//                            mInterstitialAd.show();
//                        }
//                    }
                    testNewsFragment.showSwipeUpGuide();
                    newsDetailFragment.clearWebview();
                } else {
                    newsDetailFragment.clearWebview();

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setUpIntrestialAd() {
        mInterstitialAd = new InterstitialAd(mContext);
        if (BuildConfig.DEBUG) {

            mInterstitialAd.setAdUnitId(mContext.getResources().getString(R.string.inteterstial_test_ads));
        } else {
            mInterstitialAd.setAdUnitId(mContext.getResources().getString(R.string.inteterstial_real_ads));

        }
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        loadIntrestialAds();

    }


    private void loadIntrestialAds() {
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                viewPagerMain.setCurrentItem(1);
            }
        });
    }

    private void setTodaydate() {
        ConvertAdToBs convertAdToBs = new ConvertAdToBs(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), monthFormat.format(date), dayFormat.format(date), new NepaliDateInterface() {
            @Override
            public void setConvertedDate(String nepYear, String nepMonth, String nepNumberOfMonth, String nepDay, String dayOfWeek, boolean isNepali) {
                FeaturesVariables.FeaturesTypesEnum featuresTypesEnum = FeaturesVariables.FeaturesTypesEnum.FEATURES_DATE;
                DateModel dateModel = new DateModel();
                dateModel.year = nepYear;
                dateModel.month = nepMonth;
                dateModel.dayOfTheMonth = nepDay;
                dateModel.dayOFWeek = dayOfWeek;
                if (DateDatabaseUtils.isSavedModel(featuresTypesEnum) == DateDatabaseUtils.CheckDateDataStatus.ALREADY_SAVED) {
                    DateDatabaseUtils.deleteGoldDatabase(featuresTypesEnum);
                    DateDatabaseUtils.addNewNewsData(dateModel, featuresTypesEnum);
                } else {
                    DateDatabaseUtils.addNewNewsData(dateModel, featuresTypesEnum);
                }
            }
        });
    }

    private void setTestNewFragmentListener() {
        testNewsFragment.setUpParent(new FragBaseViewPager.OnViewpagerPageChangeListner() {
            @Override
            public void onPageChange() {

            }

            @Override
            public void onPageTurnUp() {

            }

            @Override
            public void onPageTurnDown() {

            }

            @Override
            public void onPageSelected(String newsLink, int pagePosition) {
                Log.d("mynewsLink", "onPageSelected: " + newsLink);
                newsDetailFragment.setUrl(newsLink);
            }

            @Override
            public void onClickNavFeatures() {
                viewPagerMain.setCurrentItem(0);
            }
        });

        newsDetailFragment.setNdfListener(new BaseNewsDetailFragment.NDFListener() {
            @Override
            public void onBackPress() {
                viewPagerMain.setCurrentItem(1);
            }
        });
        featuresFragment.setFeaturesFramentListener(new BaseFeaturesFragment.FeaturesFragmentListener() {
            @Override
            public void onNewsNavPress() {
                viewPagerMain.setCurrentItem(1);
            }

            @Override
            public void showDialog() {
                features_loading_screen.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideDialog() {
                features_loading_screen.setVisibility(View.GONE);
            }
        });
    }

    private void setUpViepager() {
        testMAinAdapater = new TestMAinAdapater(getActivity().getSupportFragmentManager(), 0, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, testNewsFragment, newsDetailFragment, featuresFragment);
        viewPagerMain.setAdapter(testMAinAdapater);
        viewPagerMain.setOffscreenPageLimit(2);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void setPosition(int position) {
        viewPagerMain.setCurrentItem(position);
    }
}
