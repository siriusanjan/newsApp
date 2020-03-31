package com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils;

import android.text.TextUtils;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.gson_utils.GesonConverter;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class.AllNewsDatabase;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DataRasiFalModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.FeaturesVariables;

public class RasifalDatabaseUtils {
    public enum CheckRasiDataStatus {
        ALREADY_SAVED, NOT_SAVED_YET;
    }

    public static void addNewNewsData(DataRasiFalModel dataRasiFalModel, FeaturesVariables.FeaturesTypesEnum featuresTypesEnum, RasifalDatabaseUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        AllNewsDatabase allNewsDatabase = new AllNewsDatabase();
        allNewsDatabase.setJson(GesonConverter.RasiModelToJson(dataRasiFalModel));
        allNewsDatabase.setTypeCode(featuresTypesEnum.getFeaturesTypeCode());
        allNewsDatabase.save();
        DataRasiFalModel saveModel = saveRasiDataModel(featuresTypesEnum);
        if (saveModel != null) {
            onDatabaseChangeListener.onSuccess(saveModel);
        } else {
            onDatabaseChangeListener.onFail("Not saved");
        }
    }

    public static void deleteGoldDatabase(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        new Delete().from(AllNewsDatabase.class).where("TypeCode=?", featuresTypesEnum.getFeaturesTypeCode()).execute();
    }

    public static RasifalDatabaseUtils.CheckRasiDataStatus isSavedModel(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        DataRasiFalModel saveListModel = saveRasiDataModel(featuresTypesEnum);
        if (saveListModel != null) {
            if (saveListModel.getData().getFetched().size() > 0) {

                return RasifalDatabaseUtils.CheckRasiDataStatus.ALREADY_SAVED;
            } else {
                return RasifalDatabaseUtils.CheckRasiDataStatus.NOT_SAVED_YET;

            }
        } else {
            return RasifalDatabaseUtils.CheckRasiDataStatus.NOT_SAVED_YET;
        }
    }


    public static DataRasiFalModel saveRasiDataModel(FeaturesVariables.FeaturesTypesEnum featuresTypesEnum) {
        AllNewsDatabase mySaveData = new Select().from(AllNewsDatabase.class).where("TypeCode=?", featuresTypesEnum.getFeaturesTypeCode()).executeSingle();
        if (mySaveData != null) {
            String mySaveJson = mySaveData.getJson();
            if (!TextUtils.isEmpty(mySaveJson)) {
                if (featuresTypesEnum == FeaturesVariables.FeaturesTypesEnum.FEATURES_RASIFAL) {
                    DataRasiFalModel dataRasiFalModel = GesonConverter.RasiJsonModleClass(mySaveJson);
                    try {
                        if (dataRasiFalModel.getData().getFetched().size() > 0) {
                            return dataRasiFalModel;
                        } else {
                            return null;
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
        void onSuccess(DataRasiFalModel dataRasiFalModel);

        void onFail(String errMsg);
    }

}
