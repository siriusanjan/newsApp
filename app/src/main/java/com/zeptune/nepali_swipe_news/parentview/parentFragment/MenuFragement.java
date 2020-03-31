package com.zeptune.nepali_swipe_news.parentview.parentFragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.BuildConfig;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.adapter.inUse.Adapter;
import com.zeptune.nepali_swipe_news.all_interfaces.recyclerView_MenuInterface.RasifalInterface;
import com.zeptune.nepali_swipe_news.date_converter.converter.ConvertAdToBs;
import com.zeptune.nepali_swipe_news.date_converter.interface_date.NepaliDateInterface;
import com.zeptune.nepali_swipe_news.models.CategoryDataModel;
import com.zeptune.nepali_swipe_news.models.CategoryListDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.ServiceFactory;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DataRasiFalModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.RasiFalDatum;
import com.zeptune.nepali_swipe_news.share_preferance.SharePreferenceValues;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragement extends Fragment implements NepaliDateInterface, RasifalInterface {

    //Variables
    private ArrayList<CategoryDataModel> arrayList;
    private String currentEngDay, currentEngMonth, currentEngYear;
    private ArrayList<RasiFalDatum> myRasiDatum;
    //Class
    private Context mContext;
    private Date date;
    private DateFormat dayFormat, monthFormat;
    private RecyclerView.LayoutManager categoryListLayoutManager, rasifalLayoutManager;
    private Gson gson;
    private Adapter adapter;
    private SharePreferenceValues sharePreferenceValues;
    private DataRasiFalModel thisDataRasiModel;
    private Adapter rasifalTypeAdapter;
    //View
    private View view;
    private RecyclerView all_category_recyclerView, rasifal_recycler_view;
    private AdView mAdView;
    private TextView txt_Nepali_Day, txt_Nepali_Month, txt_Nepali_Year, txt_day_of_the_week;

    public MenuFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu_fragement, container, false);
        all_category_recyclerView = view.findViewById(R.id.all_category_recyclerview);
        mAdView = view.findViewById(R.id.adViewMenu);
        getRasifalFromCloud();
        initUi();
        initObj();
        getData();
        setUpDateUi();
        loadBannerAds();
        setUpRecyclerViewForCategory();

        return view;
    }


    private void initUi() {
        all_category_recyclerView = view.findViewById(R.id.all_category_recyclerview);
        rasifal_recycler_view = view.findViewById(R.id.rasifal_recycler_view);
        txt_Nepali_Day = view.findViewById(R.id.txt_Nepali_Day);
        txt_Nepali_Month = view.findViewById(R.id.txt_Nepali_Month);
        txt_Nepali_Year = view.findViewById(R.id.txt_Nepali_Year);
        txt_day_of_the_week = view.findViewById(R.id.txt_day_of_the_week);
//        rasifal_recycler_view.setNestedScrollingEnabled(false);
        all_category_recyclerView.setNestedScrollingEnabled(false);

        mAdView = view.findViewById(R.id.adViewMenu);
    }

    private void initObj() {
        myRasiDatum = new ArrayList<>();
        thisDataRasiModel = new DataRasiFalModel();
        date = new Date();
        dayFormat = new SimpleDateFormat("dd");
        monthFormat = new SimpleDateFormat("MM");
        categoryListLayoutManager = new GridLayoutManager(mContext, 3);
        rasifalLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        sharePreferenceValues = new SharePreferenceValues();
        arrayList = new ArrayList<>();
        gson = new Gson();


    }

    private DataRasiFalModel getRasiFals() {
        String rasifalJson = sharePreferenceValues.getSavedString(mContext, sharePreferenceValues.RASIFAL, sharePreferenceValues.RASIFAL_PREFERENCE);
        Log.d("Myrasifalsize", "Adapter: " + rasifalJson);
        return gson.fromJson(rasifalJson, DataRasiFalModel.class);
    }

    private void displayRasifal(String RasiName, String rasiSubTitle, String description) {
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.rasifal_custom_dialog);
        TextView title = dialog.findViewById(R.id.txt_title_raifal);
        TextView contentDescription = dialog.findViewById(R.id.txt_rasifal_content);
        Button btnClose = dialog.findViewById(R.id.bt_close_rasifal_dialogue);

// set the custom dialog components - text, image and button

        title.setText(rasiSubTitle);
        contentDescription.setText(description);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setUpDateUi() {
        currentEngDay = dayFormat.format(date);
        currentEngMonth = monthFormat.format(date);
        currentEngYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        new ConvertAdToBs(currentEngYear, monthFormat.format(date), currentEngDay, this);

    }

    private void setUpRecyclerViewForCategory() {
        Type categoryType = new TypeToken<CategoryListDataModel>() {
        }.getType();
        CategoryListDataModel categoryListDataModel = gson.fromJson(loadJSONFromAsset(), categoryType);
        arrayList = categoryListDataModel.getData();
        Adapter categoryTypeAdapter = new Adapter(arrayList, mContext, Adapter.CATEGOTY_TYPE);
        rasifalTypeAdapter = new Adapter(thisDataRasiModel, myRasiDatum, mContext, Adapter.RASIFAL_TYPE, this);
        all_category_recyclerView.setLayoutManager(categoryListLayoutManager);
        all_category_recyclerView.setAdapter(categoryTypeAdapter);
        rasifal_recycler_view.setLayoutManager(rasifalLayoutManager);
        rasifal_recycler_view.setAdapter(rasifalTypeAdapter);
        if (myRasiDatum != null && getRasiFals() != null) {
            myRasiDatum.clear();
            myRasiDatum.addAll(getRasiFals().getData().getFetched());
        } else if (getRasiFals() != null) {
            myRasiDatum = getRasiFals().getData().getFetched();
        }
        rasifalTypeAdapter.notifyDataSetChanged();
    }

    private void loadBannerAds() {
        if (BuildConfig.DEBUG) {
            // do something for a debug build
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_test_ads));
//            MobileAds.initialize(getActivity(),mContext.getString(R.string.banner_test_ads));
        } else {
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_real_ads));

        }
        AdRequest request = new AdRequest.Builder().build();
        mAdView.loadAd(request);
    }

    private void getData() {
        Call<String> categoryCall = ServiceFactory.getAllCategory().getAllCategory();

        categoryCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("mydad", "onResponse: " + call.request().url());
                Log.d("mydad", "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("mydad", "onFailure: " + call.request().url());
                Log.d("mydad", "onFailure: " + t.getMessage());
                Log.d("mydad", "onFailure: " + t.getLocalizedMessage());
                Log.d("mydad", "onFailure: " + t.getCause());

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;

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

    @Override
    public void setConvertedDate(String nepYear, String nepMonth, String nepNumberOfMonth, String nepDay, String dayOfWeek, boolean isNepali) {
        txt_Nepali_Year.setText(nepYear);
        txt_Nepali_Month.setText(nepMonth.concat(" "));
        txt_Nepali_Day.setText(nepDay.concat(", "));
        txt_day_of_the_week.setText(dayOfWeek.concat(", "));
    }

    @Override
    public void getRasifals(String rasiTitle, String rasiSubTitle, String description) {
        if (!TextUtils.isEmpty(rasiSubTitle) && !TextUtils.isEmpty(description)) {
            displayRasifal(rasiTitle, rasiSubTitle, description);
        }

    }

    private void getRasifalFromCloud() {
        Call<DataRasiFalModel> latestRasifal = ServiceFactory.getRasifals().getRasiFal();
        latestRasifal.enqueue((new Callback<DataRasiFalModel>() {
            @Override
            public void onResponse(Call<DataRasiFalModel> call, Response<DataRasiFalModel> response) {
                Log.d("myrasifall", "onResponse: " + response.body());
                Log.d("myrasifall", "onResponse: " + call.request().url());
                Log.d("myrasifall", "onResponse: " + response.body().data.getFetched().size());
                if (response.body() != null) {
                    Gson gson = new Gson();
                    DataRasiFalModel dataRasiFalModel = response.body();
                    String rasiFalJson = gson.toJson(dataRasiFalModel);
                    if (myRasiDatum != null && dataRasiFalModel != null) {
                        myRasiDatum.clear();
                        myRasiDatum.addAll(dataRasiFalModel.getData().getFetched());
                    } else if (getRasiFals() != null) {
                        myRasiDatum = dataRasiFalModel.getData().getFetched();
                    }
                    if (rasifalTypeAdapter != null) {
                        Log.d("Myrasifalsize", "onResponse: checkadapter null " + rasiFalJson);
                        rasifalTypeAdapter.notifyDataSetChanged();
                    }


                    if (rasiFalJson != null) {
                        sharePreferenceValues.saveStringToPreference(mContext, sharePreferenceValues.RASIFAL, rasiFalJson, sharePreferenceValues.RASIFAL_PREFERENCE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DataRasiFalModel> call, Throwable t) {
                Log.d("myrasifall", "onResponse: " + call.request().url());

            }
        }));
    }

}
