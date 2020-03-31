package com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils;

import android.text.TextUtils;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.gson_utils.GesonConverter;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class.AllNewsDatabase;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.GoldModelWrapper;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesVariables;

public class GoldDatabaseUtils {
    public enum CheckGoldDataStatus {
        ALREADY_SAVED, NOT_SAVED_YET;
    }

    public static void addNewNewsData(GoldModelWrapper goldDataModel, FeaturesVariables.FeaturesTypesEnum featuresTypesEnum, GoldDatabaseUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        AllNewsDatabase allNewsDatabase = new AllNewsDatabase();
        allNewsDatabase.setJson(GesonConverter.GoldModelToJson(goldDataModel));
        allNewsDatabase.setTypeCode(featuresTypesEnum.getFeaturesTypeCode());
        allNewsDatabase.save();
        GoldModelWrapper saveModel = saveGoldDataModel(featuresTypesEnum);
        if (saveModel != null) {
            onDatabaseChangeListener.onSuccess(saveModel);
        } else {
            onDatabaseChangeListener.onFail("Not saved");
        }
    }

    public static void deleteGoldDatabase(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        new Delete().from(AllNewsDatabase.class).where("TypeCode=?", featuresTypesEnum.getFeaturesTypeCode()).execute();
    }

    public static GoldDatabaseUtils.CheckGoldDataStatus isSavedModel(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        GoldModelWrapper saveListModel = saveGoldDataModel(featuresTypesEnum);
        if (saveListModel != null) {
            return CheckGoldDataStatus.ALREADY_SAVED;
        } else {
            return CheckGoldDataStatus.NOT_SAVED_YET;
        }
    }


    public static GoldModelWrapper saveGoldDataModel(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        AllNewsDatabase mySaveData = new Select().from(AllNewsDatabase.class).where("TypeCode=?", featuresTypesEnum.getFeaturesTypeCode()).executeSingle();
        if (mySaveData != null) {
            String mySaveJson = mySaveData.getJson();
            if (!TextUtils.isEmpty(mySaveJson)) {
                if (featuresTypesEnum == FeaturesVariables.FeaturesTypesEnum.FEATURES_GOLD_SILVER) {
                    GoldModelWrapper goldModelWrapper = GesonConverter.GoldJsonModleClass(mySaveJson);
                    try {
                        if (goldModelWrapper.goldDataModels.size() > 0) {
                            return goldModelWrapper;
                        }
                    } catch (Exception ignored) {

                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return null;
    }

    public interface OnDatabaseChangeListener {
        void onSuccess(GoldModelWrapper goldModelWrapper);

        void onFail(String errMsg);
    }
}
