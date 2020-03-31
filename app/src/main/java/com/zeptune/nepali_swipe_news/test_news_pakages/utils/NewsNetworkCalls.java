package com.zeptune.nepali_swipe_news.test_news_pakages.utils;

import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.NewsDatabaseUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.TestNewsDatabaseUtils;
import com.zeptune.nepali_swipe_news.retrofit.retofit.ServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsNetworkCalls {
    static boolean isSuccess;
    static NewsDatabaseUtils newsDatabaseUtils;
    public static void getAllLatestNews(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, TestNewsDatabaseUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        Call<DataumListModel> latestNewsCall = ServiceFactory.getAllCategory().getLatestNews();
        latestNewsCall.enqueue(new Callback<DataumListModel>() {
            @Override
            public void onResponse(Call<DataumListModel> call, Response<DataumListModel> response) {
                DataumListModel dataumListModel = response.body();
                if (dataumListModel != null) {
                    if (TestNewsDatabaseUtils.CheckNewDataStatus.IS_NEW_MODEL.equals(TestNewsDatabaseUtils.isLatestModel(newsOrCoronaTypesEnum, dataumListModel))) {
                        TestNewsDatabaseUtils.addNewNewsData(dataumListModel, newsOrCoronaTypesEnum, onDatabaseChangeListener);
                    } else if (TestNewsDatabaseUtils.CheckNewDataStatus.IS_LATEST_MODEL.equals(TestNewsDatabaseUtils.isLatestModel(newsOrCoronaTypesEnum, dataumListModel))) {
                        TestNewsDatabaseUtils.updateNewData(dataumListModel, newsOrCoronaTypesEnum, onDatabaseChangeListener);
                    } else if (TestNewsDatabaseUtils.CheckNewDataStatus.IS_OLD_MODEL.equals(TestNewsDatabaseUtils.isLatestModel(newsOrCoronaTypesEnum, dataumListModel))) {
                        onDatabaseChangeListener.onFail("Up to date");
                    }
                }


            }

            @Override
            public void onFailure(Call<DataumListModel> call, Throwable t) {
                onDatabaseChangeListener.onFail("Failed to Connect to Server");

            }
        });

    }

    public static void checkLatestNews() {
        Call<DataumListModel> latestNewsCall = ServiceFactory.getAllCategory().getLatestNews();
        latestNewsCall.enqueue(new Callback<DataumListModel>() {
            @Override
            public void onResponse(Call<DataumListModel> call, Response<DataumListModel> response) {


            }

            @Override
            public void onFailure(Call<DataumListModel> call, Throwable t) {

            }
        });
    }

    public static void getAllCategoryWiseLatestNews(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, TestNewsDatabaseUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        Call<DataumListModel> latestNewsCall = ServiceFactory.getAllCategory().getCategoryWiseNews(newsOrCoronaTypesEnum.getNewsTypeCode());
        latestNewsCall.enqueue(new Callback<DataumListModel>() {
            @Override
            public void onResponse(Call<DataumListModel> call, Response<DataumListModel> response) {
                DataumListModel dataumListModel = response.body();

                if (dataumListModel != null) {
                    if (TestNewsDatabaseUtils.CheckNewDataStatus.IS_NEW_MODEL.equals(TestNewsDatabaseUtils.isLatestModel(newsOrCoronaTypesEnum, dataumListModel))) {
                        TestNewsDatabaseUtils.addNewNewsData(dataumListModel, newsOrCoronaTypesEnum, onDatabaseChangeListener);
                    } else if (TestNewsDatabaseUtils.CheckNewDataStatus.IS_LATEST_MODEL.equals(TestNewsDatabaseUtils.isLatestModel(newsOrCoronaTypesEnum, dataumListModel))) {
                        TestNewsDatabaseUtils.updateNewData(dataumListModel, newsOrCoronaTypesEnum, onDatabaseChangeListener);
                    } else if (TestNewsDatabaseUtils.CheckNewDataStatus.IS_OLD_MODEL.equals(TestNewsDatabaseUtils.isLatestModel(newsOrCoronaTypesEnum, dataumListModel))) {
                        onDatabaseChangeListener.onFail("Up to date");


                    }
                }

            }

            @Override
            public void onFailure(Call<DataumListModel> call, Throwable t) {

                onDatabaseChangeListener.onFail("Failed to Connect to Server");

            }
        });
    }

    public static void getPageWiseNews(String pageNumber, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, OnNetworkCallInterFace onNetworkCallInterFace) {
        if (newsOrCoronaTypesEnum == NewsVariables.NewsOrCoronaTypesEnum.NEWS_All) {
            Call<DataumListModel> latestNewsCall = ServiceFactory.getAllCategory().getLatestNewsPage(pageNumber);
            latestNewsCall.enqueue(new Callback<DataumListModel>() {
                @Override
                public void onResponse(Call<DataumListModel> call, Response<DataumListModel> response) {
                    DataumListModel dataumListModel = response.body();
                    if (dataumListModel != null) {
                        onNetworkCallInterFace.onSuccess(dataumListModel, newsOrCoronaTypesEnum);
//                        TestNewsDatabaseUtils.updatePagenationNewsDataModel(dataumListModel, newsOrCoronaTypesEnum, onDatabaseChangeListener);
                    }
                }

                @Override
                public void onFailure(Call<DataumListModel> call, Throwable t) {
                    onNetworkCallInterFace.onFailed("Failed to connect to server");
                }
            });

        } else {

            Call<DataumListModel> latestNewsCall = ServiceFactory.getAllCategory().getCategoryWiseNewsPage(newsOrCoronaTypesEnum.getNewsTypeCode(), pageNumber);
            latestNewsCall.enqueue(new Callback<DataumListModel>() {
                @Override
                public void onResponse(Call<DataumListModel> call, Response<DataumListModel> response) {

                    DataumListModel dataumListModel = response.body();
                    if (dataumListModel != null) {
//                        TestNewsDatabaseUtils.updatePagenationNewsDataModel(dataumListModel, newsOrCoronaTypesEnum, onDatabaseChangeListener);
                        onNetworkCallInterFace.onSuccess(dataumListModel, newsOrCoronaTypesEnum);

                    }

                }

                @Override
                public void onFailure(Call<DataumListModel> call, Throwable t) {
                    onNetworkCallInterFace.onFailed("Failed to connect to server");

                }
            });

        }
    }

    public interface OnNetworkCallInterFace {
        void onSuccess(DataumListModel dataumListModel, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum);

        void onFailed(String errMsg);
    }

}
