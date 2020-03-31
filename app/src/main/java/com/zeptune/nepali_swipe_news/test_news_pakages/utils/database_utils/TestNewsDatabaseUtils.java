package com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils;

import android.text.TextUtils;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.models.Datum;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.gson_utils.GesonConverter;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class.AllNewsDatabase;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;

import java.util.ArrayList;

public class TestNewsDatabaseUtils {

    public enum CheckNewDataStatus {
        IS_LATEST_MODEL, IS_NEW_MODEL, IS_OLD_MODEL;
    }

    public static void addNewNewsData(DataumListModel dataumListModel, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, OnDatabaseChangeListener onDatabaseChangeListener) {
        AllNewsDatabase allNewsDatabase = new AllNewsDatabase();
        allNewsDatabase.setJson(GesonConverter.NewsModelToJson(dataumListModel));
        allNewsDatabase.setTypeCode(newsOrCoronaTypesEnum.getNewsTypeCode());
        allNewsDatabase.save();
        DataumListModel saveModel = saveDataListModel(newsOrCoronaTypesEnum);
        if (saveModel != null) {
            onDatabaseChangeListener.onSuccess(saveModel);
        } else {
            onDatabaseChangeListener.onFail("Not saved");
        }
    }

    public static void updateNewData(DataumListModel dataumListModel, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, OnDatabaseChangeListener onDatabaseChangeListener) {
        DataumListModel saveDataListModel = saveDataListModel(newsOrCoronaTypesEnum);
        if (saveDataListModel != null) {
            int myDataumSize = saveDataListModel.getData().size();
            ArrayList<Datum> saveDatum = saveDataListModel.getData();
            saveDatum.clear();
            saveDatum.addAll(dataumListModel.getData());
            saveDataListModel.setData(saveDatum);
            saveDataListModel.setMeta(dataumListModel.getMeta());
            String updatedJSonString = GesonConverter.NewsModelToJson(saveDataListModel);
            updatePaginationDataModelDatabase(updatedJSonString, newsOrCoronaTypesEnum);
            onDatabaseChangeListener.onSuccess(saveDataListModel(newsOrCoronaTypesEnum));
        } else {
            addNewNewsData(dataumListModel, newsOrCoronaTypesEnum, onDatabaseChangeListener);
        }
    }

    public static void updatePagenationNewsDataModel(DataumListModel dataumListModel, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, OnDatabaseChangeListener onDatabaseChangeListener) {
        DataumListModel mySaveDataListModel =new DataumListModel();
        if (mySaveDataListModel != null) {
            int myDataumSize = saveDataListModel(newsOrCoronaTypesEnum).getData().size();
            ArrayList<Datum> saveDatum =new ArrayList<>();
            saveDatum=saveDataListModel(newsOrCoronaTypesEnum).getData();
            ArrayList<Datum> callDatum=new ArrayList<>();
            callDatum=dataumListModel.getData();
            saveDatum.addAll(callDatum);
            mySaveDataListModel.setData(saveDatum);
            mySaveDataListModel.setMeta(dataumListModel.getMeta());

            String updatedJSonString = GesonConverter.NewsModelToJson(mySaveDataListModel);
            if(mySaveDataListModel.getData().size()>myDataumSize) {

                updatePaginationDataModelDatabase(updatedJSonString, newsOrCoronaTypesEnum);
            if (saveDataListModel(newsOrCoronaTypesEnum) != null) {
                if (myDataumSize < saveDataListModel(newsOrCoronaTypesEnum).getData().size()) {
                    onDatabaseChangeListener.onSuccess(saveDataListModel(newsOrCoronaTypesEnum));
                } else {
                    onDatabaseChangeListener.onFail("Up to date");
                }
            }
            }


        }
    }

    public static void updatePaginationDataModelDatabase(String updatedJsonString, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        new Update(AllNewsDatabase.class)
                .set("Json=?", updatedJsonString)
                .where("TypeCode=?", newsOrCoronaTypesEnum.getNewsTypeCode())
                .execute();
    }

    public static CheckNewDataStatus isLatestModel(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, DataumListModel dataumListModel) {
        DataumListModel saveListModel = saveDataListModel(newsOrCoronaTypesEnum);
        if (saveListModel != null) {
            if(saveListModel.getData().size()>0&& dataumListModel.getData().size()>0) {
                if (TextUtils.equals(saveListModel.getData().get(0).getTitle().trim(), dataumListModel.getData().get(0).getTitle().trim())) {
                    return CheckNewDataStatus.IS_OLD_MODEL;
                } else {
                    return CheckNewDataStatus.IS_LATEST_MODEL;
                }
            }else{
                return CheckNewDataStatus.IS_NEW_MODEL;

            }
        } else {
            return CheckNewDataStatus.IS_NEW_MODEL;
        }

    }


    public static DataumListModel saveDataListModel(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        AllNewsDatabase mySaveData = new Select().from(AllNewsDatabase.class).where("TypeCode=?", newsOrCoronaTypesEnum.getNewsTypeCode()).executeSingle();
        if (mySaveData != null) {
            String mySaveJson = mySaveData.getJson();
            if (!TextUtils.isEmpty(mySaveJson)) {
                return GesonConverter.NewsJsonModleClass(mySaveJson);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public interface OnDatabaseChangeListener {
        void onSuccess(DataumListModel dataumListModel);

        void onFail(String errMsg);
    }
}
