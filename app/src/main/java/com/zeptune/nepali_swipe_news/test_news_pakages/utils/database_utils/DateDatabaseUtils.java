package com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils;

import android.text.TextUtils;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class.AllNewsDatabase;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DateModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.gson_utils.GesonConverter;

public class DateDatabaseUtils {
    public enum CheckDateDataStatus {
        ALREADY_SAVED, NOT_SAVED_YET;
    }

    public static void addNewNewsData(DateModel dateModel, FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        AllNewsDatabase allNewsDatabase = new AllNewsDatabase();
        allNewsDatabase.setJson(GesonConverter.DateModelToJson(dateModel));
        allNewsDatabase.setTypeCode(featuresTypesEnum.getFeaturesTypeCode());
        allNewsDatabase.save();
        DateModel saveModel = saveDateDataModel(featuresTypesEnum);
//        if (saveModel != null) {
//            onDatabaseChangeListener.onSuccess(saveModel);
//        } else {
//            onDatabaseChangeListener.onFail("Not saved");
//        }
    }

    public static void setTodayDate(TextView year, TextView txt_month, TextView day, TextView day_of_week) {
        DateModel dateModel = getTodayDate();
        if (dateModel != null) {
            year.setText(dateModel.year);
            txt_month.setText(dateModel.month);
            day.setText(dateModel.dayOfTheMonth);
            day_of_week.setText(dateModel.dayOFWeek);
        }

    }

    public static String getDate() {
        DateModel dateModel = getTodayDate();
        if (dateModel != null) {
            return dateModel.dayOfTheMonth.concat(" ").concat(dateModel.month).concat(",").concat(" ").concat(dateModel.year).concat(" ").concat(dateModel.dayOFWeek);
        }
        return "";
    }

    private static DateModel getTodayDate() {
        return DateDatabaseUtils.saveDateDataModel(FeaturesVariables.FeaturesTypesEnum.FEATURES_DATE);
    }

    public static void deleteGoldDatabase(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        new Delete().from(AllNewsDatabase.class).where("TypeCode=?", featuresTypesEnum.getFeaturesTypeCode()).execute();
    }

    public static DateDatabaseUtils.CheckDateDataStatus isSavedModel(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        DateModel saveListModel = saveDateDataModel(featuresTypesEnum);
        if (saveListModel != null) {
            return DateDatabaseUtils.CheckDateDataStatus.ALREADY_SAVED;
        } else {
            return DateDatabaseUtils.CheckDateDataStatus.NOT_SAVED_YET;
        }
    }


    public static DateModel saveDateDataModel(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        AllNewsDatabase mySaveData = new Select().from(AllNewsDatabase.class).where("TypeCode=?", featuresTypesEnum.getFeaturesTypeCode()).executeSingle();
        if (mySaveData != null) {
            String mySaveJson = mySaveData.getJson();
            if (!TextUtils.isEmpty(mySaveJson)) {
                if (featuresTypesEnum == FeaturesVariables.FeaturesTypesEnum.FEATURES_DATE) {
                    return GesonConverter.DateJsonModleClass(mySaveJson);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public interface OnDatabaseChangeListener {
        void onSuccess(DateModel dateModel);

        void onFail(String errMsg);
    }
}
