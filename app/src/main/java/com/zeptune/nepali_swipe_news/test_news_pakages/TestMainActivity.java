package com.zeptune.nepali_swipe_news.test_news_pakages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.all_interfaces.InterfaceFragmentToogle;
import com.zeptune.nepali_swipe_news.date_converter.converter.ConvertAdToBs;
import com.zeptune.nepali_swipe_news.date_converter.interface_date.NepaliDateInterface;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DateModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.CaseDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseListModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.NewsHoster.BaseNewsHoster;
import com.zeptune.nepali_swipe_news.test_news_pakages.NewsHoster.NewsHoster;
import com.zeptune.nepali_swipe_news.test_news_pakages.loading.BaseNetworkCallFragment;
import com.zeptune.nepali_swipe_news.test_news_pakages.loading.LoadedTimeUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.loading.NetworkCallerFragment;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.CoronaNetworkCall;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCaseDbUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCountryWiseDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.DateDatabaseUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestMainActivity extends AppCompatActivity implements InterfaceFragmentToogle {
    NewsHoster newsHoster;
    NetworkCallerFragment networkCallerFragment;
    FragmentManager fragmentManager;
    private int currentPosition = 0;
    private int backPressCount = 0;
    private DateFormat dayFormat, monthFormat;
    private Date date;
    private Fragment fragment;
    private static boolean POP_IN_SAVE_INSTANCE = false;
    private static boolean COMMIT_NEW_FRAGMENT = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        dayFormat = new SimpleDateFormat("dd");
        monthFormat = new SimpleDateFormat("MM");
        date = new Date();
        setTodaydate();

//        CoronaNetworkCall.getContryWiseCoronaState(NewsVariables.NewsOrCoronaTypesEnum.CORONA_COUNTRY_WISE_STATE, new CoroCountryWiseDatabaseUtils.OnDatabaseChangeListener() {
//            @Override
//            public void onSuccess(CountryWiseListModel countryWiseListModel) {
//                Log.d("fromStorage", "onSuccess: " + countryWiseListModel.countries_stat.get(0).deaths);
//            }
//
//            @Override
//            public void onFail(String errMsg) {
//                Log.d("fromStorage", "onSuccess: " + errMsg);
//
//
//            }
//        });
//        CoronaNetworkCall.getWorldCoronaStatus(NewsVariables.NewsOrCoronaTypesEnum.CORONA_WORLD_CASES, new CoroCaseDbUtils.OnDatabaseChangeListener() {
//            @Override
//            public void onSuccess(CaseDataModel caseDataModel) {
//                Log.d("mysavedModel", "onSuccess: " + caseDataModel.total_recovered);
//            }
//
//            @Override
//            public void onFail(String errMsg) {
//                Log.d("mysavedModel", "onfail: " + errMsg);
//
//            }
//        });


//barCharFragment();
        initUi();
        try {
            initObj();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void setTodaydate() {
        ConvertAdToBs convertAdToBs = new ConvertAdToBs(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), monthFormat.format(date), dayFormat.format(date), new NepaliDateInterface() {
            @Override
            public void setConvertedDate(String nepYear, String nepMonth, String nepNumberOfMonth, String nepDay, String dayOfWeek, boolean isNepali) {
                FeaturesVariables.FeaturesTypesEnum featuresTypesEnum = FeaturesVariables.FeaturesTypesEnum.FEATURES_DATE;
                DateModel dateModel = new DateModel();
                dateModel.year = nepYear;
                dateModel.month = nepMonth;
                dateModel.dayOfTheMonth = nepDay;
                dateModel.dayOFWeek = dayOfWeek;
                if (DateDatabaseUtils.isSavedModel(featuresTypesEnum) == DateDatabaseUtils.CheckDateDataStatus.ALREADY_SAVED) {
                    DateDatabaseUtils.deleteGoldDatabase(featuresTypesEnum);
                    DateDatabaseUtils.addNewNewsData(dateModel, featuresTypesEnum);
                } else {
                    DateDatabaseUtils.addNewNewsData(dateModel, featuresTypesEnum);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        changeStatusBarColor();

    }

    public void changeStatusBarColor() {
        Window window = getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT > 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        }


    }

    public void initUi() {
        newsHoster = new NewsHoster();
    }

    private void setUpBaseNewsHoster(NewsHoster newsHoster) {
        newsHoster.setUpViewPagerBase(new BaseNewsHoster.NewsHosterListeners() {
            @Override
            public void onBackPress(int currentPagerPosition) {
                backPressCount = 0;
                currentPosition = currentPagerPosition;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentPendingTask();
    }

    private void fragmentPendingTask() {
        if (POP_IN_SAVE_INSTANCE) {
            if (fragmentManager.findFragmentByTag("loadingfrg") != null) {
                fragmentManager.popBackStack("netCallFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            if (fragment != null) {
                fragmentManager.beginTransaction().replace(R.id.container_test_main_activity, newsHoster, "hosterFragment").commit();
                setUpBaseNewsHoster(newsHoster);
            } else {
                fragmentManager.beginTransaction().add(R.id.container_test_main_activity, newsHoster, "hosterFragment").commit();
                setUpBaseNewsHoster(newsHoster);
            }
            POP_IN_SAVE_INSTANCE = false;
        }

        if (COMMIT_NEW_FRAGMENT) {
            if (fragment != null) {
                fragmentManager.beginTransaction().replace(R.id.container_test_main_activity, newsHoster, "hosterFragment").commit();
                setUpBaseNewsHoster(newsHoster);
            } else {
                fragmentManager.beginTransaction().add(R.id.container_test_main_activity, newsHoster, "hosterFragment").commit();
                setUpBaseNewsHoster(newsHoster);
            }
            COMMIT_NEW_FRAGMENT = false;
        }

    }

    public void initObj() throws ParseException {
        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.container_test_main_activity);
        networkCallerFragment = new NetworkCallerFragment();
        if (isNetworkAvailable()) {
            if (LoadedTimeUtils.canLoadData(this)) {
                if (fragment != null) {
                    fragmentManager.beginTransaction().replace(R.id.container_test_main_activity, networkCallerFragment, "loadingfrg").addToBackStack("netCallFrag").commit();
                } else {
                    fragmentManager.beginTransaction().add(R.id.container_test_main_activity, networkCallerFragment, "loadingfrg").addToBackStack("netCallFrag").commit();
                }
                networkCallerFragment.setUpCall(new BaseNetworkCallFragment.NetworkCallStatusListener() {
                    @Override
                    public void onCallCompleted(boolean isCompleted) {
                        if (!fragmentManager.isStateSaved()) {
                            if (isCompleted) {
                                if (fragmentManager.findFragmentByTag("loadingfrg") != null) {
                                    fragmentManager.popBackStack("netCallFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                }
                                if (fragment != null) {
                                    fragmentManager.beginTransaction().replace(R.id.container_test_main_activity, newsHoster, "hosterFragment").commit();
                                    setUpBaseNewsHoster(newsHoster);
                                } else {
                                    fragmentManager.beginTransaction().add(R.id.container_test_main_activity, newsHoster, "hosterFragment").commit();
                                    setUpBaseNewsHoster(newsHoster);
                                }
                            }
                        } else {
                            POP_IN_SAVE_INSTANCE = true;
                        }
                    }
                });
            } else {
                if (!fragmentManager.isStateSaved()) {

                    if (fragment != null) {
                        fragmentManager.beginTransaction().replace(R.id.container_test_main_activity, newsHoster, "hosterFragment").commit();
                        setUpBaseNewsHoster(newsHoster);
                    } else {
                        fragmentManager.beginTransaction().add(R.id.container_test_main_activity, newsHoster, "hosterFragment").commit();
                        setUpBaseNewsHoster(newsHoster);
                    }
                } else {
                    COMMIT_NEW_FRAGMENT = true;
                }
            }
        } else {
            fragmentManager.beginTransaction().add(R.id.container_test_main_activity, newsHoster, "hosterFragment").commit();
            setUpBaseNewsHoster(newsHoster);
        }

    }


    @Override
    public void toogleFragment(int fragmentPosition) {

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.findFragmentByTag("hosterFragment") != null) {
            if (currentPosition == 2) {
                newsHoster.setPosition(1);
                backPressCount = 0;
            } else if (currentPosition == 1) {
                newsHoster.setPosition(0);
                backPressCount = 0;
            } else if (currentPosition == 0) {
                if (backPressCount < 1) {
                    Toast.makeText(this, "Press Again to Close", Toast.LENGTH_SHORT).show();
                    backPressCount++;
                } else {
                    super.onBackPressed();
                }
            }
        } else if (backPressCount < 1) {
            backPressCount++;
        } else if (backPressCount == 1) {
            super.onBackPressed();
        }
    }
}
