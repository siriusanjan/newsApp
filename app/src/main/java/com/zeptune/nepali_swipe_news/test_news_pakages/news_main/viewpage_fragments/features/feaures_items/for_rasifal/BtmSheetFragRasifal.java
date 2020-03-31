package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.for_rasifal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.zeptune.nepali_swipe_news.BuildConfig;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.adapter.inUse.FeaturesAdapter;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DataRasiFalModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.News_Variabls;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.RasiFalDatum;
import com.zeptune.nepali_swipe_news.share_preferance.SharePreferenceValues;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesNetworkCalls;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.RasifalDatabaseUtils;

import java.util.ArrayList;

public class BtmSheetFragRasifal extends BaseRasifalSheet implements View.OnClickListener {

    public BtmSheetFragRasifal() {

    }

    private View view;
    private RecyclerView rasifal_detail_recycler_view;
    private TextView txt_rasifal_update;
    private RecyclerView.LayoutManager layoutManager;
    private FeaturesAdapter rasifalDetailAdapter;
    private AdView adsRasifal;
    private SharePreferenceValues sharePreferenceValues;
    private Gson gson;
    private ArrayList<RasiFalDatum> myRasiDatum;
    private RelativeLayout card_main_rasifal;
    private FrameLayout rsifla_loading_screen;
    private TextView txt_date;
    private String date;


    private Context mContext;

    public static BtmSheetFragRasifal newInstance(Bundle bundle) {
        BtmSheetFragRasifal btmSheetFragRasifal = new BtmSheetFragRasifal();
        btmSheetFragRasifal.setArguments(bundle);

        return btmSheetFragRasifal;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);

    }

    public void setRasifalBase(BaseRasifalSheetListener baseRasifalSheetListener) {
        super.setBaseRasifalSheetListener(baseRasifalSheetListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            myRasiDatum = getArguments().getParcelableArrayList("rasiData");
            date = getArguments().getString("date");
        }
        view = inflater.inflate(R.layout.frg_rasifal_detail_view, container, false);
        initRasiFalUi();
        iniObj();
        setUpRasifalDetailRecyclerView();

        return view;

    }

    private void loadBannerAds() {
        if (BuildConfig.DEBUG) {
            // do something for a debug build
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_test_ads));

        } else {
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_real_ads));

        }
        AdRequest request = new AdRequest.Builder().build();
        adsRasifal.loadAd(request);
    }

    private void initRasiFalUi() {
        rasifal_detail_recycler_view = view.findViewById(R.id.rasifal_detail_recycler_view);
        txt_rasifal_update = view.findViewById(R.id.txt_rasifal_update);
        card_main_rasifal = view.findViewById(R.id.card_main_rasifal);
        txt_date = view.findViewById(R.id.date);
        rsifla_loading_screen = view.findViewById(R.id.rsifla_loading_screen);
        adsRasifal = view.findViewById(R.id.adsRasifal);
        if (date != null) {
            txt_date.setText(date);
        }
        view.findViewById(R.id.img_rasifal_close).setOnClickListener(this);
        txt_rasifal_update.setOnClickListener(this);
    }

    private void setUpRasifalDetailRecyclerView() {
        layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rasifalDetailAdapter = new FeaturesAdapter(myRasiDatum, mContext, News_Variabls.TYPE.RASIFAL_TYPE, FeaturesAdapter.RASIFAL_TYPE_BACK);
        rasifal_detail_recycler_view.setLayoutManager(layoutManager);
        rasifal_detail_recycler_view.setAdapter(rasifalDetailAdapter);
    }

    private void iniObj() {
        sharePreferenceValues = new SharePreferenceValues();
        gson = new Gson();
        if (isNetworkAvailable()) {
            loadBannerAds();
            if (adsRasifal.getVisibility() == View.GONE) {

                adsRasifal.setVisibility(View.VISIBLE);
            }
        } else {
            if (adsRasifal.getVisibility() == View.VISIBLE) {

                adsRasifal.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txt_rasifal_update:
                if (isNetworkAvailable()) {
                    rsifla_loading_screen.setVisibility(View.VISIBLE);
                    getRasifalFromCloud();
                } else {
                    Toast.makeText(mContext, "Network not Available", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.img_rasifal_close:
                this.dismiss();
                break;
        }
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

    private DataRasiFalModel getRasiFals() {
        String rasifalJson = sharePreferenceValues.getSavedString(mContext, sharePreferenceValues.RASIFAL, sharePreferenceValues.RASIFAL_PREFERENCE);
        return gson.fromJson(rasifalJson, DataRasiFalModel.class);
    }

    private void getRasifalFromCloud() {
        FeaturesNetworkCalls.getRasifalFromCloud(FeaturesVariables.FeaturesTypesEnum.FEATURES_RASIFAL, new RasifalDatabaseUtils.OnDatabaseChangeListener() {
            @Override
            public void onSuccess(DataRasiFalModel dataRasiFalModel) {
                if (dataRasiFalModel != null) {
                    if (dataRasiFalModel.getData() != null) {
                        if (dataRasiFalModel.getData().getFetched() != null) {
                            if (dataRasiFalModel.getData().getFetched().size() > 0) {
                                BtmSheetFragRasifal.super.performNewUpdate();
                                if (myRasiDatum != null) {

                                    txt_date.setText(dataRasiFalModel.data.getMyDate());
                                    myRasiDatum.clear();
                                    myRasiDatum.addAll(dataRasiFalModel.getData().getFetched());
                                    rasifalDetailAdapter.notifyDataSetChanged();
                                } else {
                                    txt_date.setText(dataRasiFalModel.data.getMyDate());
                                    myRasiDatum = dataRasiFalModel.getData().getFetched();
                                    rasifalDetailAdapter.notifyDataSetChanged();
                                }
                                showError("Updated");
                            } else {
                                showError("Null Data");

                            }
                        } else {
                            showError("Null Data");

                        }
                    } else {
                        showError("Null Data");
                    }
                }
                rsifla_loading_screen.setVisibility(View.GONE);
            }

            @Override
            public void onFail(String errMsg) {
                showError(errMsg);
                rsifla_loading_screen.setVisibility(View.GONE);
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
}
