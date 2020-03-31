package com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils;

import android.text.TextUtils;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class.AllNewsDatabase;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.AffCountryDataModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.gson_utils.GesonConverter;

public class CoroAffCountryDbUtils {
    public enum CheckCoroAffConDbStatus {
        ALREADY_SAVED, NOT_SAVED_YET;
    }

    public static void addNewNewsData(AffCountryDataModel affCountryDataModel, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, CoroAffCountryDbUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        AllNewsDatabase allNewsDatabase = new AllNewsDatabase();
        allNewsDatabase.setJson(GesonConverter.CoroAffModelToJson(affCountryDataModel));
        allNewsDatabase.setTypeCode(newsOrCoronaTypesEnum.getNewsTypeCode());
        allNewsDatabase.save();
        AffCountryDataModel saveModel = saveAffectedCountry(newsOrCoronaTypesEnum);
        if (saveModel != null) {
            onDatabaseChangeListener.onSuccess(saveModel);
        } else {
            onDatabaseChangeListener.onFail("Not saved");
        }
    }

    public static void deleteGoldDatabase(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        new Delete().from(AllNewsDatabase.class).where("TypeCode=?", newsOrCoronaTypesEnum.getNewsTypeCode()).execute();
    }

    public static CoroAffCountryDbUtils.CheckCoroAffConDbStatus isSavedModel(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        AffCountryDataModel saveListModel = saveAffectedCountry(newsOrCoronaTypesEnum);
        if (saveListModel != null) {
            return CoroAffCountryDbUtils.CheckCoroAffConDbStatus.ALREADY_SAVED;
        } else {
            return CoroAffCountryDbUtils.CheckCoroAffConDbStatus.NOT_SAVED_YET;
        }
    }


    public static AffCountryDataModel saveAffectedCountry(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        AllNewsDatabase mySaveData = new Select().from(AllNewsDatabase.class).where("TypeCode=?", newsOrCoronaTypesEnum.getNewsTypeCode()).executeSingle();
        if (mySaveData != null) {
            String mySaveJson = mySaveData.getJson();
            if (!TextUtils.isEmpty(mySaveJson)) {
                if (newsOrCoronaTypesEnum == NewsVariables.NewsOrCoronaTypesEnum.CORONA_AFFECTED_COUNTRY) {
                    return GesonConverter.AffCoroJsonToModle(mySaveJson);
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
        void onSuccess(AffCountryDataModel affCountryDataModel);
        void onFail(String errMsg);
    }
}
