package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.bottom_sheet_detail_display;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zeptune.nepali_swipe_news.BuildConfig;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseListModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCountryWiseDatabaseUtils;

import java.util.ArrayList;

public class CoronaDetailBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    public CoronaDetailBottomSheet() {

    }

    private View view;
    private Context mContext;
    int index;
    AdView adCoronaDetailsFragment;
    private ImageView img_corona_detail_close;
    private TextView txt_death,
            txt_death_number,
            txt_cases,
            txt_cases_number,
            txt_total_recovered,
            txt_total_recovered_number,
            txt_new_death,
            txt_new_death_number,
            txt_new_cases,
            txt_new_cases_number,
            txt_serious_critical,
            txt_total_serious_critical_number,
            txt_title_Country_Name,
            txt_statistic_taken_at;


    public static CoronaDetailBottomSheet newInstance(Bundle bundle) {
        CoronaDetailBottomSheet coronaDetailBottomSheet = new CoronaDetailBottomSheet();
        coronaDetailBottomSheet.setArguments(bundle);
        return coronaDetailBottomSheet;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setStyle(DialogFragment.STYLE_NORMAL,R.style.AppBottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            index = getArguments().getInt("index");
        }
        view = inflater.inflate(R.layout.frg_corona_detail, container, false);
        iniObj();
        initUI();
        return view;
    }


    private void iniObj() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();

        if (isNetworkAvailable()) {
            loadBannerAds();
            if (adCoronaDetailsFragment.getVisibility() == View.GONE) {
                adCoronaDetailsFragment.setVisibility(View.VISIBLE);
            }
        } else {
            if (adCoronaDetailsFragment.getVisibility() == View.VISIBLE) {
                adCoronaDetailsFragment.setVisibility(View.GONE);
            }
        }
    }

    private void initUI() {
        adCoronaDetailsFragment = view.findViewById(R.id.ad_corona_detail);
        txt_death = view.findViewById(R.id.txt_death);
        txt_death_number = view.findViewById(R.id.txt_death_number);
        txt_cases = view.findViewById(R.id.txt_cases);
        txt_cases_number = view.findViewById(R.id.txt_cases_number);

        txt_total_recovered = view.findViewById(R.id.txt_total_recovered);
        txt_total_recovered_number = view.findViewById(R.id.txt_total_recovered_number);
        txt_new_death = view.findViewById(R.id.txt_new_death);
        txt_new_death_number = view.findViewById(R.id.txt_new_death_number);
        txt_new_cases = view.findViewById(R.id.txt_new_cases);
        txt_new_cases_number = view.findViewById(R.id.txt_new_cases_number);
        txt_serious_critical = view.findViewById(R.id.txt_serious_critical);
        txt_total_serious_critical_number = view.findViewById(R.id.txt_total_serious_critical_number);
        txt_statistic_taken_at = view.findViewById(R.id.txt_statistic_taken_at);
        txt_title_Country_Name = view.findViewById(R.id.txt_title_Country_Name);
        txt_title_Country_Name.setOnClickListener(this);

    }

    public void setData() {
        CountryWiseListModel countryWiseListModel = CoroCountryWiseDatabaseUtils.saveCountryWiseModel(NewsVariables.NewsOrCoronaTypesEnum.CORONA_COUNTRY_WISE_STATE);
        if (countryWiseListModel != null) {
            if (countryWiseListModel.countries_stat != null) {
                ArrayList<CountryWiseDataModel> countryWiseDataModelList = countryWiseListModel.countries_stat;
                if (countryWiseDataModelList.size() > index) {
                    CountryWiseDataModel countryWiseDataModel = countryWiseDataModelList.get(index);
                    txt_cases_number.setText(countryWiseDataModel.cases);
                    txt_death_number.setText(countryWiseDataModel.deaths);
                    txt_new_cases_number.setText(countryWiseDataModel.new_cases);
                    txt_new_death_number.setText(countryWiseDataModel.new_deaths);
                    txt_total_recovered_number.setText(countryWiseDataModel.total_recovered);
                    txt_total_serious_critical_number.setText(countryWiseDataModel.serious_critical);
                    txt_title_Country_Name.setText(countryWiseDataModel.country_name);

                }
                String takenDate=countryWiseListModel.statistic_taken_at.concat(" GMT");
                txt_statistic_taken_at.setText(takenDate);


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
        adCoronaDetailsFragment.loadAd(request);
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

    private void showError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_title_Country_Name:
                this.dismiss();
                break;
        }
    }
}
