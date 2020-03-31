package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.features_gold_silver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.zeptune.nepali_swipe_news.BuildConfig;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.GoldDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.GoldModelWrapper;
import com.zeptune.nepali_swipe_news.share_preferance.SharePreferenceValues;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesNetworkCalls;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.GoldDatabaseUtils;

import java.util.ArrayList;

public class BtmSheetFragGoldPrice extends BaseGoldSilverSheet implements View.OnClickListener {

    public BtmSheetFragGoldPrice() {

    }

    private View view;
    //gold views
    private ImageView img_gold_close;
    private TextView txt_gold_update, txt_hallmark_gold, txt_hallmark_gold_price, txt_Tajabi_gold, txt_Tajabi_gold_price, txt_Hallmark_gold_gram, txt_Hallmark_gold_gram_price, txt_Tajabi_gold_gram, txt_Tajabi_gold_gram_price;
    private Context mContext;
    private SharePreferenceValues sharePreferenceValues;
    private ArrayList<GoldDataModel> myGoldSilverList;
    private Gson gson;
    private AdView adsGoldSilver;
    private FrameLayout gold_loading_screen;
    private TextView txt_Silver_tola, txt_silver_tola_price, txt_Silver_gram, txt_silver_gram_price;


    public static BtmSheetFragGoldPrice newInstance(Bundle bundle) {
        BtmSheetFragGoldPrice btmSheetFragGoldPrice = new BtmSheetFragGoldPrice();
        btmSheetFragGoldPrice.setArguments(bundle);
        return btmSheetFragGoldPrice;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        setStyle(DialogFragment.STYLE_NORMAL,R.style.AppBottomSheetDialogTheme);

    }

    public void setBaseGoldSheet(BaseGoldSheetListener baseGoldSheet) {
        super.setBaseGoldSheetListener(baseGoldSheet);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            myGoldSilverList = getArguments().getParcelableArrayList("goldData");
        }
        view = inflater.inflate(R.layout.frg_gold_detail_view, container, false);
        iniObj();
        initGoldUI();
        return view;
    }


    private void iniObj() {
        sharePreferenceValues = new SharePreferenceValues();
        gson = new Gson();
    }

    private void initGoldUI() {
        txt_gold_update = view.findViewById(R.id.txt_gold_update);
        gold_loading_screen = view.findViewById(R.id.gold_loading_screen);
        img_gold_close = view.findViewById(R.id.img_gold_close);
        txt_hallmark_gold = view.findViewById(R.id.txt_hallmark_gold);
        txt_hallmark_gold_price = view.findViewById(R.id.txt_hallmark_gold_price);
        txt_Tajabi_gold = view.findViewById(R.id.txt_Tajabi_gold);
        txt_Tajabi_gold_price = view.findViewById(R.id.txt_Tajabi_gold_price);
        txt_Hallmark_gold_gram = view.findViewById(R.id.txt_Hallmark_gold_gram);
        txt_Hallmark_gold_gram_price = view.findViewById(R.id.txt_Hallmark_gold_gram_price);
        txt_Tajabi_gold_gram = view.findViewById(R.id.txt_Tajabi_gold_gram);
        txt_Tajabi_gold_gram_price = view.findViewById(R.id.txt_Tajabi_gold_gram_price);
        adsGoldSilver = view.findViewById(R.id.adsGoldSilver);
        txt_Silver_tola = view.findViewById(R.id.txt_Silver_tola);
        txt_silver_tola_price = view.findViewById(R.id.txt_silver_tola_price);
        txt_Silver_gram = view.findViewById(R.id.txt_Silver_gram);
        txt_silver_gram_price = view.findViewById(R.id.txt_silver_gram_price);


        txt_gold_update.setOnClickListener(this);
        img_gold_close.setOnClickListener(this);
        assignValueToGold(myGoldSilverList);
        if (isNetworkAvailable()) {
            loadBannerAds();
            if (adsGoldSilver.getVisibility() == View.GONE) {
                adsGoldSilver.setVisibility(View.VISIBLE);
            }
        } else {
            if (adsGoldSilver.getVisibility() == View.VISIBLE) {
                adsGoldSilver.setVisibility(View.GONE);
            }
        }
    }

    private void loadBannerAds() {
        if (BuildConfig.DEBUG) {
            // do something for a debug build
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_test_ads));

        } else {
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_real_ads));

        }
        AdRequest request = new AdRequest.Builder().build();
        adsGoldSilver.loadAd(request);
    }

    private void assignValueToGold(ArrayList<GoldDataModel> arrayList) {
        if (arrayList.size() > 0) {

            txt_hallmark_gold.setText(arrayList.get(0).title);
            txt_hallmark_gold_price.setText(arrayList.get(0).value);

            txt_Tajabi_gold.setText(arrayList.get(1).title);
            txt_Tajabi_gold_price.setText(arrayList.get(1).value);

            txt_Hallmark_gold_gram.setText(arrayList.get(3).title);
            txt_Hallmark_gold_gram_price.setText(arrayList.get(3).value);

            txt_Tajabi_gold_gram.setText(arrayList.get(4).title);
            txt_Tajabi_gold_gram_price.setText(arrayList.get(4).value);


            txt_Silver_tola.setText(arrayList.get(2).title);
            txt_silver_tola_price.setText(arrayList.get(2).value);

            txt_Silver_gram.setText(arrayList.get(5).title);
            txt_silver_gram_price.setText(arrayList.get(5).value);

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private GoldDataModel getGolds() {
        String goldJson = sharePreferenceValues.getSavedString(mContext, sharePreferenceValues.GOLD_SILVER, sharePreferenceValues.GOLD_SILVER_PREFERENCE);
        Log.d("Myrasifalsize", "Adapter: " + goldJson);
        return gson.fromJson(goldJson, GoldDataModel.class);
    }

    private void getGoldPriceFromCloud() {
        FeaturesNetworkCalls.getGoldPriceFromCloud(FeaturesVariables.FeaturesTypesEnum.FEATURES_GOLD_SILVER, new GoldDatabaseUtils.OnDatabaseChangeListener() {
            @Override
            public void onSuccess(GoldModelWrapper goldModelWrapper) {
                if (goldModelWrapper != null) {
                    BtmSheetFragGoldPrice.super.performOnNewUpdate();
                    if (goldModelWrapper.goldDataModels.size() > 0) {
                        assignValueToGold(goldModelWrapper.goldDataModels);
                        showError("Updated");
                    } else {
                        showError("Null Data");
                    }
                } else {
                    showError("Null Data");

                }
                gold_loading_screen.setVisibility(View.GONE);
            }

            @Override
            public void onFail(String errMsg) {
                showError(errMsg);
                gold_loading_screen.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_gold_update:
                if (isNetworkAvailable()) {
                    gold_loading_screen.setVisibility(View.VISIBLE);
                    getGoldPriceFromCloud();
                } else {
                    Toast.makeText(mContext, "Network not Available", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.img_gold_close:
                this.dismiss();
                break;
        }
    }

    private void showError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

    }
}
