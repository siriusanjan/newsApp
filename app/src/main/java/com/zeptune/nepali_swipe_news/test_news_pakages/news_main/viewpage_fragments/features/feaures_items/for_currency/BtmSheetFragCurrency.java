package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.feaures_items.for_currency;

import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.zeptune.nepali_swipe_news.BuildConfig;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.adapter.inUse.CurrencyAdapter;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.currencyDataModel.CurrencyDatum;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.currencyDataModel.DataCurrencyModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesNetworkCalls;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CurrencyDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.DateDatabaseUtils;

import java.util.ArrayList;

public class BtmSheetFragCurrency extends BaseCurrencyFragment implements View.OnClickListener {

    public BtmSheetFragCurrency() {

    }

    private View view;
    private RecyclerView recycler_currency_recycler_view;
    private AdView adsCurrency;

    private Context mContext;
    private ArrayList<CurrencyDatum> currencyData;
    private CurrencyAdapter currencyAdapter;

    ImageView img_currency_close;
    TextView txt_title_rasifal_detail;
    TextView txt_currency_update;
    TextView date;
    String dateToday;
    private FrameLayout currency_loading_screen;

    public static BtmSheetFragCurrency newInstance() {

        return new BtmSheetFragCurrency();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);

    }

    public void setBaseCurrency(BaseCurrencySheetListener baseCurrencySheetListener) {
        super.setBaseCurrencySheetListener(baseCurrencySheetListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            currencyData = getArguments().getParcelableArrayList("currencyData");
            dateToday = getArguments().getString("date");
        }
        view = inflater.inflate(R.layout.frag_currency_view, container, false);

        initUi();
        setUpRecyclerView();
        return view;

    }

    private void initUi() {
        recycler_currency_recycler_view = view.findViewById(R.id.recycler_currency_recycler_view);
        img_currency_close = view.findViewById(R.id.img_currency_close);
        txt_title_rasifal_detail = view.findViewById(R.id.txt_title_rasifal_detail);
        txt_currency_update = view.findViewById(R.id.txt_currency_update);
        adsCurrency = view.findViewById(R.id.adsCurrency);
        currency_loading_screen = view.findViewById(R.id.currency_loading_screen);
        date = view.findViewById(R.id.date);
        currencyData = new ArrayList<>();
        if(getCurrencyModel()!=null) {
            date.setText(getCurrencyModel().getData().getMyDate());
            Log.d("dateToShow", "initUi: " + getCurrencyModel().getData().getMyDate());
            if (currencyData != null && getCurrencyModel() != null) {
                currencyData.addAll(getCurrencyModel().getData().getFetched_data());

            } else if (getCurrencyModel() != null) {
                currencyData = getCurrencyModel().getData().getFetched_data();
            }
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        currencyAdapter = new CurrencyAdapter(currencyData, true, new CurrencyAdapter.CurrencyAdapterListener() {
            @Override
            public void onViewClicked() {

            }
        });
        recycler_currency_recycler_view.setLayoutManager(layoutManager);
        recycler_currency_recycler_view.setAdapter(currencyAdapter);
        loadBannerAds();
        img_currency_close.setOnClickListener(this);
        txt_currency_update.setOnClickListener(this);
    }

    private DataCurrencyModel getCurrencyModel() {

        return CurrencyDatabaseUtils.saveCurrencyDataModel(FeaturesVariables.FeaturesTypesEnum.FEATURES_CURRENCY);
    }

    private void setUpRecyclerView() {
//        adViewCurrency=view.findViewById(R.id.adV)
    }

    private void loadBannerAds() {
        if (BuildConfig.DEBUG) {
            // do something for a debug build
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_test_ads));

        } else {
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_real_ads));

        }
        AdRequest request = new AdRequest.Builder().build();
        adsCurrency.loadAd(request);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_currency_close:
                this.dismiss();
                break;
            case R.id.txt_currency_update:
                //todo currency update
                currency_loading_screen.setVisibility(View.VISIBLE);
                callCurrrencyUpdate();
                break;
        }

    }

    private void callCurrrencyUpdate() {
        FeaturesNetworkCalls.getCurrencyExchangeRate(FeaturesVariables.FeaturesTypesEnum.FEATURES_CURRENCY, new CurrencyDatabaseUtils.OnDatabaseChangeListener() {
            @Override
            public void onSuccess(DataCurrencyModel currencyDataModel) {
                if (currencyDataModel != null) {
                    BtmSheetFragCurrency.super.performOnNewUpdate();
                    date.setText(DateDatabaseUtils.getDate());
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
                currency_loading_screen.setVisibility(View.GONE);
            }

            @Override
            public void onFail(String errMsg) {
                showToast(errMsg);
                currency_loading_screen.setVisibility(View.GONE);
            }
        });
    }

    private void showToast(String errMsg) {
        Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
