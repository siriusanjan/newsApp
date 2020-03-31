package com.zeptune.nepali_swipe_news.dialogue_activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.adapter.inUse.FeaturesAdapter;
import com.zeptune.nepali_swipe_news.retrofit.retofit.ServiceFactory;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DataRasiFalModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.GoldDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.News_Variabls;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.RasiFalDatum;
import com.zeptune.nepali_swipe_news.share_preferance.SharePreferenceValues;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isOnRasifalContext = false, isOnGoldContext = false;
    private ArrayList<RasiFalDatum> myRasiDatum;
    private ArrayList<GoldDataModel> myGoldSilverList;
    //rasifalViews
    private RecyclerView rasifal_detail_recycler_view;
    private TextView txt_rasifal_update;
    private RecyclerView.LayoutManager layoutManager;
    private FeaturesAdapter rasifalDetailAdapter;
    private SharePreferenceValues sharePreferenceValues;
    private Gson gson;
    //gold views
    private ImageView img_gold_close;
    private TextView txt_gold_update, txt_hallmark_gold, txt_hallmark_gold_price, txt_Tajabi_gold, txt_Tajabi_gold_price, txt_Hallmark_gold_gram, txt_Hallmark_gold_gram_price, txt_Tajabi_gold_gram, txt_Tajabi_gold_gram_price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(true);
        if (getIntent() != null) {
            if (TextUtils.equals(getIntent().getExtras().getString("type"), News_Variabls.TYPE.RASIFAL_TYPE.toString())) {
                isOnRasifalContext = true;
                initRasifalViews();

            } else if (TextUtils.equals(getIntent().getExtras().getString("type"), News_Variabls.TYPE.GOLD_TYPE.toString())) {
                isOnGoldContext = true;
                initGoldViews();
            } else {
                finish();
            }
        }
    }

    private void initGoldViews() {
        setContentView(R.layout.frg_gold_detail_view);
        initGoldUI();
        iniObj();
        getAllTheGoldIntent();


    }

    private void getAllTheGoldIntent() {
        myGoldSilverList = getIntent().getExtras().getParcelableArrayList("goldData");
        Log.d("rasiArrayList", "getAllTheRasiIntent: " + myGoldSilverList.size());
        assignValueToGold(myGoldSilverList);
    }

    private void initGoldUI() {
        txt_gold_update = findViewById(R.id.txt_gold_update);
        img_gold_close = findViewById(R.id.img_gold_close);
        txt_hallmark_gold = findViewById(R.id.txt_hallmark_gold);
        txt_hallmark_gold_price = findViewById(R.id.txt_hallmark_gold_price);
        txt_Tajabi_gold = findViewById(R.id.txt_Tajabi_gold);
        txt_Tajabi_gold_price = findViewById(R.id.txt_Tajabi_gold_price);
        txt_Hallmark_gold_gram = findViewById(R.id.txt_Hallmark_gold_gram);
        txt_Hallmark_gold_gram_price = findViewById(R.id.txt_Hallmark_gold_gram_price);
        txt_Tajabi_gold_gram = findViewById(R.id.txt_Tajabi_gold_gram);
        txt_Tajabi_gold_gram_price = findViewById(R.id.txt_Tajabi_gold_gram_price);
        txt_gold_update.setOnClickListener(this);
        img_gold_close.setOnClickListener(this);
    }

    private void assignValueToGold(ArrayList<GoldDataModel> arrayList) {
        txt_hallmark_gold.setText(arrayList.get(0).title);
        txt_hallmark_gold_price.setText(arrayList.get(0).value);

        txt_Tajabi_gold.setText(arrayList.get(1).title);
        txt_Tajabi_gold_price.setText(arrayList.get(1).value);

        txt_Hallmark_gold_gram.setText(arrayList.get(3).title);
        txt_Hallmark_gold_gram_price.setText(arrayList.get(3).value);

        txt_Tajabi_gold_gram.setText(arrayList.get(4).title);
        txt_Tajabi_gold_gram_price.setText(arrayList.get(4).value);
    }

    private void initRasifalViews() {
        setContentView(R.layout.frg_rasifal_detail_view);
        getAllTheRasiIntent();
        initRasiFalUi();
        iniObj();
        setUpRasifalDetailRecyclerView();
    }

    private void iniObj() {
        sharePreferenceValues = new SharePreferenceValues();
        gson = new Gson();
    }

    private void setUpRasifalDetailRecyclerView() {
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rasifalDetailAdapter = new FeaturesAdapter(myRasiDatum, this, News_Variabls.TYPE.RASIFAL_TYPE, FeaturesAdapter.RASIFAL_TYPE_BACK);
        rasifal_detail_recycler_view.setLayoutManager(layoutManager);
        rasifal_detail_recycler_view.setAdapter(rasifalDetailAdapter);

    }

    private void initRasiFalUi() {
        rasifal_detail_recycler_view = findViewById(R.id.rasifal_detail_recycler_view);
        txt_rasifal_update = findViewById(R.id.txt_rasifal_update);
        findViewById(R.id.img_rasifal_close).setOnClickListener(this);
        txt_rasifal_update.setOnClickListener(this);
    }

    private void getAllTheRasiIntent() {
        myRasiDatum = getIntent().getExtras().getParcelableArrayList("rasiData");
        Log.d("rasiArrayList", "getAllTheRasiIntent: " + myRasiDatum.size());
    }

    private DataRasiFalModel getRasiFals() {
        String rasifalJson = sharePreferenceValues.getSavedString(ShowDetailActivity.this, sharePreferenceValues.RASIFAL, sharePreferenceValues.RASIFAL_PREFERENCE);
        Log.d("Myrasifalsize", "Adapter: " + rasifalJson);
        return gson.fromJson(rasifalJson, DataRasiFalModel.class);
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
                    if (rasifalDetailAdapter != null) {
                        Log.d("Myrasifalsize", "onResponse: checkadapter null " + rasiFalJson);
                        rasifalDetailAdapter.notifyDataSetChanged();
                    }


                    if (rasiFalJson != null) {
                        sharePreferenceValues.saveStringToPreference(ShowDetailActivity.this, sharePreferenceValues.RASIFAL, rasiFalJson, sharePreferenceValues.RASIFAL_PREFERENCE);
                    }
                    Toast.makeText(ShowDetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataRasiFalModel> call, Throwable t) {
                Log.d("myrasifall", "onResponse: " + call.request().url());
                Toast.makeText(ShowDetailActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();

            }
        }));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getGoldPriceFromCloud() {
        Call<ArrayList<GoldDataModel>> latestRasifal = ServiceFactory.getRasifals().getGoldPrice();
        latestRasifal.enqueue((new Callback<ArrayList<GoldDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GoldDataModel>> call, Response<ArrayList<GoldDataModel>> response) {
                Log.d("myGoldPrice", "onResponse: " + response.body());
                Log.d("myGoldPrice", "onResponse: " + call.request().url());
                Log.d("myGoldPrice", "onResponse: " + response.body().size());
                if (response.body() != null) {
                    Gson gson = new Gson();
                    ArrayList<GoldDataModel> mylist = response.body();
                    String goldSilverJson = gson.toJson(mylist);
                    if (myGoldSilverList != null && mylist != null) {
                        myGoldSilverList.clear();
                        myGoldSilverList.addAll(mylist);
                    } else if (getRasiFals() != null) {
                        myGoldSilverList = mylist;
                    }
//                    if (rasifalTypeAdapter != null) {
//                        Log.d("Myrasifalsize", "onResponse: checkadapter null " + goldSilverJson);
//                        rasifalTypeAdapter.notifyDataSetChanged();
//                    }

                    assignValueToGold(mylist);
                    Toast.makeText(ShowDetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    if (goldSilverJson != null) {
                        sharePreferenceValues.saveStringToPreference(ShowDetailActivity.this, sharePreferenceValues.GOLD_SILVER, goldSilverJson, sharePreferenceValues.GOLD_SILVER_PREFERENCE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GoldDataModel>> call, Throwable t) {
                Log.d("myGoldPrice", "onFailed: " + call.request().url());
                Log.d("myGoldPrice", "onFailed: " + t.getMessage());
                Log.d("myGoldPrice", "onFailed: " + t.getCause());
                Toast.makeText(ShowDetailActivity.this, "Failed To Update", Toast.LENGTH_SHORT).show();


            }
        }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_rasifal_update:
                if (isNetworkAvailable()) {
                    getRasifalFromCloud();
                } else {
                    Toast.makeText(this, "Network not Available", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.txt_gold_update:
                if (isNetworkAvailable()) {
                    getGoldPriceFromCloud();
                } else {
                    Toast.makeText(this, "Network not Available", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.img_gold_close:
                finish();
                break;
            case R.id.img_rasifal_close:
                finish();
                break;
        }
    }

}
