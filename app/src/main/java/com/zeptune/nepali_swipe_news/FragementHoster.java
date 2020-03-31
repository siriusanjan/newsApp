package com.zeptune.nepali_swipe_news;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.zeptune.nepali_swipe_news.adapter.inUse.ParentViewPagerAdapter;
import com.zeptune.nepali_swipe_news.all_interfaces.PagingInterface;
import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_detail_fragment.NewsDetailFragment;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsUpdateInterface;
import com.zeptune.nepali_swipe_news.all_interfaces.ParentAdapeterNotofier;
import com.zeptune.nepali_swipe_news.retrofit.retofit.ServiceFactory;
import com.zeptune.nepali_swipe_news.sqlite.DatabaseHandler;
import com.zeptune.nepali_swipe_news.utils.GetJsonTobyte;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragementHoster extends Fragment implements View.OnClickListener,NewsInterface {
    public FragementHoster() {
    }
    ParentViewPagerAdapter parentViewPagerAdapter;
    ViewPager2 fragmentHosterViewPager;
    TabLayout parentTablayout;
    NewsUpdateInterface newsUpdateInterface;
    public AppBarLayout appBarLayout;
    ImageView imgSetting, navigateMenu, imgTowardsMainNews;
    String mynewsLink;
    Fragment fragment;
    ImageView img_refreshNews;
    Context mContext;
    NewsDetailFragment newsDetailFragment;
    public static String MY_PREFS_NAME = "myPrefString";
    private static String MY_PREFS_DATA_NAME = "mydata";
    PagingInterface pagingInterface;
    Boolean scrollOnTransi;
    int page = 0;
    int subPageposition;
    int a = 0;
    int pageViewChecker = 0;
    MotionLayout motionLayout;
    ImageView img_gototop;

    //remote config
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseRemoteConfigSettings configSettings;
    private long cacheExpiration = 3600L;
    DatabaseHandler databaseHandler;
    DataumListModel returnedDAtalistModel;
    ParentAdapeterNotofier parentAdapeterNotofier;
    FragmentManager fragmentManager;
    Bundle bundle = new Bundle();
    NewsInterface newsInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() instanceof MainActivity) {
//            ((MainActivity) getActivity()).instanceFromFragmentHoster(this);
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoster, container, false);
        initUi(view);
        initObj();
        setClicklistener();
        imgSetting.setVisibility(View.GONE);

        if (motionLayout.getCurrentState() == -1) {
            motionLayout.setTransition(R.id.expanded, R.id.collapsed);
            motionLayout.transitionToEnd();
            imgSetting.setVisibility(View.GONE);
        }
        setUpViewPager();
        return view;
    }

    private void initUi(View view) {
        imgSetting = view.findViewById(R.id.imgSetting);
        navigateMenu = view.findViewById(R.id.imgMenu);
        img_gototop = view.findViewById(R.id.img_gototop);
        img_refreshNews = view.findViewById(R.id.img_refreshNews);
        imgTowardsMainNews =view. findViewById(R.id.imgTowardsNews);
        motionLayout = view.findViewById(R.id.motionLayoutMainNews);
        parentTablayout = view.findViewById(R.id.parentTablayout);
        fragmentHosterViewPager = view.findViewById(R.id.fragmentHosterViewPager);
    }

    private void setClicklistener() {
        navigateMenu.setOnClickListener(this);
        imgTowardsMainNews.setOnClickListener(this);
        img_refreshNews.setOnClickListener(this);
        img_gototop.setOnClickListener(this);

    }
    private void initObj(){

    }
    private void setUpViewPager() {
        if((FragmentActivity) getActivity()!=null) {
            Log.d("activitynotnull", "setUpViewPager: ");
            parentViewPagerAdapter = new ParentViewPagerAdapter(this.getActivity(), 1);
        }
        fragmentHosterViewPager.setAdapter(parentViewPagerAdapter);
        parentTablayout.setTabIndicatorFullWidth(false);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(parentTablayout, fragmentHosterViewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText("Peek");

                } else if (position == 1) {
                    tab.setText("News");
                }
            }
        });
        tabLayoutMediator.attach();
//        parentTablayout.setScrollX(parentTablayout.getWidth());
        parentTablayout.getTabAt(1).select();
        parentTablayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {

                    case 0:


                        if (motionLayout.getCurrentState() == R.id.touchTohide) {
                            motionLayout.setTransition(R.id.touchTohide, R.id.expanded);
                            motionLayout.setTransitionDuration(250);
                            motionLayout.transitionToStart();
                            imgSetting.setVisibility(View.VISIBLE);
                        } else if (motionLayout.getCurrentState() == R.id.collapsed) {
                            motionLayout.setTransition(R.id.collapsed, R.id.expanded);
                            motionLayout.setTransitionDuration(250);
                            motionLayout.transitionToStart();
                            imgSetting.setVisibility(View.GONE);
                        } else if (motionLayout.getCurrentState() == R.id.showtap) {
                            motionLayout.setTransition(R.id.showtap, R.id.expanded);
                            motionLayout.setTransitionDuration(250);
                            motionLayout.transitionToStart();
                            imgSetting.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 1:

                        //Do something after 100ms
                        motionLayout.setTransition(R.id.expanded, R.id.collapsed);
                        motionLayout.setTransitionDuration(250);
                        motionLayout.transitionToStart();
                        imgSetting.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

                try {
                    newsUpdateInterface.updateNews(GetJsonTobyte.getDatalisModel(databaseHandler.getAllContacts(0).get(0).getNewsDatumByte()), true);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                motionLayout.setTransition(R.id.touchTohide, R.id.collapsed);
                motionLayout.setTransitionDuration(400);
                motionLayout.transitionToEnd();
                imgSetting.setVisibility(View.GONE);
                break;
        }

    }

    private DataumListModel getLatestNews() {
        final ProgressDialog dialog = ProgressDialog.show(mContext, "",
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
        if (scrollOnTransi != null) {
            Log.d("scrollandchange", "swipingDown: scrolled" + page);

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext=context;
    }


    @Override
    public void newsLink(String newsLink, int type) {

    }

    @Override
    public void newImageLink(String imageLink) {

    }

    @Override
    public void swipingDown(Boolean hideLayout) {
        scrollOnTransi = hideLayout;
        changePage(hideLayout);
    }

    @Override
    public void onRightSwipe(Boolean rightSwipe) {

    }

    @Override
    public void verticalViewpagerPosition(int position) {

    }
}
