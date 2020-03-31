package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.zeptune.nepali_swipe_news.Animation.ViewAnimation;
import com.zeptune.nepali_swipe_news.Animation.ZoomOutPageTransformer;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.adapter.category.CategoryListAdapter;
import com.zeptune.nepali_swipe_news.adapter.inUse.Adapter;
import com.zeptune.nepali_swipe_news.adapter.inUse.TestVerticalAdapter;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;
import com.zeptune.nepali_swipe_news.all_interfaces.PagingInterface;
import com.zeptune.nepali_swipe_news.models.CategoryDataModel;
import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.GuideUtils.GuideLinePref;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.DateDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.gson_utils.GesonConverter;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsNetworkCalls;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.TestNewsDatabaseUtils;
import com.zeptune.nepali_swipe_news.share_preferance.SharePreferenceValues;
import com.zeptune.nepali_swipe_news.sqlite.DatabaseHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class TestNewsFragment extends FragBaseViewPager implements View.OnClickListener {

    //variables
    private ArrayList<CategoryDataModel> arrayList;


    private int previousPagePosition = 0;

    //views
    private ViewPager2 verticalViewPager;
    private RecyclerView category_list_onMainView;
    private TextView txt_nav_features, txt_title_news, txt_guide;
    private AdView adViewNewFragment;
    private View view;
    private LinearLayout news_err_layout;
    private TextView txt_err_message, txt_loading_data;
    private Button btn_Reload;
    private ProgressBar loadingProgressBar;
    private ConstraintLayout main_layout_news_display;
    private FrameLayout layout_update;
    private ImageView img_update, img_go_top, img_animation, img_animation_centre;
    private MotionLayout layout_motion_news;
    private LinearLayout layout_news_frg_feat;
    //    private SwipeRefreshLayout swipe_to_refreshViewPager;
    private TextView displayTodayDay, displayTodayMonth, displayTodayYear, displayTodayDayOfWeek;
    private boolean isDataChangedCalling = false;


    //class
    private Context mContext;
    private NewsVariables.NewsOrCoronaTypesEnum selectedNewsTypeEnum = NewsVariables.NewsOrCoronaTypesEnum.NEWS_All;
    private SharePreferenceValues sharePreferenceValues;
    private DataumListModel dataumListModel;
    private DataumListModel finalListForAdapterCall;
    private DataumListModel categoryDatalistModel;
    private DatabaseHandler databaseHandler;
    private TestVerticalAdapter myverticalPagerAdapter, anotherVerticalViewpager;
    private ViewAnimation viewAnimation;
    private Gson gson;
    private RecyclerView.LayoutManager categoryListLayoutManager;

    private DataumListModel listTobePass;
    private Animation animationDownToUp;


    //interface
    private NewsInterface newsInterface;
    private PagingInterface pagingInterface;

    Handler handler = new Handler();


    public TestNewsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initUi();
        initObj();
        return view;
    }

    private void setOnRefreshListener() {
//        swipe_to_refreshViewPager.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                isDataChangedCalling = true;
////                newsArrayList.clear();
////                newsRecyclerAdaptor.notifyDataSetChanged();
//                callToUpdatePagenation();
//            }
//        });
    }

    private void initUi() {
        category_list_onMainView = view.findViewById(R.id.category_list_onMainView);
        verticalViewPager = view.findViewById(R.id.container);
        img_animation = view.findViewById(R.id.img_animation);
        txt_guide = view.findViewById(R.id.txt_guide);
        img_animation_centre = view.findViewById(R.id.img_animation_centre);
        news_err_layout = view.findViewById(R.id.news_err_layout);
        txt_err_message = view.findViewById(R.id.txt_err_message);
        txt_loading_data = view.findViewById(R.id.txt_loading_data);
        btn_Reload = view.findViewById(R.id.btn_Reload);
        txt_title_news = view.findViewById(R.id.txt_title_news);
        txt_nav_features = view.findViewById(R.id.txt_nav_features);
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar);
        layout_update = view.findViewById(R.id.layout_update);
        main_layout_news_display = view.findViewById(R.id.main_layout_news_display);
        layout_motion_news = view.findViewById(R.id.layout_motion_news);
        img_go_top = view.findViewById(R.id.img_go_top);
        img_update = view.findViewById(R.id.img_update);
        layout_news_frg_feat = view.findViewById(R.id.layout_news_frg_feat);
        displayTodayDay = view.findViewById(R.id.displayTodayDay);
        displayTodayDayOfWeek = view.findViewById(R.id.displayTodayDayOfWeek);
        displayTodayMonth = view.findViewById(R.id.displayTodayMonth);
        displayTodayYear = view.findViewById(R.id.displayTodayYear);
        txt_title_news.setOnClickListener(this);
        txt_nav_features.setOnClickListener(this);
        btn_Reload.setOnClickListener(this);
        layout_news_frg_feat.setOnClickListener(this);
        DateDatabaseUtils.setTodayDate(displayTodayYear, displayTodayMonth, displayTodayDay, displayTodayDayOfWeek);
    }

    public void setUpParent(OnViewpagerPageChangeListner onViewpagerPageChangeListner) {
        super.setUpListener(onViewpagerPageChangeListner);
    }

    public void showSwipeUpGuide() {
//    Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.right_to_left_anim);
//    img_animation.startAnimation(animation);
        if (!GuideLinePref.getInstance(mContext).getGuideSwipeForMore() && finalListForAdapterCall != null) {
            if (finalListForAdapterCall.getData() != null) {
                if (finalListForAdapterCall.getData().size() > 0) {
                    setSwipeUpGuideUI(true);
                    animationDownToUp = AnimationUtils.loadAnimation(mContext, R.anim.down_to_up_anim);
                    img_animation_centre.startAnimation(animationDownToUp);
                    GuideLinePref.getInstance(mContext).setGuideSwipeForMore(true);
                    Log.d("myguide", "showSwipeUpGuide: " + GuideLinePref.getInstance(mContext).getGuideSwipeForMore());

                }
            }

        }
    }

    public void showSwipeLeftGuide() {

        if (!GuideLinePref.getInstance(mContext).getGuideSwipToNewsDetail() && finalListForAdapterCall.getData().size() > 0) {
            setSwipeToLeftUI(true);
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.right_to_left_anim);
            img_animation.startAnimation(animation);
            GuideLinePref.getInstance(mContext).setGuideSwipeToNewsDetail(true);

        }
    }

    public void setSwipeToLeftUI(boolean show) {
        if (show) {
            if (img_animation.getVisibility() == View.GONE) {
                img_animation.setVisibility(View.VISIBLE);
            }
            if (img_animation_centre.getVisibility() == View.VISIBLE) {
                img_animation_centre.setVisibility(View.GONE);
            }
            if (txt_guide.getVisibility() == View.GONE) {
                txt_guide.setVisibility(View.VISIBLE);
            }
            txt_guide.setText("Swipe Left For News Detail");
        } else {
            img_animation.clearAnimation();
            if (img_animation.getVisibility() == View.VISIBLE) {
                img_animation.setVisibility(View.GONE);
            }
            if (txt_guide.getVisibility() == View.VISIBLE) {
                txt_guide.setVisibility(View.GONE);
            }
        }
    }

    public void setSwipeUpGuideUI(boolean show) {
        if (show) {
            if (img_animation_centre.getVisibility() == View.GONE) {
                img_animation_centre.setVisibility(View.VISIBLE);
            }
            if (img_animation.getVisibility() == View.VISIBLE) {
                img_animation.setVisibility(View.GONE);
            }
            if (txt_guide.getVisibility() == View.GONE) {
                txt_guide.setVisibility(View.VISIBLE);
            }
            txt_guide.setText("Keep Swiping Up For More News ");
        } else {
            img_animation_centre.clearAnimation();
            if (img_animation_centre.getVisibility() == View.VISIBLE) {
                img_animation_centre.setVisibility(View.GONE);
            }
            if (txt_guide.getVisibility() == View.VISIBLE) {
                txt_guide.setVisibility(View.GONE);
            }
        }
    }

    private void initObj() {
//        newsInterface = (NewsInterface) mContext;
//        pagingInterface = (PagingInterface) mContext;
        verticalViewPager.setPageTransformer(new ZoomOutPageTransformer());
        databaseHandler = new DatabaseHandler(mContext);
//        viewAnimation = new ViewAnimation();
        sharePreferenceValues = new SharePreferenceValues();
        categoryListLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        finalListForAdapterCall = new DataumListModel();
        setUpVerticalViewPager();
        setUpCategoryList();
    }

    private void setUpCategoryList() {
        arrayList = new ArrayList<>();
        arrayList = getCategoriesList();
        CategoryListAdapter categoryTypeAdapter = new CategoryListAdapter(arrayList, mContext, Adapter.MAIN_CATEGORY_TYPE, new CategoryListAdapter.OnCategoryItemClickListener() {
            @Override
            public void onCategoryItemSelected(String newsTypesEnum) {
                if (!TextUtils.isEmpty(newsTypesEnum)) {
                    previousPagePosition = 0;
                    selectedNewsTypeEnum = myStringEnumHasmap(newsTypesEnum);
                    DataumListModel dataumListModel = TestNewsDatabaseUtils.saveDataListModel(selectedNewsTypeEnum);
                    if (dataumListModel != null) {
                        if (finalListForAdapterCall != null) {
                            finalListForAdapterCall.setMeta(dataumListModel.getMeta());
                            finalListForAdapterCall.setData(dataumListModel.getData());
                        } else {
                            finalListForAdapterCall = dataumListModel;
                        }
                        myverticalPagerAdapter.notifyDataSetChanged();
                        verticalViewPager.setCurrentItem(0, true);
                        txt_err_message.setText("Opps!! No Data");
                        if (news_err_layout.getVisibility() == View.VISIBLE) {
                            news_err_layout.setVisibility(View.GONE);
                        }
                    } else {
                        finalListForAdapterCall.setData(null);
                        finalListForAdapterCall.setMeta(null);
                        finalListForAdapterCall.setNewsType(null);
                        myverticalPagerAdapter.notifyDataSetChanged();
                        txt_err_message.setText("Opps!! No Data");
                        if (news_err_layout.getVisibility() == View.GONE) {
                            news_err_layout.setVisibility(View.VISIBLE);


                        }
                    }
                    if (finalListForAdapterCall != null) {
                        if (finalListForAdapterCall.getData() != null) {
                            if (finalListForAdapterCall.getData().size() > 0) {

                                TestNewsFragment.super.performPageSelection(finalListForAdapterCall.getData().get(0).getFullLink(), 0);
                            }
                        }
                    }


                }

            }
        });
        LinearLayoutManager categoryListLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        category_list_onMainView.setLayoutManager(categoryListLayoutManager);
        category_list_onMainView.setAdapter(categoryTypeAdapter);
        category_list_onMainView.setNestedScrollingEnabled(false);

    }


    private void setUpVerticalViewPager() {
        if (TestNewsDatabaseUtils.saveDataListModel(NewsVariables.NewsOrCoronaTypesEnum.NEWS_All) != null) {
            finalListForAdapterCall = TestNewsDatabaseUtils.saveDataListModel(NewsVariables.NewsOrCoronaTypesEnum.NEWS_All);
        } else {
            news_err_layout.setVisibility(View.VISIBLE);
        }
        myverticalPagerAdapter = new TestVerticalAdapter(finalListForAdapterCall, mContext, null);
        verticalViewPager.setAdapter(myverticalPagerAdapter);
        setUpViewPagerCallBack();


    }

    private void setUpViewPagerCallBack() {
        verticalViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    updateImgUpdateGoToTop(true);
                } else {
                    if (img_go_top.getVisibility() == View.GONE) {
                        updateImgUpdateGoToTop(false);
                    }
                }
                if (position == 1) {
                    if (img_animation_centre.getVisibility() == View.VISIBLE) {
                        setSwipeUpGuideUI(false);
                    }
                }
                if (position == 3) {
                    showSwipeLeftGuide();
                }

                if (previousPagePosition < position) {
                    changeMotionScene(true);
                    previousPagePosition = position;
                } else if (previousPagePosition > position) {
                    changeMotionScene(false);
                    previousPagePosition = position;
                }
                if (finalListForAdapterCall.getData() != null) {
                    if (finalListForAdapterCall.getData().size() > position) {
                        TestNewsFragment.super.performPageSelection(finalListForAdapterCall.getData().get(position).getFullLink(), position);
                        if (position == finalListForAdapterCall.getData().size() - 6) {
                            callPagination();
                        } else if (position == finalListForAdapterCall.getData().size() - 5 && !isDataChangedCalling) {
                            callPagination();
                        } else if (position == finalListForAdapterCall.getData().size() - 4 && !isDataChangedCalling) {
                            callPagination();
                        } else if (position == finalListForAdapterCall.getData().size() - 3 && !isDataChangedCalling) {
                            callPagination();
                        } else if (position == finalListForAdapterCall.getData().size() - 2 && !isDataChangedCalling) {
                            callPagination();
                        } else if (position == finalListForAdapterCall.getData().size() - 1 && !isDataChangedCalling) {
                            callPagination();
                        }
                    }
                }
                if (finalListForAdapterCall != null) {
                    if (finalListForAdapterCall.getData() != null && !isDataChangedCalling) {
                        if (finalListForAdapterCall.getData().size() == position + 1) {

                            if (isNetworkAvailable() && finalListForAdapterCall.getData().size() > 4) {
                                setOnRefreshListener();

                                isDataChangedCalling = true;

                            } else {
                                Snackbar snackbar = Snackbar
                                        .make(main_layout_news_display, "No  Connection", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        }

                    }
                    super.onPageSelected(position);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void callPagination() {
        if (isNetworkAvailable()) {
            if (!isDataChangedCalling) {

                callToUpdatePagenation();
            }

        } else {
            Snackbar snackbar = Snackbar
                    .make(main_layout_news_display, "No  Connection", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    private void callToUpdatePagenation() {
        isDataChangedCalling = true;
        NewsNetworkCalls.getPageWiseNews(String.valueOf(Integer.parseInt(finalListForAdapterCall.getMeta().getCurrent_page().toString()) + 1), selectedNewsTypeEnum, new NewsNetworkCalls.OnNetworkCallInterFace() {
            @Override
            public void onSuccess(DataumListModel dataumListModel, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
                DataumListModel oldDatabaseModel = TestNewsDatabaseUtils.saveDataListModel(newsOrCoronaTypesEnum);
                oldDatabaseModel.getData().addAll(dataumListModel.getData());
                oldDatabaseModel.setMeta(dataumListModel.getMeta());
                String myString = GesonConverter.NewsModelToJson(oldDatabaseModel);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TestNewsDatabaseUtils.updatePaginationDataModelDatabase(myString, newsOrCoronaTypesEnum);
                        DataumListModel testListModel = GesonConverter.NewsJsonModleClass(myString);
                        finalListForAdapterCall.getData().clear();
                        finalListForAdapterCall.setData(testListModel.getData());
                        finalListForAdapterCall.setMeta(testListModel.getMeta());
                        myverticalPagerAdapter.notifyDataSetChanged();
                        showToast("updated");
                        isDataChangedCalling = false;
                        handler.removeCallbacks(this::run);

                    }
                }, 200);
            }

            @Override
            public void onFailed(String errMsg) {
                isDataChangedCalling = false;
                Snackbar snackbar = Snackbar
                        .make(main_layout_news_display, errMsg, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("AllNewsCategory.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private HashMap<String, Integer> myNewsCategoryHasMap() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("tops", 1);
        hashMap.put("pltc", 2);
        hashMap.put("sprt", 3);
        hashMap.put("scte", 4);
        hashMap.put("wrld", 5);
        hashMap.put("busi", 6);
        hashMap.put("entm", 7);
        hashMap.put("hlth", 8);
        hashMap.put("blog", 9);
        hashMap.put("advs", 10);
        hashMap.put("oths", 11);

        return hashMap;
    }

    private ArrayList<CategoryDataModel> getCategoriesList() {
        ArrayList<CategoryDataModel> arrayList = new ArrayList<>();
        arrayList.add(new CategoryDataModel("allNews", "All News"));
        arrayList.add(new CategoryDataModel("tops", "Tops"));
        arrayList.add(new CategoryDataModel("pltc", "Politics"));
        arrayList.add(new CategoryDataModel("sprt", "Sports"));
        arrayList.add(new CategoryDataModel("scte", "Technology"));
        arrayList.add(new CategoryDataModel("wrld", "World"));
        arrayList.add(new CategoryDataModel("busi", "Business"));
        arrayList.add(new CategoryDataModel("entm", "Entertainment"));
        arrayList.add(new CategoryDataModel("hlth", "Health"));
        arrayList.add(new CategoryDataModel("oths", "UnCategorized"));
        return arrayList;
    }

    private NewsVariables.NewsOrCoronaTypesEnum myStringEnumHasmap(String enumString) {
        HashMap<String, NewsVariables.NewsOrCoronaTypesEnum> hashMap = new HashMap<>();
        hashMap.put("tops", NewsVariables.NewsOrCoronaTypesEnum.News_TOPS);
        hashMap.put("allNews", NewsVariables.NewsOrCoronaTypesEnum.NEWS_All);
        hashMap.put("pltc", NewsVariables.NewsOrCoronaTypesEnum.News_POLITICS);
        hashMap.put("sprt", NewsVariables.NewsOrCoronaTypesEnum.News_SPORTS);
        hashMap.put("scte", NewsVariables.NewsOrCoronaTypesEnum.News_ENTERTAINMENT);
        hashMap.put("wrld", NewsVariables.NewsOrCoronaTypesEnum.News_WORLD);
        hashMap.put("busi", NewsVariables.NewsOrCoronaTypesEnum.News_BUSINESS);
        hashMap.put("entm", NewsVariables.NewsOrCoronaTypesEnum.News_ENTERTAINMENT);
        hashMap.put("hlth", NewsVariables.NewsOrCoronaTypesEnum.News_HEALTH);
        hashMap.put("oths", NewsVariables.NewsOrCoronaTypesEnum.News_UNCATEGORIES);
        return hashMap.get(enumString);

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }


    private void setBtnReloadVisibility() {
        btn_Reload.setVisibility(View.VISIBLE);
        txt_loading_data.setVisibility(View.GONE);
        loadingProgressBar.setVisibility(View.GONE);
    }

    private void setLoadingProgressVisibility() {
        btn_Reload.setVisibility(View.GONE);
        txt_loading_data.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.VISIBLE);

    }

    private void networkCallForAllNews() {
        if (isNetworkAvailable()) {
            setLoadingProgressVisibility();

            NewsNetworkCalls.getAllLatestNews(selectedNewsTypeEnum, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    if (dataumListModel != null) {
                        if (finalListForAdapterCall != null) {
                            finalListForAdapterCall.setMeta(dataumListModel.getMeta());
                            finalListForAdapterCall.setData(dataumListModel.getData());
                            finalListForAdapterCall.setNewsType(dataumListModel.getNewsType());
                        } else {
                            finalListForAdapterCall = dataumListModel;
                        }
                        if (news_err_layout.getVisibility() == View.VISIBLE) {
                            news_err_layout.setVisibility(View.GONE);
                        }
                        setBtnReloadVisibility();

                        myverticalPagerAdapter.notifyDataSetChanged();
                    } else {
                        setBtnReloadVisibility();
                        txt_err_message.setText("Data is null from Cloud");
                    }
                }

                @Override
                public void onFail(String errMsg) {
                    setBtnReloadVisibility();
                    txt_err_message.setText(errMsg);

                }
            });
        } else {
            txt_err_message.setText("Internet Not Available");
            setBtnReloadVisibility();

        }
    }

    private void networkCallForCategoryNews() {
        if (isNetworkAvailable()) {
            setLoadingProgressVisibility();
            NewsNetworkCalls.getAllCategoryWiseLatestNews(selectedNewsTypeEnum, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    if (dataumListModel != null) {
                        if (finalListForAdapterCall != null) {
                            finalListForAdapterCall.setMeta(dataumListModel.getMeta());
                            finalListForAdapterCall.setData(dataumListModel.getData());
                            finalListForAdapterCall.setNewsType(dataumListModel.getNewsType());
                        } else {
                            finalListForAdapterCall = dataumListModel;
                        }
                        if (news_err_layout.getVisibility() == View.VISIBLE) {
                            news_err_layout.setVisibility(View.GONE);
                        }
                        setBtnReloadVisibility();

                        myverticalPagerAdapter.notifyDataSetChanged();
                    } else {
                        setBtnReloadVisibility();
                        txt_err_message.setText("Data is null from Cloud");
                    }
                }

                @Override
                public void onFail(String errMsg) {
                    setBtnReloadVisibility();
                    txt_err_message.setText(errMsg);

                }
            });
        } else {
            txt_err_message.setText("Internet Not Available");
            setBtnReloadVisibility();
        }
    }

    private void networkCallForUpDateAllNews() {
        if (isNetworkAvailable()) {
            setLoadingProgressVisibility();

            NewsNetworkCalls.getAllLatestNews(selectedNewsTypeEnum, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    if (dataumListModel != null) {
                        if (finalListForAdapterCall != null) {
                            finalListForAdapterCall.setMeta(dataumListModel.getMeta());
                            finalListForAdapterCall.setData(dataumListModel.getData());
                            finalListForAdapterCall.setNewsType(dataumListModel.getNewsType());
                        } else {
                            finalListForAdapterCall = dataumListModel;
                        }
                        myverticalPagerAdapter.notifyDataSetChanged();
                        if (news_err_layout.getVisibility() == View.VISIBLE) {
                            news_err_layout.setVisibility(View.GONE);
                        }
                        setBtnReloadVisibility();

                        myverticalPagerAdapter.notifyDataSetChanged();
                    } else {
                        setBtnReloadVisibility();
                        txt_err_message.setText("Data is null from Cloud");
                    }
                    layout_update.setVisibility(View.GONE);
                    showError("Updated");
                }

                @Override
                public void onFail(String errMsg) {
                    layout_update.setVisibility(View.GONE);
                    showError(errMsg);

                }
            });
        } else {
            layout_update.setVisibility(View.GONE);
            showError("Internet Not Available");

        }
    }

    private void networkCallForUpdateCategoryNews() {
        if (isNetworkAvailable()) {
            setLoadingProgressVisibility();
            NewsNetworkCalls.getAllCategoryWiseLatestNews(selectedNewsTypeEnum, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    if (dataumListModel != null) {
                        if (finalListForAdapterCall != null) {
                            finalListForAdapterCall.setMeta(dataumListModel.getMeta());
                            finalListForAdapterCall.setData(dataumListModel.getData());
                            finalListForAdapterCall.setNewsType(dataumListModel.getNewsType());
                        } else {
                            finalListForAdapterCall = dataumListModel;
                        }
                        if (news_err_layout.getVisibility() == View.VISIBLE) {
                            news_err_layout.setVisibility(View.GONE);
                        }
                        setBtnReloadVisibility();

                        myverticalPagerAdapter.notifyDataSetChanged();
                    } else {
                        setBtnReloadVisibility();
                        txt_err_message.setText("Data is null from Cloud");
                    }
                    layout_update.setVisibility(View.GONE);
                    showError("Updated");
                }

                @Override
                public void onFail(String errMsg) {
                    layout_update.setVisibility(View.GONE);
                    showError(errMsg);

                }
            });
        } else {
            layout_update.setVisibility(View.GONE);
            showError("Internet Not Available");
        }
    }

    private void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    private void showError(String message) {
        Snackbar snackbar = Snackbar
                .make(main_layout_news_display, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onViewPagerPageSelected(String newsLink, int pagePosition) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Reload:
                if (selectedNewsTypeEnum == NewsVariables.NewsOrCoronaTypesEnum.NEWS_All) {
                    networkCallForAllNews();

                } else {
                    networkCallForCategoryNews();
                }
                break;

            case R.id.layout_news_frg_feat:

                if (img_go_top.getVisibility() == View.VISIBLE) {
                    verticalViewPager.setCurrentItem(0, true);

                } else if (img_update.getVisibility() == View.VISIBLE) {
                    layout_update.setVisibility(View.VISIBLE);
                    if (selectedNewsTypeEnum == NewsVariables.NewsOrCoronaTypesEnum.NEWS_All) {
                        networkCallForUpDateAllNews();
                    } else {
                        networkCallForUpdateCategoryNews();
                    }
                }
                break;
            case R.id.txt_nav_features:

                super.performClickFeatures();
                break;
        }

    }

    private void updateImgUpdateGoToTop(boolean isPositionZero) {
        if (isPositionZero) {
            if (img_go_top.getVisibility() == View.VISIBLE) {
                img_update.setVisibility(View.VISIBLE);
                img_go_top.setVisibility(View.GONE);
            }

        } else {
            if (img_go_top.getVisibility() == View.GONE) {
                img_update.setVisibility(View.GONE);
                img_go_top.setVisibility(View.VISIBLE);
            }
        }
    }

    private void changeMotionScene(boolean hasPositonIncresed) {
        if (hasPositonIncresed) {
            Log.d("myCurrentState", "changeMotionScene:  has increased" + layout_motion_news.getCurrentState());
            if (layout_motion_news.getCurrentState() == R.id.swipe_up) {
                layout_motion_news.setTransition(R.id.swipe_up, R.id.swipe_down);
                layout_motion_news.setTransitionDuration(100);
                layout_motion_news.transitionToEnd();
            }
        } else {

            if (layout_motion_news.getCurrentState() == R.id.swipe_down) {
                Log.d("myCurrentState", "changeMotionScene:  has decreased" + layout_motion_news.getCurrentState());

                layout_motion_news.setTransition(R.id.swipe_down, R.id.swipe_up);
                layout_motion_news.setTransitionDuration(100);
                layout_motion_news.transitionToEnd();
            }
        }
    }
}
