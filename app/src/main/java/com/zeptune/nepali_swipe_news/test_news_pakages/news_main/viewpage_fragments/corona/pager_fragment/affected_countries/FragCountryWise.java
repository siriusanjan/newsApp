package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;
import com.zeptune.nepali_swipe_news.BuildConfig;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.CaseDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseListModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.corona_pager_fragment.BaseFragCoronaWorldData;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.corona_pager_fragment.fra_country_wise_data.FragCountryWiseGraph;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.corona_pager_fragment.frag_corona_world_data.FragWorldCoronaData;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.corona_pager_fragment.CoronaPagerAdapter;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.CoronaNetworkCall;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCaseDbUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCountryWiseDatabaseUtils;

import java.util.ArrayList;

public class FragCountryWise extends Fragment {
    View view;


    Context mContext;
    TabLayout tab_layout_corona;
    ViewPager view_pager_corona;
    AdView ad_corona_data;
    LinearLayout img_back;
    TextView txt_update;
    FragWorldCoronaData fragWorldCoronaData;
    FragCountryWiseGraph fragCountryWiseGraph;
    ArrayList<CountryWiseBarModel> countryWiseBarDeathModelsList;
    ArrayList<CountryWiseBarModel> countryWiseBarCasesModelsList;
    private FrameLayout corona_loading_screen;
    public InterstitialAd mInterstitialAd;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_country_wise, container, false);
        initUI();
        initAd();
        return view;
    }

    public void initAd() {

        if (BuildConfig.DEBUG) {
            // do something for a debug build
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_test_ads));

        } else {
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_real_ads));

        }
        AdRequest request = new AdRequest.Builder().build();
        ad_corona_data.loadAd(request);

    }

    public void initUI() {
        tab_layout_corona = view.findViewById(R.id.tab_layout_corona);
        ad_corona_data = view.findViewById(R.id.ad_corona_data);
        img_back = view.findViewById(R.id.img_back);
        txt_update = view.findViewById(R.id.txt_update);
        corona_loading_screen = view.findViewById(R.id.corona_loading_screen);

        view_pager_corona = view.findViewById(R.id.view_pager_corona);
        fragWorldCoronaData = new FragWorldCoronaData();
        fragCountryWiseGraph = new FragCountryWiseGraph();
        countryWiseBarDeathModelsList = new ArrayList<>();
        countryWiseBarCasesModelsList = new ArrayList<>();
        txt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {

                    corona_loading_screen.setVisibility(View.VISIBLE);
                    updateCoronaData();
                } else {
                    Toast.makeText(mContext, "Network Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable() && mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    getActivity().onBackPressed();
                }
            }
        });


//        setDeathListBar(countryWiseBarDeathModelsList,"New Death By corona in each country");


//        getEntries();
        setUpViewpager();
        setUpIntrestialAd();
        tabListener();

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

    private void tabListener() {

        tab_layout_corona.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                if (!mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
                getActivity().onBackPressed();

            }
        });
    }

    private void setUpViewpager() {
        CoronaPagerAdapter coronaPagerAdapter = new CoronaPagerAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragWorldCoronaData, fragCountryWiseGraph);
        view_pager_corona.setAdapter(coronaPagerAdapter);
        tab_layout_corona.setupWithViewPager(view_pager_corona);
    }





    private void updateCoronaData() {

        CoronaNetworkCall.getContryWiseCoronaState(NewsVariables.NewsOrCoronaTypesEnum.CORONA_COUNTRY_WISE_STATE, new CoroCountryWiseDatabaseUtils.OnDatabaseChangeListener() {
            @Override
            public void onSuccess(CountryWiseListModel countryWiseListModelll) {
                corona_loading_screen.setVisibility(View.GONE);
                if (countryWiseListModelll != null) {

                    fragCountryWiseGraph.setBaseFragCountryWiseData(new BaseFragCoronaWorldData.CoronaWorldDataListener() {
                        @Override
                        public void onWorldCoronaDataUpdated() {
                            Toast.makeText(mContext, "Updated", Toast.LENGTH_SHORT).show();
                            countryWiseBarDeathModelsList = ListUtils.getDeathList();
                            countryWiseBarCasesModelsList = ListUtils.getCasesList();
                            fragCountryWiseGraph.setDeathListBar(countryWiseBarDeathModelsList, "Death By corona in each country"
                            );
                            fragCountryWiseGraph.setCasesListBarSecond(countryWiseBarCasesModelsList, "Cases of corona in each country");
                            fragCountryWiseGraph.notifyRecyclerAdapter(countryWiseListModelll);
                        }
                    });
                }
            }

            @Override
            public void onFail(String errMsg) {
                corona_loading_screen.setVisibility(View.GONE);
                Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();


            }
        });
        CoronaNetworkCall.getWorldCoronaStatus(NewsVariables.NewsOrCoronaTypesEnum.CORONA_WORLD_CASES, new CoroCaseDbUtils.OnDatabaseChangeListener() {
            @Override
            public void onSuccess(CaseDataModel caseDataModel) {
                corona_loading_screen.setVisibility(View.GONE);
                fragWorldCoronaData.setBaseFragWorldCoronaData(new BaseFragCoronaWorldData.CoronaWorldDataListener() {
                    @Override
                    public void onWorldCoronaDataUpdated() {
                        Toast.makeText(mContext, "Updated", Toast.LENGTH_SHORT).show();
                        fragWorldCoronaData.setDataCaseData();
                    }
                });
            }

            @Override
            public void onFail(String errMsg) {
                corona_loading_screen.setVisibility(View.GONE);
                Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();

            }
        });
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
