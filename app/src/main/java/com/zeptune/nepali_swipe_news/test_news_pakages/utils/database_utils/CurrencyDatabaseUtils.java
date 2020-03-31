package com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils;

import android.text.TextUtils;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class.AllNewsDatabase;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.currencyDataModel.DataCurrencyModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.gson_utils.GesonConverter;

public class CurrencyDatabaseUtils {
    public enum CheckCurrencyDataStatus {
        ALREADY_SAVED, NOT_SAVED_YET;
    }

    public static void addNewNewsData(DataCurrencyModel DataCurrencyModel, FeaturesVariables.FeaturesTypesEnum featuresTypesEnum, CurrencyDatabaseUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        AllNewsDatabase allNewsDatabase = new AllNewsDatabase();
        allNewsDatabase.setJson(GesonConverter.CurrencyExchageDMToJson(DataCurrencyModel));
        allNewsDatabase.setTypeCode(featuresTypesEnum.getFeaturesTypeCode());
        allNewsDatabase.save();
        DataCurrencyModel saveModel = saveCurrencyDataModel(featuresTypesEnum);
        if (saveModel != null) {
            onDatabaseChangeListener.onSuccess(saveModel);
        } else {
            onDatabaseChangeListener.onFail("Not saved");
        }
    }

    public static void deleteGoldDatabase(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        new Delete().from(AllNewsDatabase.class).where("TypeCode=?", featuresTypesEnum.getFeaturesTypeCode()).execute();
    }

    public static CurrencyDatabaseUtils.CheckCurrencyDataStatus isSavedModel(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        DataCurrencyModel saveListModel = saveCurrencyDataModel(featuresTypesEnum);
        if (saveListModel != null) {
            return CurrencyDatabaseUtils.CheckCurrencyDataStatus.ALREADY_SAVED;
        } else {
            return CurrencyDatabaseUtils.CheckCurrencyDataStatus.NOT_SAVED_YET;
        }
    }


    public static DataCurrencyModel saveCurrencyDataModel(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        AllNewsDatabase mySaveData = new Select().from(AllNewsDatabase.class).where("TypeCode=?", featuresTypesEnum.getFeaturesTypeCode()).executeSingle();
        if (mySaveData != null) {
            String mySaveJson = mySaveData.getJson();
            if (!TextUtils.isEmpty(mySaveJson)) {
                if (featuresTypesEnum == FeaturesVariables.FeaturesTypesEnum.FEATURES_CURRENCY) {
                    DataCurrencyModel dataCurrencyModel = GesonConverter.CurrencyExchangJsonTODM(mySaveJson);
                    try {
                        if (dataCurrencyModel.getData().getFetched_data().size() > 0) {
                            return dataCurrencyModel;
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
        void onSuccess(DataCurrencyModel currencyDataModel);

        void onFail(String errMsg);
    }
}
