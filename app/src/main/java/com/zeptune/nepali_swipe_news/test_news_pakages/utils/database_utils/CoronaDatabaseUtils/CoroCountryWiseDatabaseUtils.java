package com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils;

import android.text.TextUtils;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class.AllNewsDatabase;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseListModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.gson_utils.GesonConverter;

public class CoroCountryWiseDatabaseUtils {
    public enum CheckCoroCountWiseDBStatus {
        ALREADY_SAVED, NOT_SAVED_YET;
    }

    public static void addNewNewsData(CountryWiseListModel countryWiseListModel, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, CoroCountryWiseDatabaseUtils.OnDatabaseChangeListener onDatabaseChangeListener) {
        AllNewsDatabase allNewsDatabase = new AllNewsDatabase();
        allNewsDatabase.setJson(GesonConverter.CoroCountryWiseDMToJson(countryWiseListModel));
        allNewsDatabase.setTypeCode(newsOrCoronaTypesEnum.getNewsTypeCode());
        allNewsDatabase.save();
        CountryWiseListModel saveModel = saveCountryWiseModel(newsOrCoronaTypesEnum);
        if (saveModel != null) {
            onDatabaseChangeListener.onSuccess(saveModel);
        } else {
            onDatabaseChangeListener.onFail("Not saved");
        }
    }

    public static void deleteGoldDatabase(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        new Delete().from(AllNewsDatabase.class).where("TypeCode=?", newsOrCoronaTypesEnum.getNewsTypeCode()).execute();
    }

    public static CoroCountryWiseDatabaseUtils.CheckCoroCountWiseDBStatus isSavedModel(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        CountryWiseListModel saveListModel = saveCountryWiseModel(newsOrCoronaTypesEnum);
        if (saveListModel != null) {
            return CoroCountryWiseDatabaseUtils.CheckCoroCountWiseDBStatus.ALREADY_SAVED;
        } else {
            return CoroCountryWiseDatabaseUtils.CheckCoroCountWiseDBStatus.NOT_SAVED_YET;
        }
    }


    public static CountryWiseListModel saveCountryWiseModel(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        AllNewsDatabase mySaveData = new Select().from(AllNewsDatabase.class).where("TypeCode=?", newsOrCoronaTypesEnum.getNewsTypeCode()).executeSingle();
        if (mySaveData != null) {
            String mySaveJson = mySaveData.getJson();
            if (!TextUtils.isEmpty(mySaveJson)) {
                if (newsOrCoronaTypesEnum == NewsVariables.NewsOrCoronaTypesEnum.CORONA_COUNTRY_WISE_STATE) {
                    try {
                        CountryWiseListModel countryWiseListModel = GesonConverter.CoroCountryWiseJsonTODM(mySaveJson);
                        if (countryWiseListModel.countries_stat.size() > 0) {
                            return countryWiseListModel;
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
        void onSuccess(CountryWiseListModel countryWiseListModel);

        void onFail(String errMsg);
    }
}
