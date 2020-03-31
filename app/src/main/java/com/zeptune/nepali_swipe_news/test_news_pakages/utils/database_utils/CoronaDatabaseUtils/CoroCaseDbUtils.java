package com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils;

import android.text.TextUtils;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class.AllNewsDatabase;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.CaseDataModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.gson_utils.GesonConverter;

public class CoroCaseDbUtils {
    public enum CheckCoroDbStatus {
        ALREADY_SAVED, NOT_SAVED_YET;
    }

    public static void addNewNewsData(CaseDataModel caseDataModel, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, CoroCaseDbUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        AllNewsDatabase allNewsDatabase = new AllNewsDatabase();
        allNewsDatabase.setJson(GesonConverter.CoroCaseDataModelToString(caseDataModel));
        allNewsDatabase.setTypeCode(newsOrCoronaTypesEnum.getNewsTypeCode());
        allNewsDatabase.save();
        CaseDataModel saveModel = saveCoroCaseDataModel(newsOrCoronaTypesEnum);
        if (saveModel != null) {
            onDatabaseChangeListener.onSuccess(saveModel);
        } else {
            onDatabaseChangeListener.onFail("Not saved");
        }
    }

    public static void deleteGoldDatabase(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        new Delete().from(AllNewsDatabase.class).where("TypeCode=?", newsOrCoronaTypesEnum.getNewsTypeCode()).execute();
    }

    public static CoroCaseDbUtils.CheckCoroDbStatus isSavedModel(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        CaseDataModel saveListModel = saveCoroCaseDataModel(newsOrCoronaTypesEnum);
        if (saveListModel != null) {
            return CoroCaseDbUtils.CheckCoroDbStatus.ALREADY_SAVED;
        } else {
            return CoroCaseDbUtils.CheckCoroDbStatus.NOT_SAVED_YET;
        }
    }


    public static CaseDataModel saveCoroCaseDataModel(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        AllNewsDatabase mySaveData = new Select().from(AllNewsDatabase.class).where("TypeCode=?", newsOrCoronaTypesEnum.getNewsTypeCode()).executeSingle();
        if (mySaveData != null) {
            String mySaveJson = mySaveData.getJson();
            if (!TextUtils.isEmpty(mySaveJson)) {
                if (newsOrCoronaTypesEnum == NewsVariables.NewsOrCoronaTypesEnum.CORONA_WORLD_CASES) {
                    return GesonConverter.CoroCaseJsonToDM(mySaveJson);
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
        void onSuccess(CaseDataModel caseDataModel);
        void onFail(String errMsg);
    }
}
