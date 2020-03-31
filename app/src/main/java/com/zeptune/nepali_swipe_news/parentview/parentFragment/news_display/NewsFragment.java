package com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.zeptune.nepali_swipe_news.Animation.ViewAnimation;
import com.zeptune.nepali_swipe_news.CategoryActivity;
import com.zeptune.nepali_swipe_news.MainActivity;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.adapter.inUse.TestVerticalAdapter;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsUpdateInterface;
import com.zeptune.nepali_swipe_news.all_interfaces.PagingInterface;
import com.zeptune.nepali_swipe_news.all_interfaces.ParentAdapeterNotofier;
import com.zeptune.nepali_swipe_news.models.CategoryDataModel;
import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.parentview.VerticalFragment;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.NewsDataArraylist;
import com.zeptune.nepali_swipe_news.share_preferance.SharePreferenceValues;
import com.zeptune.nepali_swipe_news.sqlite.DatabaseHandler;
import com.zeptune.nepali_swipe_news.utils.my_interface.AnimationInterface;

import java.util.ArrayList;
import java.util.HashMap;


import android.os.Handler;

import androidx.fragment.app.Fragment;

import com.zeptune.nepali_swipe_news.adapter.VerticalViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements VerticalFragment.ToggleVerticalViewPagerScrolling, NewsInterface, NewsUpdateInterface, PagingInterface, ParentAdapeterNotofier,
        AnimationInterface {
//        ArrayList<DataModel> dataModels = new ArrayList<>();
//}

    //variables
    private ArrayList<CategoryDataModel> arrayList;
    private String MY_PREFS_DATA_NAME = "mydata";
    private final String KEY_NEWS_JSON = "news_json";
    private HashMap<String, Integer> newsCategorykey;
    private int type;
    private String code;
    private boolean checkNetwork = true;
    private int priviousDataSize = 1;
    private int currentpage = 0;
    private NewsDataArraylist posts;
    private ArrayList<NewsDataArraylist> postList = new ArrayList<>();
    private boolean show_Up_Guide = false, show_Left_Guide = false, show_Right_Guide = false;

    //views
    private ViewPager2 verticalViewPager;
    private TestVerticalAdapter categoryWiseVerticalPagerAdapter;
    private FrameLayout loading_frame_layout;
    private ProgressBar progressBar;
    private RecyclerView category_list_onMainView;
    private TextView txtMainteinance, txtLoading, swipe_up_guide, swipe_right_guide, swipe_left_guide;
    private AdView adViewNewFragment;
    private View view;


    //class
    private Context mContext;
    private Handler handlerForSwipeUpAnimation, handlerForSwipeLeftAnimation, handlerForSwipeRightAnimation;
    private Runnable swipeUpRunnable, swipeRightRunnable, swipeLeftRunnable;
    private SharePreferenceValues sharePreferenceValues;
    private DataumListModel dataumListModel;
    private DataumListModel finalListForAdapterCall;
    private DataumListModel categoryDatalistModel;
    private DatabaseHandler databaseHandler;
    private TestVerticalAdapter myverticalPagerAdapter, anotherVerticalViewpager;
    private ViewAnimation viewAnimation;
    private Gson gson;
    private RecyclerView.LayoutManager categoryListLayoutManager;


    //interface
    private NewsInterface newsInterface;
    private PagingInterface pagingInterface;


    public NewsFragment() {
        // Required empty public constructor
    }


    public static NewsFragment newInstance(int type, String code) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("code", code);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        handlerForSwipeUpAnimation = new Handler();
        handlerForSwipeRightAnimation = new Handler();
        handlerForSwipeLeftAnimation = new Handler();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setSendData(this, this);
            ((MainActivity) getActivity()).sendMyPageNumber(this, this);
        } else if (getActivity() instanceof CategoryActivity) {
            ((CategoryActivity) getActivity()).sendMyCategoryPageNumber(this);
        }
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);

        return view;}

    @Override
    public void newsLink(String newsLink, int type) {

    }

    @Override
    public void newImageLink(String imageLink) {

    }

    @Override
    public void swipingDown(Boolean hideLayout) {

    }

    @Override
    public void onRightSwipe(Boolean rightSwipe) {

    }

    @Override
    public void verticalViewpagerPosition(int position) {

    }

    @Override
    public void updateNews(DataumListModel dataumListModel, boolean returntop) {

    }

    @Override
    public void pageNumber(String pageNumber, int position, Boolean showFromTop, VerticalViewPagerAdapter verticalViewPagerAdapter, String code) {

    }

    @Override
    public void notiyUpadate(boolean update) {

    }

    @Override
    public void trigger(int page) {

    }

    @Override
    public void removeLeftAnimation(boolean remove) {

    }

    @Override
    public void removeRightAnimation(boolean remove) {

    }
}

//        if (getArguments() != null) {
//            type = getArguments().getInt("type");
//            code = getArguments().getString("code");
//        }
//
//
//        dataModels.add(new DataModel("Android Volley Tutorial", "itme one", "this the first vertical view pager item"));
//        dataModels.add(new DataModel("Android Dagger 2", "itme two", "this the second vertical view pager item"));
//        dataModels.add(new DataModel("Android Geocoder Reverse Geocoding", "item 3", "this is the third view  paget item "));
//        dataModels.add(new DataModel("Android Notification Direct Reply", "item 4", "this is the forth view pager item"));
//        dataModels.add(new DataModel("RecyclerView Android with Dividers and Contextual Toolbar", "item 5", " this is the fifth view pager item"));
//        if (type == 1) {
//            myRightAnimation();
//            myLeftAnimation();
//            myUpAnimation();
//            if (isNetworkAvailable()) {
//                getLatestNews(view);
//            } else {
//                if (databaseHandler.getAllContacts(0).size() > 0) {
//                    try {
//                        if (finalListForAdapterCall != null) {
//                            finalListForAdapterCall.getData().clear();
//                            finalListForAdapterCall.setData(getDataFromDatabase(0).getData());
//                            finalListForAdapterCall.setMeta(getDataFromDatabase(0).getMeta());
//                        } else {
//                            finalListForAdapterCall = getDataFromDatabase(0);
//                        }
//                        if (myverticalPagerAdapter == null) {
//                            myverticalPagerAdapter = new TestVerticalAdapter(finalListForAdapterCall, mContext, null);
//                            verticalViewPager.setAdapter(myverticalPagerAdapter);
//                            loading_frame_layout.setVisibility(View.GONE);
//                            progressBar.setVisibility(View.GONE);
//                            txtLoading.setVisibility(View.GONE);
//
//                        } else {
//                            myverticalPagerAdapter.notifyDataSetChanged();
//                            loading_frame_layout.setVisibility(View.GONE);
//                            progressBar.setVisibility(View.GONE);
//                            txtLoading.setVisibility(View.GONE);
//                        }
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    show_Up_Guide = true;
//
//                } else {
//                    showDialog();
//                    Toast.makeText(mContext, "please connect to internaet", Toast.LENGTH_SHORT).show();
//                    swipe_up_guide.setVisibility(View.GONE);
//                }
//
//            }
//
//
//        } else if (type == 2) {
//            int categoryid = newsCategorykey.get(code);
//            if (isNetworkAvailable()) {
//                getCategoryWiseNews(view, code);
//            } else {
//                if (categoryDatalistModel != null) {
//                    categoryDatalistModel.getData().clear();
//                    try {
//                        categoryDatalistModel.setData(getDataFromDatabase(categoryid).getData());
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        categoryDatalistModel.setMeta(getDataFromDatabase(categoryid).getMeta());
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        if (getDataFromDatabase(categoryid) != null) {
//                            categoryDatalistModel = getDataFromDatabase(categoryid);
//                        } else {
//                            Toast.makeText(mContext, "Network not available", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//            if (categoryDatalistModel != null) {
//                if (categoryWiseVerticalPagerAdapter == null) {
//                    categoryWiseVerticalPagerAdapter = new TestVerticalAdapter(categoryDatalistModel, mContext, null, code);
//                    verticalViewPager.setAdapter(categoryWiseVerticalPagerAdapter);
//                    loading_frame_layout.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.GONE);
//                    txtLoading.setVisibility(View.GONE);
//                }
//            }
//        }
//
//        viewPagerRegisterCallBack();
//
//
//        return view;
//    }
//
//    private void initUi() {
//        loading_frame_layout = view.findViewById(R.id.loading_frame_layout);
//        progressBar = view.findViewById(R.id.newsProgressBar);
//        txtLoading = view.findViewById(R.id.txtLoading);
//        txtMainteinance = view.findViewById(R.id.txtMaintainace);
//        adViewNewFragment = view.findViewById(R.id.adViewNewFragment);
//        verticalViewPager = view.findViewById(R.id.container);
//        swipe_up_guide = view.findViewById(R.id.swipe_up_guide);
//        swipe_left_guide = view.findViewById(R.id.swipe_left_guide);
//        swipe_right_guide = view.findViewById(R.id.swipe_right_guide);
//        category_list_onMainView = view.findViewById(R.id.category_list_onMainView);
//
//
//
//    }
//
//    private void setUpCategoryList() {
//        Type categoryType = new TypeToken<CategoryListDataModel>() {
//        }.getType();
//        gson = new Gson();
//        CategoryListDataModel categoryListDataModel = gson.fromJson(loadJSONFromAsset(), categoryType);
//        arrayList = categoryListDataModel.getData();
//        Adapter categoryTypeAdapter = new Adapter(arrayList, mContext, Adapter.MAIN_CATEGORY_TYPE);
//        category_list_onMainView.setLayoutManager(categoryListLayoutManager);
//        category_list_onMainView.setAdapter(categoryTypeAdapter);
//        category_list_onMainView.setNestedScrollingEnabled(false);
//    }
//
//    private void initObj() {
//        newsInterface = (NewsInterface) mContext;
//        pagingInterface = (PagingInterface) mContext;
//        verticalViewPager.setPageTransformer(new ZoomOutPageTransformer());
//        newsCategorykey = myNewsCategoryHasMap();
//        databaseHandler = new DatabaseHandler(mContext);
//        viewAnimation = new ViewAnimation();
//        sharePreferenceValues = new SharePreferenceValues();
//        categoryListLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
//        setUpCategoryList();
//
//
//        swipeUpRunnable = new Runnable() {
//            @Override
//            public void run() {
//                if (sharePreferenceValues.isSwipedFromPreference(mContext, sharePreferenceValues.NEWS_UP_SWIPE, sharePreferenceValues.SWIPE_PREFERENCE)) {
//                    if (swipe_up_guide.getVisibility() != View.VISIBLE && show_Up_Guide) {
//                        swipe_up_guide.setVisibility(View.VISIBLE);
//                    }
//                    swipe_up_guide.setAnimation(viewAnimation.inFromButtonAnimation());
//                    myUpAnimation();
//                } else {
//                    swipe_up_guide.setVisibility(View.GONE);
//                    handlerForSwipeUpAnimation.removeCallbacks(swipeUpRunnable);
//                }
//
//            }
//        };
//        swipeRightRunnable = new Runnable() {
//            @Override
//            public void run() {
//                if (sharePreferenceValues.isSwipedFromPreference(mContext, sharePreferenceValues.NEWS_DETAIL_SWIPE, sharePreferenceValues.SWIPE_PREFERENCE)) {
//                    if (swipe_right_guide.getVisibility() != View.VISIBLE && show_Right_Guide) {
//                        swipe_right_guide.setVisibility(View.VISIBLE);
//                    }
//                    swipe_right_guide.setAnimation(viewAnimation.inFromRightAnimation());
//                    myRightAnimation();
//                } else {
//                    swipe_right_guide.setVisibility(View.GONE);
//                    handlerForSwipeRightAnimation.removeCallbacks(swipeRightRunnable);
//                }
//            }
//        };
//        swipeLeftRunnable = new Runnable() {
//            @Override
//            public void run() {
//                if (sharePreferenceValues.isSwipedFromPreference(mContext, sharePreferenceValues.CATEGORY_SWIPE, sharePreferenceValues.SWIPE_PREFERENCE)) {
//
//                    if (swipe_left_guide.getVisibility() != View.VISIBLE && show_Left_Guide) {
//                        swipe_left_guide.setVisibility(View.VISIBLE);
//                    }
//                    swipe_left_guide.setAnimation(viewAnimation.inFromLeftAnimation());
//                    myLeftAnimation();
//                } else {
//                    swipe_left_guide.setVisibility(View.GONE);
//                    handlerForSwipeLeftAnimation.removeCallbacks(swipeLeftRunnable);
//                }
//            }
//        };
//
//    }
//
//
//    private String loadJSONFromAsset() {
//        String json = null;
//        try {
//            InputStream is = getActivity().getAssets().open("AllNewsCategory.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }
//
//    private void myUpAnimation() {
//        handlerForSwipeUpAnimation.postDelayed(swipeUpRunnable, 2000);
//    }
//
//    private void myLeftAnimation() {
//        handlerForSwipeLeftAnimation.postDelayed(swipeLeftRunnable, 3000);
//
//
//    }
//
//    private void myRightAnimation() {
//        handlerForSwipeRightAnimation.postDelayed(swipeRightRunnable, 3000);
//    }
//
//    private void viewPagerRegisterCallBack() {
//        verticalViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                Log.d("viewpagerPosition", "onViewPagerPageSelected: " + position);
//
//                if (position == 1) {
//
//                    handlerForSwipeUpAnimation.removeCallbacks(swipeUpRunnable);
//                    if (sharePreferenceValues.isSwipedFromPreference(mContext, sharePreferenceValues.NEWS_UP_SWIPE, sharePreferenceValues.SWIPE_PREFERENCE)) {
//                        sharePreferenceValues.saveBooleanToPreference(mContext, sharePreferenceValues.NEWS_UP_SWIPE, false, sharePreferenceValues.SWIPE_PREFERENCE);
//                        handlerForSwipeUpAnimation.removeCallbacks(swipeUpRunnable);
//                        show_Left_Guide = true;
//
//                    }
//                    swipe_up_guide.setVisibility(View.GONE);
//
//
//                    if (show_Up_Guide) {
//                        show_Up_Guide = false;
//                    }
//                }
//                if (!sharePreferenceValues.isSwipedFromPreference(mContext, sharePreferenceValues.CATEGORY_SWIPE, sharePreferenceValues.SWIPE_PREFERENCE)) {
//                    myRightAnimation();
//                }
//                if (BuildConfig.DEBUG) {
//                    // do something for a debug build
//                    MobileAds.initialize(getActivity(), mContext.getResources().getString(R.string.banner_test_ads));
//
//                } else {
////            adViewNewFragment.setAdUnitId("ca-app-pub-8562845792538150/5341328929");
//                    MobileAds.initialize(getActivity(), mContext.getResources().getString(R.string.banner_real_ads));
//
//                }
//                AdRequest request = new AdRequest.Builder().build();
//                adViewNewFragment.loadAd(request);
//
//                if (finalListForAdapterCall != null) {
//                    Log.d("myCurrentPAge", "onViewPagerPageSelected: size " + finalListForAdapterCall.getData().size());
//                    Log.d("myCurrentPAge", "onViewPagerPageSelected: page position" + finalListForAdapterCall.getMeta().getCurrent_page());
//                    Log.d("myCurrentPAge", "onViewPagerPageSelected: viewpagerposition " + position);
//                    Datum datum = finalListForAdapterCall.getData().get(position);
//                    newsInterface.newsLink(datum.getFullLink(), 0);
//                    newsInterface.newImageLink(datum.getImage());
////                    Log.d("myCurrentPAge", "onViewPagerPageSelected: "+finalListForAdapterCall.getMeta().getCurrent_page());
//                    if (position + 5 >= finalListForAdapterCall.getData().size()) {
//                        if (checkNetwork) {
//                            priviousDataSize = finalListForAdapterCall.getData().size();
//                            if (finalListForAdapterCall != null) {
//                                pagingInterface.pageNumber(String.valueOf(Integer.parseInt(finalListForAdapterCall.getMeta().getCurrent_page()) + 1), position, null, null, null);
//                                checkNetwork = false;
//                            } else {
//                                Toast.makeText(mContext, "No Connection", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//
//                } else if (categoryDatalistModel != null) {
//                    Log.d("myCurrentPAge", "onViewPagerPageSelected: size " + categoryDatalistModel.getData().size());
//                    Log.d("myCurrentPAge", "onViewPagerPageSelected: page position" + categoryDatalistModel.getMeta().getCurrent_page());
//                    Log.d("myCurrentPAge", "onViewPagerPageSelected: viewpagerposition " + position);
//                    Datum datum = categoryDatalistModel.getData().get(position);
//                    newsInterface.newsLink(datum.getFullLink(), 1);
//                    newsInterface.newImageLink(datum.getImage());
//                    if (position + 5 >= categoryDatalistModel.getData().size()) {
//                        if (checkNetwork) {
//                            priviousDataSize = categoryDatalistModel.getData().size();
//                            pagingInterface.pageNumber(String.valueOf(Integer.parseInt(categoryDatalistModel.getMeta().getCurrent_page()) + 1), position, null, null, code);
//                            checkNetwork = false;
//                        }
//                    }
//                }
//                if (currentpage < position) {
//                    newsInterface.swipingDown(true);
//                    currentpage = position;
//                } else if (currentpage > position) {
//                    newsInterface.swipingDown(false);
//                    currentpage = position;
//                }
//                newsInterface.verticalViewpagerPosition(position);
//                if (finalListForAdapterCall != null) {
//                    Log.d("myCurrentPAge", "viewPagerRegisterCallBack: priviousdatasize" + priviousDataSize);
//                    if (priviousDataSize < finalListForAdapterCall.getData().size()) {
//                        checkNetwork = true;
//                    }
//                } else if (categoryDatalistModel != null) {
//                    if (priviousDataSize < categoryDatalistModel.getData().size()) {
//                        checkNetwork = true;
//                    }
//                }
//
//            }
//
//
//        });
//
//
//    }
//
//
//    public DataumListModel getDataFromDatabase(int id) throws UnsupportedEncodingException {
//        if (databaseHandler.getAllContacts(id).size() > 0) {
//            return GetJsonTobyte.getDatalisModel(databaseHandler.getAllContacts(id).get(0).getNewsDatumByte());
//        } else {
//            return null;
//        }
//    }
//
//    public void updateDataBase(DataumListModel dataumListModel, int id) {
//        byte[] datalistmodelByte = GetJsonTobyte.getByte(dataumListModel);
//        databaseHandler.updateContact(datalistmodelByte, id);
//    }
//
//    public void deleteDatabase() {
//        databaseHandler.deleteAll(0);
//    }
//
//    public void addToDatabase(byte[] byteToBeAdded, int id) {
//        databaseHandler.addContact(byteToBeAdded, id);
//
//    }
//
//
//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//    public void getLatestNews(final View view) {
//        Call<DataumListModel> latestNewsCall = ServiceFactory.getAllCategory().getLatestNews();
//        latestNewsCall.enqueue(new Callback<DataumListModel>() {
//            @Override
//            public void onResponse(Call<DataumListModel> call, Response<DataumListModel> response) {
//                show_Up_Guide = true;
//                Log.d("siiiisss", "onResponse: " + call.request().url());
//                dataumListModel = response.body();
//                if (dataumListModel != null) {
//
//                    if (databaseHandler.getAllContacts(0).size() > 0) {
//                        try {
//                            if (!dataumListModel.getData().get(0).getTitle().trim().equals(getDataFromDatabase(0).getData().get(0).getTitle())) {
//                                updateDataBase(dataumListModel, 0);
//                                if (finalListForAdapterCall != null) {
//                                    finalListForAdapterCall.getData().clear();
//                                    finalListForAdapterCall.setData(dataumListModel.getData());
//                                    finalListForAdapterCall.setMeta(dataumListModel.getMeta());
//                                } else {
//                                    finalListForAdapterCall = dataumListModel;
//                                }
//                            } else {
//                                if (finalListForAdapterCall != null) {
//                                    finalListForAdapterCall.getData().clear();
//                                    finalListForAdapterCall.setData(getDataFromDatabase(0).getData());
//                                    finalListForAdapterCall.setMeta(getDataFromDatabase(0).getMeta());
//                                } else {
//                                    finalListForAdapterCall = getDataFromDatabase(0);
//                                }
//                            }
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//
//                    } else {
//                        Log.d("darabaseloder", "onResponse: this  from new created ");
//                        byte[] mybyte = GetJsonTobyte.getByte(dataumListModel);
//                        addToDatabase(mybyte, 0);
//                        try {
//                            if (finalListForAdapterCall != null) {
//                                finalListForAdapterCall.getData().clear();
//                                finalListForAdapterCall.setData(getDataFromDatabase(0).getData());
//                                finalListForAdapterCall.setMeta(getDataFromDatabase(0).getMeta());
//                            } else {
//                                finalListForAdapterCall = getDataFromDatabase(0);
//                            }
//
//
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                        Log.d("myfirstnetworkcall", "onResponse: firstNEtworkcall");
//                    }
//
//                    if (myverticalPagerAdapter == null) {
//                        myverticalPagerAdapter = new TestVerticalAdapter(finalListForAdapterCall, mContext, null);
//                        verticalViewPager.setAdapter(myverticalPagerAdapter);
//                        loading_frame_layout.setVisibility(View.GONE);
//                        progressBar.setVisibility(View.GONE);
//                        txtLoading.setVisibility(View.GONE);
//
//                    } else {
//                        myverticalPagerAdapter.notifyDataSetChanged();
//                    }
//
//                } else {
//                    txtLoading.setVisibility(View.GONE);
//                    txtMainteinance.setVisibility(View.VISIBLE);
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<DataumListModel> call, Throwable t) {
//
//
//                Log.d("ssss", "onFailure: " + call.request().url());
//                Log.d("ssss", "onFailure: " + t.getMessage());
//                Log.d("ssss", "onFailure: " + t.getLocalizedMessage());
//                Log.d("ssss", "onFailure: " + t.getCause());
//
//            }
//        });
//    }
//
//    public void getLatestPageWisrNews(String pageNumber, final Boolean showFromTop, final VerticalViewPagerAdapter verticalViewPagerAdapter, String code) {
//        if (code == null) {
//            Call<DataumListModel> latestNewsCall = ServiceFactory.getAllCategory().getLatestNewsPage(pageNumber);
//            latestNewsCall.enqueue(new Callback<DataumListModel>() {
//                @Override
//                public void onResponse(Call<DataumListModel> call, Response<DataumListModel> response) {
//                    try {
//                        DataumListModel mydataumListModel = response.body();
//                        if (mydataumListModel != null) {
//                            DataumListModel oldListToUpdate = getDataFromDatabase(0);
//                            Log.d("thisskkkkk", "onResponse: first " + finalListForAdapterCall.getData().size());
//                            int a = finalListForAdapterCall.getData().size();
//                            ArrayList<Datum> mynewData = mydataumListModel.getData();
//                            oldListToUpdate.getData().addAll(mynewData);
//                            oldListToUpdate.setMeta(mydataumListModel.getMeta());
//                            updateDataBase(oldListToUpdate, 0);
//                            int b = getDataFromDatabase(0).getData().size();
//
//                            if (b > a) {
//                                if (finalListForAdapterCall != null) {
//                                    finalListForAdapterCall.getData().clear();
//                                    finalListForAdapterCall.setData(getDataFromDatabase(0).getData());
//                                    finalListForAdapterCall.setMeta(getDataFromDatabase(0).getMeta());
//                                    Log.d("thisskkkkk", "onResponse: lasr finallddd" + finalListForAdapterCall.getData().size());
//                                } else {
//                                    finalListForAdapterCall = getDataFromDatabase(0);
//                                    Log.d("thisskkkkk", "onResponse: lasr eeeeee" + finalListForAdapterCall.getData().size());
//
//                                }
//
//                                if (b > a) {
//
//                                    if (anotherVerticalViewpager != null) {
//                                        Log.d("thisskkkkk", "onResponse: lasr " + finalListForAdapterCall.getData().size());
//                                        if (finalListForAdapterCall.getData().size() > 0) {
//                                            anotherVerticalViewpager.notifyDataSetChanged();
//                                        }
//
//                                    } else if (myverticalPagerAdapter != null) {
//                                        myverticalPagerAdapter.notifyDataSetChanged();
//                                    }
//
//                                }
//                            }
//                        }
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//
//
//                @Override
//                public void onFailure(Call<DataumListModel> call, Throwable t) {
//
//                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_DATA_NAME, MODE_PRIVATE).edit();
//                    editor.putString("jsonString", "");
//                    editor.apply();
//                    Log.d("ssss", "onFailure: " + call.request().url());
//                    Log.d("ssss", "onFailure: " + t.getMessage());
//                    Log.d("ssss", "onFailure: " + t.getLocalizedMessage());
//                    Log.d("ssss", "onFailure: " + t.getCause());
//
//                }
//            });
//        } else {
//            Log.d("mypagenationcall", "getLatestPageWisrNews: pagenationion calll");
//            int categoryid = newsCategorykey.get(code);
//            Call<DataumListModel> latestNewsCall = ServiceFactory.getAllCategory().getCategoryWiseNewsPage(code, pageNumber);
//            latestNewsCall.enqueue(new Callback<DataumListModel>() {
//                @Override
//                public void onResponse(Call<DataumListModel> call, Response<DataumListModel> response) {
//                    try {
//                        Log.d("requestedUrl", "onResponse: " + call.request().url());
//                        DataumListModel mydataumListModel = response.body();
//                        if (mydataumListModel != null) {
//
//
//                            if (mydataumListModel != null) {
//                                DataumListModel oldListToUpdate = getDataFromDatabase(categoryid);
//                                ArrayList<Datum> mynewData = mydataumListModel.getData();
//                                oldListToUpdate.getData().addAll(mynewData);
//                                oldListToUpdate.setMeta(mydataumListModel.getMeta());
//                                updateDataBase(oldListToUpdate, categoryid);
//
//
//                                if (categoryDatalistModel != null) {
//                                    categoryDatalistModel.getData().clear();
//                                    categoryDatalistModel.setData(getDataFromDatabase(categoryid).getData());
//                                    categoryDatalistModel.setMeta(getDataFromDatabase(categoryid).getMeta());
//                                    Log.d("thisskkkkk", "onResponse: lasr finallddd" + categoryDatalistModel.getData().size());
//                                } else {
//                                    categoryDatalistModel = getDataFromDatabase(categoryid);
//                                    Log.d("thisskkkkk", "onResponse: lasr eeeeee" + categoryDatalistModel.getData().size());
//                                }
//
//
//                                if (categoryWiseVerticalPagerAdapter != null) {
//                                    Log.d("thisskkkkk", "onResponse: lasr " + categoryDatalistModel.getData().size());
//                                    if (categoryDatalistModel.getData().size() > 0) {
//                                        categoryWiseVerticalPagerAdapter.notifyDataSetChanged();
//
//
//                                    }
//                                }
//                                Log.d("categorysize", "onResponse: " + categoryDatalistModel.getData().size());
//                            } else {
//                                Toast.makeText(mContext, "page ends here", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//
//
//                @Override
//                public void onFailure(Call<DataumListModel> call, Throwable t) {
//
//                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_DATA_NAME, MODE_PRIVATE).edit();
//                    editor.putString("jsonString", "");
//                    editor.apply();
//                    Log.d("ssss", "onFailure: " + call.request().url());
//                    Log.d("ssss", "onFailure: " + t.getMessage());
//                    Log.d("ssss", "onFailure: " + t.getLocalizedMessage());
//                    Log.d("ssss", "onFailure: " + t.getCause());
//
//                }
//            });
//
//        }
//    }
//
//    public void getCategoryWiseNews(final View view, String categoryCode) {
//        Call<DataumListModel> latestNewsCall = ServiceFactory.getAllCategory().getCategoryWiseNews(categoryCode);
//        latestNewsCall.enqueue(new Callback<DataumListModel>() {
//            @Override
//            public void onResponse(Call<DataumListModel> call, Response<DataumListModel> response) {
//                Log.d("dsfadasdfasdf", "onResponse: " + call.request().url());
//                int categoryid = newsCategorykey.get(categoryCode);
//
//                DataumListModel dataumListModel = response.body();
//                if (dataumListModel != null) {
//
//
//                    if (databaseHandler.getAllContacts(categoryid).size() > 0) {
//                        Log.d("tobeinserted", "onResponse: " + categoryid);
//                        try {
//
//                            if (!dataumListModel.getData().get(0).getTitle().trim().equals(getDataFromDatabase(categoryid).getData().get(0).getTitle())) {
//                                updateDataBase(dataumListModel, categoryid);
//                                if (categoryDatalistModel != null) {
//                                    categoryDatalistModel.getData().clear();
//                                    categoryDatalistModel.setData(dataumListModel.getData());
//                                    categoryDatalistModel.setMeta(dataumListModel.getMeta());
//                                } else {
//                                    categoryDatalistModel = dataumListModel;
//                                }
//                            } else {
//                                if (categoryDatalistModel != null) {
//                                    categoryDatalistModel.getData().clear();
//                                    categoryDatalistModel.setData(getDataFromDatabase(categoryid).getData());
//                                    categoryDatalistModel.setMeta(getDataFromDatabase(categoryid).getMeta());
//                                } else {
//                                    categoryDatalistModel = getDataFromDatabase(categoryid);
//                                }
//                            }
//
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//
//                    } else {
//
//                        byte[] mybyte = GetJsonTobyte.getByte(dataumListModel);
//                        addToDatabase(mybyte, categoryid);
//                        try {
//                            Log.d("darabaselodercategory", "onResponse: this  from new created " + getDataFromDatabase(categoryid));
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            if (categoryDatalistModel != null) {
//                                categoryDatalistModel.getData().clear();
//                                categoryDatalistModel.setData(getDataFromDatabase(categoryid).getData());
//                                categoryDatalistModel.setMeta(getDataFromDatabase(categoryid).getMeta());
//                            } else {
//                                categoryDatalistModel = getDataFromDatabase(categoryid);
//                            }
//
//
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                        Log.d("myfirstnetworkcall", "onResponse: firstNEtworkcall");
//                    }
//
//                    if (categoryWiseVerticalPagerAdapter != null) {
//                        categoryWiseVerticalPagerAdapter.notifyDataSetChanged();
//                    } else {
//                        categoryWiseVerticalPagerAdapter = new TestVerticalAdapter(categoryDatalistModel, mContext, null, categoryCode);
//                        verticalViewPager.setAdapter(categoryWiseVerticalPagerAdapter);
//                        loading_frame_layout.setVisibility(View.GONE);
//                        progressBar.setVisibility(View.GONE);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataumListModel> call, Throwable t) {
//                Log.d("myurl", "onFailure: " + call.request().url());
//
//            }
//        });
//    }
//
//
//    public void updateDatabase(byte[] byteToBeUpdated, int id) throws UnsupportedEncodingException {
////        databaseHandler.updateContact(byteToBeUpdated, id);
////        convertedDatamodelList = GetJsonTobyte.getDatalisModel(databaseHandler.getAllContacts().get(0).getNewsDatumByte());
////        Log.d("sizeofCurrentData", "onCreateView: " + convertedDatamodelList.getData().size());
//    }
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        this.mContext = context;
//    }
//
//    @Override
//    public void trigger(int page) {
//        if (page == 1) {
//            verticalViewPager.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//
//                    return true;
//                }
//            });
//        } else {
//            verticalViewPager.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return false;
//                }
//            });
//        }
//    }
//
//    @Override
//    public void newsLink(String newsLink, int type) {
//
//    }
//
//    @Override
//    public void newImageLink(String imageLink) {
//
//    }
//
//    @Override
//    public void swipingDown(Boolean hideLayout) {
//
//    }
//
//    @Override
//    public void onRightSwipe(Boolean rightSwipe) {
//
//    }
//
//    @Override
//    public void verticalViewpagerPosition(int position) {
//
//    }
//
//    @Override
//    public void updateNews(DataumListModel dataumListModel, boolean goTotop) {
//        if (goTotop) {
//            if (finalListForAdapterCall != null) {
//                finalListForAdapterCall.getData().clear();
//                finalListForAdapterCall.setData(dataumListModel.getData());
//                finalListForAdapterCall.setMeta(dataumListModel.getMeta());
//            } else {
//                finalListForAdapterCall = dataumListModel;
//            }
//            myverticalPagerAdapter = new TestVerticalAdapter(finalListForAdapterCall, mContext, null);
//            verticalViewPager.setAdapter(myverticalPagerAdapter);
//            loading_frame_layout.setVisibility(View.GONE);
//            progressBar.setVisibility(View.GONE);
//
//        } else {
//            if (dataumListModel != null) {
//                if (finalListForAdapterCall != null) {
//                    finalListForAdapterCall.getData().clear();
//                    finalListForAdapterCall.setData(dataumListModel.getData());
//                    finalListForAdapterCall.setMeta(dataumListModel.getMeta());
//                } else {
//                    finalListForAdapterCall = dataumListModel;
//                }
//                myverticalPagerAdapter = new TestVerticalAdapter(finalListForAdapterCall, mContext, null);
//                verticalViewPager.setAdapter(myverticalPagerAdapter);
//                loading_frame_layout.setVisibility(View.GONE);
//                progressBar.setVisibility(View.GONE);
//            } else {
//                Toast.makeText(mContext, "no new updata", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//
//    @Override
//    public void pageNumber(String pageNumber, int position, Boolean showFromTopn, VerticalViewPagerAdapter verticalViewPagerAdapter, String code) {
//        getLatestPageWisrNews(pageNumber, showFromTopn, verticalViewPagerAdapter, code);
//        Log.d("mydatabasesize", "pageNumber: " + pageNumber);
//    }
//
//    @Override
//    public void notiyUpadate(boolean update) {
//
//    }
//
//    @Override
//    public void removeLeftAnimation(boolean remove) {
//        handlerForSwipeLeftAnimation.removeCallbacks(swipeLeftRunnable);
//        swipe_left_guide.setVisibility(View.GONE);
//        show_Left_Guide = false;
//        show_Right_Guide = true;
//    }
//
//    @Override
//    public void removeRightAnimation(boolean remove) {
//        handlerForSwipeRightAnimation.removeCallbacks(swipeRightRunnable);
//        swipe_right_guide.setVisibility(View.GONE);
//        show_Right_Guide = false;
//    }
//
//
//    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
//        private float mLastPositionOffset = 0f;
//
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            if (positionOffset < mLastPositionOffset && positionOffset < 0.9) {
//                verticalViewPager.setCurrentItem(position);
//            } else if (positionOffset > mLastPositionOffset && positionOffset > 0.1) {
//                verticalViewPager.setCurrentItem(position + 1);
//            }
//            mLastPositionOffset = positionOffset;
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    public HashMap<String, Integer> myNewsCategoryHasMap() {
//        HashMap<String, Integer> hashMap = new HashMap<>();
//        hashMap.put("tops", 1);
//        hashMap.put("pltc", 2);
//        hashMap.put("sprt", 3);
//        hashMap.put("scte", 4);
//        hashMap.put("wrld", 5);
//        hashMap.put("busi", 6);
//        hashMap.put("entm", 7);
//        hashMap.put("hlth", 8);
//        hashMap.put("blog", 9);
//        hashMap.put("advs", 10);
//        hashMap.put("oths", 11);
//
//        return hashMap;
//    }
//
//    public void showDialog() {
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
//        builder1.setMessage("Network not available");
//
//        builder1.setCancelable(true);
//
//        builder1.setPositiveButton(
//                "Okay",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//    }
//
//    public String newString() {
//        return "newsString";
//    }
//}
