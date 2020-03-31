package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries;

import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseListModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCountryWiseDatabaseUtils;

import java.util.ArrayList;

public class ListUtils {

    public static ArrayList<CountryWiseBarModel> getDeathList() {
        CountryWiseListModel countryWiseListModel = CoroCountryWiseDatabaseUtils.saveCountryWiseModel(NewsVariables.NewsOrCoronaTypesEnum.CORONA_COUNTRY_WISE_STATE);
        ArrayList<CountryWiseBarModel> deathList = new ArrayList<>();
        if (countryWiseListModel != null) {
            if (countryWiseListModel.countries_stat != null) {
                ArrayList<CountryWiseDataModel> countryWiseListModelsList = countryWiseListModel.countries_stat;
                for (int i = 0; i < countryWiseListModelsList.size(); i++) {

                    CountryWiseDataModel countryWiseDataModel = countryWiseListModelsList.get(i);
                    String death = countryWiseDataModel.deaths;
                    death = death.replace(",", "");
                    deathList.add(new CountryWiseBarModel(Integer.parseInt(death), countryWiseDataModel.country_name, countryWiseDataModel.currentTimeMill));
                }
                return deathList;
            }
            return null;
        }
        return null;
    }

    public static ArrayList<CountryWiseBarModel> getNewDeathList() {
        CountryWiseListModel countryWiseListModel = CoroCountryWiseDatabaseUtils.saveCountryWiseModel(NewsVariables.NewsOrCoronaTypesEnum.CORONA_COUNTRY_WISE_STATE);
        ArrayList<CountryWiseBarModel> newDeathList = new ArrayList<>();
        if (countryWiseListModel != null) {
            if (countryWiseListModel.countries_stat != null) {
                ArrayList<CountryWiseDataModel> countryWiseListModelsList = countryWiseListModel.countries_stat;
                for (int i = 0; i < countryWiseListModelsList.size(); i++) {

                    CountryWiseDataModel countryWiseDataModel = countryWiseListModelsList.get(i);
                    String death = countryWiseDataModel.new_deaths;
                    death = death.replace(",", "");
                    newDeathList.add(new CountryWiseBarModel(Integer.parseInt(death), countryWiseDataModel.country_name, countryWiseDataModel.currentTimeMill));
                }
                return newDeathList;
            }
            return null;
        }
        return null;
    }

    public static ArrayList<CountryWiseBarModel> getCasesList() {
        CountryWiseListModel countryWiseListModel = CoroCountryWiseDatabaseUtils.saveCountryWiseModel(NewsVariables.NewsOrCoronaTypesEnum.CORONA_COUNTRY_WISE_STATE);
        ArrayList<CountryWiseBarModel> casesList = new ArrayList<>();
        if (countryWiseListModel != null) {
            if (countryWiseListModel.countries_stat != null) {
                ArrayList<CountryWiseDataModel> countryWiseListModelsList = countryWiseListModel.countries_stat;
                for (int i = 0; i < countryWiseListModelsList.size(); i++) {

                    CountryWiseDataModel countryWiseDataModel = countryWiseListModelsList.get(i);
                    String death = countryWiseDataModel.cases;
                    death = death.replace(",", "");
                    casesList.add(new CountryWiseBarModel(Integer.parseInt(death), countryWiseDataModel.country_name, countryWiseDataModel.currentTimeMill));
                }
                return casesList;
            }
            return null;
        }
        return null;
    }
}
