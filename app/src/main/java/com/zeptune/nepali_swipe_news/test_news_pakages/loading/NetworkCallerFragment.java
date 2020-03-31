package com.zeptune.nepali_swipe_news.test_news_pakages.loading;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DataRasiFalModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.GoldModelWrapper;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.currencyDataModel.DataCurrencyModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesNetworkCalls;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsNetworkCalls;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CurrencyDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.GoldDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.RasifalDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.TestNewsDatabaseUtils;

import java.util.ArrayList;

public class NetworkCallerFragment extends BaseNetworkCallFragment implements View.OnClickListener {
    public NetworkCallerFragment() {
    }

   private int mCurCheckPosition = 11;
    private Context mContext;
    private FrameLayout loading_screen_wrapper;
    private View mainView;
    private ArrayList<NewsVariables.NewsOrCoronaTypesEnum> failedNetworkCallList;

    private LinearLayout oops_wrapper, loading_wrapper;
    private TextView txt_loading_text, txt_skip_loading, txt_oops_err_txt;
    private Button btn_reload_loading;
    private NetworkCallStatusListener networkCallStatusListener;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.frag_network_caller, container, false);

        initUi();
        return mainView;
    }

    public void setUpCall(NetworkCallStatusListener networkCallStatusListener) {
        super.setUpNetworkCall(networkCallStatusListener);
    }

    private void initUi() {
        loading_screen_wrapper = mainView.findViewById(R.id.loading_screen_wrapper);
        oops_wrapper = mainView.findViewById(R.id.oops_wrapper);
        loading_wrapper = mainView.findViewById(R.id.loading_wrapper);
        txt_loading_text = mainView.findViewById(R.id.txt_loading_text);
        txt_skip_loading = mainView.findViewById(R.id.txt_skip_loading);
        btn_reload_loading = mainView.findViewById(R.id.btn_reload_loading);
        txt_oops_err_txt = mainView.findViewById(R.id.txt_oops_err_txt);
        failedNetworkCallList = new ArrayList<>();
        btn_reload_loading.setOnClickListener(this);
        txt_skip_loading.setOnClickListener(this);
        callAllCategoryNews();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }
    }

    private void callAllCategoryNews() {
        if (isNetworkAvailable()) {
            FeaturesNetworkCalls.getGoldPriceFromCloud(FeaturesVariables.FeaturesTypesEnum.FEATURES_GOLD_SILVER, new GoldDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(GoldModelWrapper goldModelWrapper) {

                }

                @Override
                public void onFail(String errMsg) {
                    showError(errMsg);

                }
            });
            FeaturesNetworkCalls.getRasifalFromCloud(FeaturesVariables.FeaturesTypesEnum.FEATURES_RASIFAL, new RasifalDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataRasiFalModel dataRasiFalModel) {

                }

                @Override
                public void onFail(String errMsg) {
                    showError(errMsg);

                }
            });

            FeaturesNetworkCalls.getCurrencyExchangeRate(FeaturesVariables.FeaturesTypesEnum.FEATURES_CURRENCY, new CurrencyDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataCurrencyModel currencyDataModel) {
                    Log.d("mycurrencyDat", "onSuccess: " + currencyDataModel.getData().getFetched_data().size());
                }

                @Override
                public void onFail(String errMsg) {
                    Log.d("mycurrencyDat", "onFail: " + errMsg);
                    showError(errMsg);
                }
            });
            NewsNetworkCalls.getAllLatestNews(NewsVariables.NewsOrCoronaTypesEnum.NEWS_All, new
                    TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                        @Override
                        public void onSuccess(DataumListModel dataumListModel) {
                        }

                        @Override
                        public void onFail(String errMSg) {
                            failedNetworkCallList.add(NewsVariables.NewsOrCoronaTypesEnum.NEWS_All);
                            showError(errMSg);
                        }
                    });
            NewsNetworkCalls.getAllCategoryWiseLatestNews(NewsVariables.NewsOrCoronaTypesEnum.News_TOPS, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    Log.d("networkCall", "onSuccess:" + NewsVariables.NewsOrCoronaTypesEnum.News_TOPS + dataumListModel.getData().size());
                }

                @Override
                public void onFail(String errMSg) {
                    failedNetworkCallList.add(NewsVariables.NewsOrCoronaTypesEnum.News_TOPS);
                    showError(errMSg);
                }
            });
            NewsNetworkCalls.getAllCategoryWiseLatestNews(NewsVariables.NewsOrCoronaTypesEnum.News_POLITICS, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    Log.d("networkCall", "onSuccess:" + NewsVariables.NewsOrCoronaTypesEnum.News_POLITICS + dataumListModel.getData().size());
                }

                @Override
                public void onFail(String errMSg) {
                    failedNetworkCallList.add(NewsVariables.NewsOrCoronaTypesEnum.News_POLITICS);
                    showError(errMSg);
                }
            });
            NewsNetworkCalls.getAllCategoryWiseLatestNews(NewsVariables.NewsOrCoronaTypesEnum.News_SPORTS, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    Log.d("networkCall", "onSuccess:" + NewsVariables.NewsOrCoronaTypesEnum.News_SPORTS + dataumListModel.getData().size());
                }

                @Override
                public void onFail(String errMSg) {
                    failedNetworkCallList.add(NewsVariables.NewsOrCoronaTypesEnum.News_SPORTS);
                    showError(errMSg);
                }
            });
            NewsNetworkCalls.getAllCategoryWiseLatestNews(NewsVariables.NewsOrCoronaTypesEnum.News_Technology, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    Log.d("networkCall", "onSuccess:" + NewsVariables.NewsOrCoronaTypesEnum.News_Technology + dataumListModel.getData().size());
                }

                @Override
                public void onFail(String errMSg) {
                    failedNetworkCallList.add(NewsVariables.NewsOrCoronaTypesEnum.News_Technology);
                    showError(errMSg);
                }
            });
            NewsNetworkCalls.getAllCategoryWiseLatestNews(NewsVariables.NewsOrCoronaTypesEnum.News_WORLD, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    Log.d("networkCall", "onSuccess:" + NewsVariables.NewsOrCoronaTypesEnum.News_WORLD + dataumListModel.getData().size());
                }

                @Override
                public void onFail(String errMSg) {
                    failedNetworkCallList.add(NewsVariables.NewsOrCoronaTypesEnum.News_WORLD);
                    showError(errMSg);
                }
            });
            NewsNetworkCalls.getAllCategoryWiseLatestNews(NewsVariables.NewsOrCoronaTypesEnum.News_BUSINESS, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    Log.d("networkCall", "onSuccess:" + NewsVariables.NewsOrCoronaTypesEnum.News_BUSINESS + dataumListModel.getData().size());
                }

                @Override
                public void onFail(String errMSg) {
                    failedNetworkCallList.add(NewsVariables.NewsOrCoronaTypesEnum.News_BUSINESS);
                    showError(errMSg);
                }
            });
            NewsNetworkCalls.getAllCategoryWiseLatestNews(NewsVariables.NewsOrCoronaTypesEnum.News_ENTERTAINMENT, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    Log.d("networkCall", "onSuccess:" + NewsVariables.NewsOrCoronaTypesEnum.News_ENTERTAINMENT + dataumListModel.getData().size());
                }

                @Override
                public void onFail(String errMSg) {
                    failedNetworkCallList.add(NewsVariables.NewsOrCoronaTypesEnum.News_ENTERTAINMENT);
                    showError(errMSg);
                }
            });
            NewsNetworkCalls.getAllCategoryWiseLatestNews(NewsVariables.NewsOrCoronaTypesEnum.News_HEALTH, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    Log.d("networkCall", "onSuccess:" + NewsVariables.NewsOrCoronaTypesEnum.News_HEALTH + dataumListModel.getData().size());
                }

                @Override
                public void onFail(String errMSg) {
                    failedNetworkCallList.add(NewsVariables.NewsOrCoronaTypesEnum.News_HEALTH);
                    showError(errMSg);

                }
            });
            NewsNetworkCalls.getAllCategoryWiseLatestNews(NewsVariables.NewsOrCoronaTypesEnum.News_UNCATEGORIES, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                @Override
                public void onSuccess(DataumListModel dataumListModel) {
                    Log.d("networkCall", "onSuccess:" + NewsVariables.NewsOrCoronaTypesEnum.News_UNCATEGORIES + dataumListModel.getData().size());
                }

                @Override
                public void onFail(String errMSg) {
                    failedNetworkCallList.add(NewsVariables.NewsOrCoronaTypesEnum.News_UNCATEGORIES);
                    showError(errMSg);
                }
            });
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (failedNetworkCallList.size() > 0) {
                        reCallFailedOne();
                    } else {
                        openMainFragment();
                    }
                }
            }, 5000);
        } else {
            showError("No Internet Connection");
            setErrorUi("No Internet Connection");
        }


    }

    private void setErrorUi(String errText) {
        loading_wrapper.setVisibility(View.GONE);
        oops_wrapper.setVisibility(View.VISIBLE);
        txt_oops_err_txt.setText(errText);
    }

    private void setLoadingUi() {
        loading_wrapper.setVisibility(View.VISIBLE);
        oops_wrapper.setVisibility(View.GONE);
    }

    private void reCallFailedOne() {
        if (isNetworkAvailable()) {
            int failedListSize = failedNetworkCallList.size();
            for (int i = failedListSize - 1; i >= 0; i--) {
                if (failedNetworkCallList.get(i) == NewsVariables.NewsOrCoronaTypesEnum.NEWS_All) {
                    callAllNews(failedNetworkCallList.get(i), i);
                } else {
                    callCategoryNew(failedNetworkCallList.get(i), i);

                }
            }
            failedListSize = failedNetworkCallList.size();
            if (failedListSize > 2) {
                setErrorUi("Not completely Loaded");
            } else {
                openMainFragment();
            }
        } else {
            showError("No Internet Connection");
            setErrorUi("No Internet Connection");

        }

    }

    private void openMainFragment() {
        //todo to open the main fragment with viewpager
        super.performOnSuccess();
    }

    private void callAllNews(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, int position) {
        NewsNetworkCalls.getAllLatestNews(newsOrCoronaTypesEnum, new
                TestNewsDatabaseUtils.OnDatabaseChangeListener() {
                    @Override
                    public void onSuccess(DataumListModel dataumListModel) {
                        Log.d("networkCall", "onSuccess:" + NewsVariables.NewsOrCoronaTypesEnum.NEWS_All + dataumListModel.getData().size());
                    }

                    @Override
                    public void onFail(String errMSg) {
                        failedNetworkCallList.add(newsOrCoronaTypesEnum);
                        showError(errMSg);
                    }
                });
        failedNetworkCallList.remove(position);
    }

    private void callCategoryNew(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, int position) {
        NewsNetworkCalls.getAllCategoryWiseLatestNews(newsOrCoronaTypesEnum, new TestNewsDatabaseUtils.OnDatabaseChangeListener() {
            @Override
            public void onSuccess(DataumListModel dataumListModel) {
                Log.d("networkCall", "onSuccess:" + newsOrCoronaTypesEnum + dataumListModel.getData().size());
            }

            @Override
            public void onFail(String errMSg) {
                failedNetworkCallList.add(newsOrCoronaTypesEnum);
                showError(errMSg);
            }
        });
        failedNetworkCallList.remove(position);
    }

    private void showError(String errMsg) {
        Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
//        Snackbar snackbar = Snackbar
//                .make(loading_screen_wrapper, errMsg, Snackbar.LENGTH_SHORT);
//        snackbar.show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_skip_loading:
                openMainFragment();
                setLoadingUi();
                break;
            case R.id.btn_reload_loading:
                reCallFailedOne();
                break;
        }
    }

    private ArrayList<NewsVariables.NewsOrCoronaTypesEnum> newEnumList() {
        ArrayList<NewsVariables.NewsOrCoronaTypesEnum> newsOrCoronaTypesEnums = new ArrayList<>();
        newsOrCoronaTypesEnums.add(NewsVariables.NewsOrCoronaTypesEnum.News_TOPS);
        newsOrCoronaTypesEnums.add(NewsVariables.NewsOrCoronaTypesEnum.NEWS_All);
        newsOrCoronaTypesEnums.add(NewsVariables.NewsOrCoronaTypesEnum.News_POLITICS);
        newsOrCoronaTypesEnums.add(NewsVariables.NewsOrCoronaTypesEnum.News_SPORTS);
        newsOrCoronaTypesEnums.add(NewsVariables.NewsOrCoronaTypesEnum.News_ENTERTAINMENT);
        newsOrCoronaTypesEnums.add(NewsVariables.NewsOrCoronaTypesEnum.News_WORLD);
        newsOrCoronaTypesEnums.add(NewsVariables.NewsOrCoronaTypesEnum.News_BUSINESS);
        newsOrCoronaTypesEnums.add(NewsVariables.NewsOrCoronaTypesEnum.News_ENTERTAINMENT);
        newsOrCoronaTypesEnums.add(NewsVariables.NewsOrCoronaTypesEnum.News_HEALTH);
        newsOrCoronaTypesEnums.add(NewsVariables.NewsOrCoronaTypesEnum.News_UNCATEGORIES);
        return newsOrCoronaTypesEnums;
    }
}
