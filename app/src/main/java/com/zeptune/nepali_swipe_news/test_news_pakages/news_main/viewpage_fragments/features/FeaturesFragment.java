package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.zeptune.nepali_swipe_news.BuildConfig;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.adapter.inUse.CurrencyAdapter;
import com.zeptune.nepali_swipe_news.adapter.inUse.FeaturesAdapter;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DataRasiFalModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.GoldDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.GoldModelWrapper;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.News_Variabls;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.RasiFalDatum;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.CaseDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseListModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.currencyDataModel.CurrencyDatum;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.currencyDataModel.DataCurrencyModel;
import com.zeptune.nepali_swipe_news.share_preferance.SharePreferenceValues;
import com.zeptune.nepali_swipe_news.test_news_pakages.CoronaActivity;
import com.zeptune.nepali_swipe_news.test_news_pakages.TestMainActivity;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.BaseFeaturesFragment;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.features_gold_silver.BaseGoldSilverSheet;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.features_gold_silver.BtmSheetFragGoldPrice;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.for_currency.BaseCurrencyFragment;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.for_currency.BtmSheetFragCurrency;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.for_rasifal.BaseRasifalSheet;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.for_rasifal.BtmSheetFragRasifal;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.CoronaNetworkCall;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesNetworkCalls;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCaseDbUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCountryWiseDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CurrencyDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.DateDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.GoldDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.RasifalDatabaseUtils;
import com.zeptune.nepali_swipe_news.utils.NewsStaticVarialbles;

import java.util.ArrayList;

public class FeaturesFragment extends BaseFeaturesFragment implements View.OnClickListener {

    //Variables
    private ArrayList<RasiFalDatum> myRasiDatum;
    private ArrayList<GoldDataModel> myGoldList;
    private ArrayList<CurrencyDatum> currencyData;
    private String date;
    //View
    private View view;
    private Button txt_update_rasifal;
    private CardView btn_open_corona_frag;
    private FrameLayout nativeAdFrameLayout;
    private RecyclerView recycler_currency_recycler_view;
    //date
    private TextView txt_year, txt_day_of_the_week, txt_month, txt_day, txt_nav_news;
    //rasifal
    private RecyclerView rasifal_recycler_view;
    private TextView txt_see_all_rasifal, updateCurrency;
    //gold
    private TextView txt_Silver_first, txt_silver_first_price, txt_silver_second_gram, txt_silver_gram_second_price;
    private TextView txt_see_all_silver_prices;
    private LinearLayout layout_features_main;
    //Class
    private DataRasiFalModel thisDataRasiModel;
    private CurrencyAdapter currencyAdapter;
    private SharePreferenceValues sharePreferenceValues;
    private Context mContext;
    private Gson gson;
    private FeaturesAdapter rasifalTypeAdapter;
    private RecyclerView.LayoutManager featureLayoutManager;
    private LinearLayout gold_wrapper;
    private LinearLayout rasfal_wrapper;
    TextView txt_rasifal_title, txt_rasi_description;
    private AdView ad_features;
    private ScrollView scroll_view_layout;
    private CardView card_currency_wrapper;
    TextView death_title, txt_death_number, corona_cases, case_title;


    public FeaturesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.test_menu_layout, container, false);
        initUi();
        initObj();
        setUpRasifalRecyclerView();
        setUpGoldSilverView();
        if (!NewsStaticVarialbles.IS_RASIFAL_CALLED) {
            getRasifalFromCloud(true);
        }
        if (!NewsStaticVarialbles.IS_GOLD_SILVER_CALLED) {
            getGoldPriceFromCloud(true);
        }
        if (!NewsStaticVarialbles.IS_CURRENC_EXCHANGED_CALLED) {
            callCurrrencyUpdate();
        }
//        if (!NewsStaticVarialbles.IS_CORONA_WORLD_CASE_CALLED) {
//            coronaWorldWideCall();
//        }
//        if (!NewsStaticVarialbles.IS_CORONA_COUNTRY_WISE_CASE_CALLED) {
//            coronaCountryWiseCall();
//
//        }
        loadAds();
        return view;
    }
///dsfasdfasdfasdf
    private void displayUnifiedNativeAd(UnifiedNativeAdView adView, UnifiedNativeAd ad) {
        TextView headlineView = adView.findViewById(R.id.ad_headline);
        headlineView.setText(ad.getHeadline());
        adView.setHeadlineView(headlineView);
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        mediaView.setMediaContent(ad.getMediaContent());
        mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
        adView.setMediaView(mediaView);
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setIconView(adView.findViewById(R.id.ad_icon));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStoreView(adView.findViewById(R.id.ad_store));

        ((TextView) adView.getHeadlineView()).setText(ad.getHeadline());
        if (ad.getBody() == null) {
            adView.getBodyView().setVisibility(View.GONE);
        }
        ((TextView) adView.getHeadlineView()).setText(ad.getHeadline());
        ((TextView) adView.getBodyView()).setText(ad.getBody());
        ((Button) adView.getCallToActionView()).setText(ad.getCallToAction());
        NativeAd.Image icon = ad.getIcon();

        if (icon == null) {
            adView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (ad.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(ad.getPrice());
        }

        if (ad.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(ad.getStore());
        }

        if (ad.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(ad.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (ad.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(ad.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(ad);


    }

    private void initUi() {
        txt_day = view.findViewById(R.id.txt_day);
        txt_month = view.findViewById(R.id.txt_month);
        txt_year = view.findViewById(R.id.txt_year);
        ad_features = view.findViewById(R.id.ad_features);
        card_currency_wrapper = view.findViewById(R.id.card_currency_wrapper);
        scroll_view_layout = view.findViewById(R.id.scroll_view_layout);
        updateCurrency = view.findViewById(R.id.updateCurrency);
        recycler_currency_recycler_view = view.findViewById(R.id.recycler_currency_recycler_view);
        recycler_currency_recycler_view.setNestedScrollingEnabled(false);
        scroll_view_layout.setNestedScrollingEnabled(false);
        txt_rasifal_title = view.findViewById(R.id.txt_rasifal_title);
        txt_rasi_description = view.findViewById(R.id.txt_rasi_description);
        txt_day_of_the_week = view.findViewById(R.id.txt_day_of_the_week);
        rasifal_recycler_view = view.findViewById(R.id.rasifal_recycler_view);
        txt_see_all_rasifal = view.findViewById(R.id.txt_see_all_rasifal);

        txt_Silver_first = view.findViewById(R.id.txt_Silver_first);
        txt_silver_first_price = view.findViewById(R.id.txt_silver_first_price);
        txt_silver_second_gram = view.findViewById(R.id.txt_silver_second_gram);
        txt_silver_gram_second_price = view.findViewById(R.id.txt_silver_gram_second_price);
        txt_see_all_silver_prices = view.findViewById(R.id.txt_see_all_silver_prices);
        layout_features_main = view.findViewById(R.id.layout_features_main);
        rasfal_wrapper = view.findViewById(R.id.rasfal_wrapper);
        gold_wrapper = view.findViewById(R.id.gold_wrapper);
        txt_nav_news = view.findViewById(R.id.txt_nav_news);
        btn_open_corona_frag = view.findViewById(R.id.btn_open_corona_frag);
        txt_update_rasifal = view.findViewById(R.id.txt_update_rasifal);
        nativeAdFrameLayout = view.findViewById(R.id.mynativeAdFrameLayout);
        death_title = view.findViewById(R.id.death_title);
        txt_death_number = view.findViewById(R.id.txt_death_number);
        corona_cases = view.findViewById(R.id.cases_num);
        case_title = view.findViewById(R.id.case_title);


        btn_open_corona_frag.setOnClickListener(this);
//        getGoldPriceFromCloud();
//        getRasifalFromCloud();
        setCoronLayoutButton();

    }

    private void setCoronLayoutButton() {
        CaseDataModel caseDataModel = CoroCaseDbUtils.saveCoroCaseDataModel(NewsVariables.NewsOrCoronaTypesEnum.CORONA_WORLD_CASES);
        if (caseDataModel != null) {
            corona_cases.setText(caseDataModel.total_cases);
            txt_death_number.setText(caseDataModel.total_deaths);
        }
    }

    private void loadAds() {

        if (BuildConfig.DEBUG) {
            // do something for a debug build
            MobileAds.initialize(((TestMainActivity) mContext), mContext.getString(R.string.banner_test_ads));

        } else {
            MobileAds.initialize(((TestMainActivity) mContext), mContext.getString(R.string.banner_real_ads));

        }
        AdRequest request = new AdRequest.Builder().build();
        ad_features.loadAd(request);
    }

    private void initObj() {
        myRasiDatum = new ArrayList<>();
        myGoldList = new ArrayList<>();
        currencyData = new ArrayList<>();
        sharePreferenceValues = new SharePreferenceValues();
        gson = new Gson();
        rasfal_wrapper.setOnClickListener(this);
        gold_wrapper.setOnClickListener(this);
        txt_nav_news.setOnClickListener(this);
        txt_update_rasifal.setOnClickListener(this);
        updateCurrency.setOnClickListener(this);
        card_currency_wrapper.setOnClickListener(this);
        recycler_currency_recycler_view.setOnClickListener(this);
        DateDatabaseUtils.setTodayDate(txt_year, txt_month, txt_day, txt_day_of_the_week);
        setUpGoldSilverView();
        setCurrencyRecyclerView();
        txt_silver_second_gram.setVisibility(View.VISIBLE);
        txt_silver_gram_second_price.setVisibility(View.VISIBLE);

//        setRadioButtonListener(getGoldSilverPrice());

//        loadNativeAds();

    }

    private void loadNativeAds() {

        if (BuildConfig.DEBUG) {
            contextWiseNativeAd(mContext.getResources().getString(R.string.native_test_ads));
        } else {
            contextWiseNativeAd(mContext.getResources().getString(R.string.native_release_ads));

        }

    }

    private void contextWiseNativeAd(String adId) {
        AdLoader adLoader = new AdLoader.Builder(mContext, adId)
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the ad.
                        // Show the ad.
                        Log.d("nativead", "onUnifiedNativeAdLoaded: ");

                        LayoutInflater inflater = LayoutInflater.from(mContext);
                        UnifiedNativeAdView adView = (UnifiedNativeAdView) inflater
                                .inflate(R.layout.ad_unified, null);
                        displayUnifiedNativeAd(adView, unifiedNativeAd);
                        adView.setBackgroundColor(mContext.getResources().getColor(R.color.soft_sky_blue));
                        nativeAdFrameLayout.removeAllViews();
                        nativeAdFrameLayout.addView(adView);

                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {

                        nativeAdFrameLayout.setBackground(mContext.getResources().getDrawable(R.drawable.blurnoimg));
                        Log.d("nativead", "onAdFailedToLoad: " + String.valueOf(errorCode));

                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();


//            ca-app-pub-8562845792538150/1279984356
        adLoader.loadAd(new AdRequest.Builder().build());
    }

//    private void setRadioButtonListener() {
//        radio_group_gold_Silver.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId == R.id.radio_btn_gold) {
//                    txt_see_all_silver_prices.setVisibility(View.VISIBLE);
//                    txt_silver_second_gram.setVisibility(View.GONE);
//                    txt_silver_gram_second_price.setVisibility(View.GONE);
//                } else if (checkedId == R.id.radio_btn_silver) {
//
//                    txt_see_all_silver_prices.setVisibility(View.GONE);
//                    txt_see_all_silver_prices.setVisibility(View.GONE);
//
//                }
//                setUpGoldSilverView();
////                setUpGoldSilverView();
//            }
//        });
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scroll_view_layout.scrollTo(0, 0);

    }

    private void setCurrencyRecyclerView() {
        if (currencyData != null && getCurrencyModel() != null) {
            currencyData.addAll(getCurrencyModel().getData().getFetched_data());
        } else if (getCurrencyModel() != null) {
            currencyData = getCurrencyModel().getData().getFetched_data();
        }
        if (currencyAdapter == null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            currencyAdapter = new CurrencyAdapter(currencyData, false, new CurrencyAdapter.CurrencyAdapterListener() {
                @Override
                public void onViewClicked() {
                    setUpCurrencyBottomSheet();
                }
            });
            recycler_currency_recycler_view.setLayoutManager(layoutManager);
            recycler_currency_recycler_view.setAdapter(currencyAdapter);
        }
        if (currencyAdapter != null) {
            currencyAdapter.notifyDataSetChanged();
        }
        if (currencyData.size() > 0) {
            recycler_currency_recycler_view.scrollToPosition(0);
        }
    }

    private void setUpRasifalRecyclerView() {
        if (rasifalTypeAdapter == null) {
            featureLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            rasifalTypeAdapter = new FeaturesAdapter(myRasiDatum, mContext, News_Variabls.TYPE.RASIFAL_TYPE, FeaturesAdapter.RASIFAL_TYPE_FRONT);
            rasifal_recycler_view.setLayoutManager(featureLayoutManager);
            rasifal_recycler_view.setAdapter(rasifalTypeAdapter);
        }

        if (myRasiDatum != null && getRasiFals() != null && getRasiFals().getData().getFetched().size()>0) {
            myRasiDatum.clear();
            myRasiDatum.addAll(getRasiFals().getData().getFetched());
            date = getRasiFals().getData().getMyDate();
            txt_rasifal_title.setText(myRasiDatum.get(0).getMain_title());
            txt_rasi_description.setText(myRasiDatum.get(0).getContent());
        } else if (getRasiFals() != null) {
            myRasiDatum = getRasiFals().getData().getFetched();
            date = getRasiFals().getData().getMyDate();
            txt_rasifal_title.setText(myRasiDatum.get(0).getMain_title());
            txt_rasi_description.setText(myRasiDatum.get(0).getContent());
        }
        rasifalTypeAdapter.notifyDataSetChanged();
    }

    private DataRasiFalModel getRasiFals() {
        return RasifalDatabaseUtils.saveRasiDataModel(FeaturesVariables.FeaturesTypesEnum.FEATURES_RASIFAL);
    }

    private DataCurrencyModel getCurrencyModel() {
        return CurrencyDatabaseUtils.saveCurrencyDataModel(FeaturesVariables.FeaturesTypesEnum.FEATURES_CURRENCY);
    }

    private ArrayList<GoldDataModel> getGoldSilverPrice() {
        GoldModelWrapper goldModelWrapper = GoldDatabaseUtils.saveGoldDataModel(FeaturesVariables.FeaturesTypesEnum.FEATURES_GOLD_SILVER);
        if (goldModelWrapper != null) {
            return goldModelWrapper.goldDataModels;
        } else {
            return null;
        }
    }

    private void setUpGoldSilverView() {
        Log.d("checkChangeListener", "setUpGoldSilverView: ");
        if (myGoldList != null && getGoldSilverPrice() != null) {
            myGoldList.clear();
            myGoldList.addAll(getGoldSilverPrice());
        } else if (getGoldSilverPrice() != null) {
            myGoldList = getGoldSilverPrice();
        }
        if (myGoldList != null) {
            if (myGoldList.size() > 0) {

                updateGoldSilverView(myGoldList);
                Log.d("mygoldlisttitile", "updateGoldSilverView: " + myGoldList.get(0).title);
            }

        }

    }

    private void onRadioButtonSelected() {

    }

    private void updateGoldSilverView(ArrayList<GoldDataModel> arrayList) {
//        if (radio_btn_gold.isChecked()) {
//            txt_see_all_silver_prices.setVisibility(View.GONE);
//            txt_Silver_first.setText(arrayList.get(0).title);
//            txt_silver_first_price.setText(arrayList.get(0).value);
//        } else if (radio_btn_silver.isChecked()) {

        txt_Silver_first.setText(arrayList.get(0).title);
        txt_silver_first_price.setText(arrayList.get(0).value);
        txt_silver_gram_second_price.setText(arrayList.get(2).value);
        txt_silver_second_gram.setText(arrayList.get(2).title);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rasfal_wrapper:
                if (myRasiDatum != null) {
                    if (myRasiDatum.size() > 2) {
                        openRasifalBottomSheet();
                    } else {
                        if (isNetworkAvailable()) {
                            getRasifalFromCloud(false);
                            super.performShowDialog();
                        } else {
                            showToast("No Network Available");
                        }
                    }
                } else {
                    if (isNetworkAvailable()) {
                        getRasifalFromCloud(false);
                        super.performShowDialog();
                    } else {
                        showToast("No Network Available");
                    }
                }
                break;
            case R.id.gold_wrapper:
                if (myGoldList != null) {
                    if (myGoldList.size() > 2) {
                        openGoldBottomSheet();
                    } else {
                        if (isNetworkAvailable()) {
                            getGoldPriceFromCloud(false);
                            super.performShowDialog();
                        } else {
                            showToast("No Network Available");
                        }
                    }
                } else {
                    if (isNetworkAvailable()) {
                        getGoldPriceFromCloud(false);
                        super.performShowDialog();
                    } else {
                        showToast("No Network Available");
                    }
                }

                break;

            case R.id.txt_nav_news:
                super.performNewsNavPress();
                break;
            case R.id.txt_update_rasifal:
                if (isNetworkAvailable()) {
                    getRasifalFromCloud(false);
                    super.performShowDialog();
                } else {
                    showToast("No Network Available");
                }
                break;
            case R.id.btn_open_corona_frag:
                startActivity(new Intent(mContext, CoronaActivity.class));
                break;
            case R.id.updateCurrency:
                super.performShowDialog();
                callCurrrencyUpdate();
                break;
            case R.id.card_currency_wrapper:
                setUpCurrencyBottomSheet();
                break;
            case R.id.recycler_currency_recycler_view:
                setUpCurrencyBottomSheet();
                ;
                break;

        }

    }

    private void callCurrrencyUpdate() {
        FeaturesNetworkCalls.getCurrencyExchangeRate(FeaturesVariables.FeaturesTypesEnum.FEATURES_CURRENCY, new CurrencyDatabaseUtils.OnDatabaseChangeListener() {
            @Override
            public void onSuccess(DataCurrencyModel currencyDataModel) {
                if (currencyDataModel != null) {

                    if (currencyData != null) {
                        currencyData.addAll(currencyDataModel.getData().getFetched_data());
                        showToast("Updated");
                    } else {
                        currencyData = currencyDataModel.getData().getFetched_data();
                        showToast("Updated");
                    }
                    currencyAdapter.notifyDataSetChanged();
//                    scroll_view_layout.scrollTo(0,0);


                }
                FeaturesFragment.super.performHideDialog();
            }

            @Override
            public void onFail(String errMsg) {
                showToast(errMsg);
                FeaturesFragment.super.performHideDialog();
            }
        });
    }

    private void openRasifalBottomSheet() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("rasiData", myRasiDatum);
        bundle.putString("date", date);
        BtmSheetFragRasifal btmSheetFragRasifal =
                BtmSheetFragRasifal.newInstance(bundle);
        btmSheetFragRasifal.show(getActivity().getSupportFragmentManager(),
                "BottomSheet");
        btmSheetFragRasifal.setRasifalBase(new BaseRasifalSheet.BaseRasifalSheetListener() {
            @Override
            public void onNewUpdate() {
                setUpRasifalRecyclerView();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void openGoldBottomSheet() {
        Bundle goldBundle = new Bundle();
        goldBundle.putParcelableArrayList("goldData", myGoldList);
        BtmSheetFragGoldPrice btmSheetFragGoldPrice =
                BtmSheetFragGoldPrice.newInstance(goldBundle);
        btmSheetFragGoldPrice.show(getActivity().getSupportFragmentManager(),
                "BottomSheetGold");
        btmSheetFragGoldPrice.setBaseGoldSheet(new BaseGoldSilverSheet.BaseGoldSheetListener() {
            @Override
            public void onNewUpdate() {
                setUpGoldSilverView();
            }
        });
    }

    public void setUpCurrencyBottomSheet() {
        BtmSheetFragCurrency btmSheetFragCurrency =
                BtmSheetFragCurrency.newInstance();
        btmSheetFragCurrency.show(getActivity().getSupportFragmentManager(),
                "BottomSheetCurrency");
        btmSheetFragCurrency.setBaseCurrency(new BaseCurrencyFragment.BaseCurrencySheetListener() {
            @Override
            public void onNewUpdate() {
                setCurrencyRecyclerView();
            }
        });

    }


    private void showToast(String errMsg) {
        Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
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
        this.mContext = context;
    }

    private void getRasifalFromCloud(boolean isAutoUpdate) {
        FeaturesNetworkCalls.getRasifalFromCloud(FeaturesVariables.FeaturesTypesEnum.FEATURES_RASIFAL, new RasifalDatabaseUtils.OnDatabaseChangeListener() {
            @Override
            public void onSuccess(DataRasiFalModel dataRasiFalModels) {
                DataRasiFalModel dataRasiFalModel = dataRasiFalModels;
                String rasiFalJson = gson.toJson(dataRasiFalModel);
                if (myRasiDatum != null && dataRasiFalModel != null) {
                    myRasiDatum.clear();
                    date = dataRasiFalModel.getData().getMyDate();

                    myRasiDatum.addAll(dataRasiFalModel.getData().getFetched());
                    txt_rasifal_title.setText(myRasiDatum.get(0).getMain_title());
                    txt_rasi_description.setText(myRasiDatum.get(0).getContent());
                } else if (dataRasiFalModel != null) {
                    if (dataRasiFalModel.getData() != null) {
                        date = dataRasiFalModel.getData().getMyDate();
                        myRasiDatum = dataRasiFalModel.getData().getFetched();
                        txt_rasifal_title.setText(myRasiDatum.get(0).getMain_title());
                        txt_rasi_description.setText(myRasiDatum.get(0).getContent());
                    }
                }
                if (rasifalTypeAdapter != null) {
                    rasifalTypeAdapter.notifyDataSetChanged();
                }
                if (!isAutoUpdate) {
                    openRasifalBottomSheet();
                }
                if (rasiFalJson != null) {
                    sharePreferenceValues.saveStringToPreference(mContext, sharePreferenceValues.RASIFAL, rasiFalJson, sharePreferenceValues.RASIFAL_PREFERENCE);
                }
                FeaturesFragment.super.performHideDialog();
                showToast("Updated");
            }


            @Override
            public void onFail(String errMsg) {
                showToast(errMsg);
                FeaturesFragment.super.performHideDialog();

            }
        });

    }

    private void getGoldPriceFromCloud(boolean isAutoUpdate) {
        FeaturesNetworkCalls.getGoldPriceFromCloud(FeaturesVariables.FeaturesTypesEnum.FEATURES_GOLD_SILVER, new GoldDatabaseUtils.OnDatabaseChangeListener() {
            @Override
            public void onSuccess(GoldModelWrapper goldModelWrapper) {
                if (goldModelWrapper != null) {
                    if (goldModelWrapper.goldDataModels.size() > 0) {
                        updateGoldSilverView(goldModelWrapper.goldDataModels);

                    }
                    if (!isAutoUpdate) {
                        openGoldBottomSheet();
                    }
                }
                FeaturesFragment.super.performHideDialog();
                showToast("Updated");
            }

            @Override
            public void onFail(String errMsg) {
                showToast(errMsg);
                FeaturesFragment.super.performHideDialog();

            }
        });
    }

    private void showError(String message) {
        Snackbar snackbar = Snackbar
                .make(layout_features_main, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void coronaCountryWiseCall() {
        CoronaNetworkCall.getContryWiseCoronaState(NewsVariables.NewsOrCoronaTypesEnum.CORONA_COUNTRY_WISE_STATE, new CoroCountryWiseDatabaseUtils.OnDatabaseChangeListener() {
            @Override
            public void onSuccess(CountryWiseListModel countryWiseListModelll) {


            }

            @Override
            public void onFail(String errMsg) {


            }
        });
    }

    private void coronaWorldWideCall() {
        if (isNetworkAvailable()) {
            CoronaNetworkCall.getWorldCoronaStatus(NewsVariables.NewsOrCoronaTypesEnum.CORONA_WORLD_CASES, new CoroCaseDbUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(CaseDataModel caseDataModel) {
                    setCoronLayoutButton();
                }

                @Override
                public void onFail(String errMsg) {

                }
            });

        }
    }
}
