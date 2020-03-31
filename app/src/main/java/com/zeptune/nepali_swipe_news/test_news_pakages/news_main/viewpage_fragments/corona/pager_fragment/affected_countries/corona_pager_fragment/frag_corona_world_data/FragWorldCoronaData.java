package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.corona_pager_fragment.frag_corona_world_data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.CaseDataModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.corona_pager_fragment.BaseFragCoronaWorldData;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCaseDbUtils;

public class FragWorldCoronaData extends BaseFragCoronaWorldData {
    View view;
    private TextView txt_death,
            txt_death_number,
            txt_cases,
            txt_cases_number,
            txt_region,
            txt_region_number,
            txt_total_recovered,
            txt_total_recovered_number,
            txt_new_death,
            txt_new_death_number,
            txt_new_cases,
            txt_new_cases_number,
            txt_serious_critical,
            txt_total_serious_critical_number,
            txt_title_Country_Name,
            txt_statistic_taken_at;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_world_corona_data, container, false);
        initUi();
        return view;
    }

    private void initUi() {
        txt_death_number = view.findViewById(R.id.txt_death_number);
        txt_cases_number = view.findViewById(R.id.txt_cases_number);
        txt_total_recovered_number = view.findViewById(R.id.txt_total_recovered_number);
        txt_new_cases_number = view.findViewById(R.id.txt_new_cases_number);
        txt_statistic_taken_at = view.findViewById(R.id.txt_statistic_taken_at);
        setDataCaseData();
    }

    public void setBaseFragWorldCoronaData(CoronaWorldDataListener coronaWorldDataListener) {
        super.setUpBaseFragCoronaWorldData(coronaWorldDataListener);
        super.notifyUpdated();
    }

    public void setDataCaseData() {
        CaseDataModel caseDataModel = CoroCaseDbUtils.saveCoroCaseDataModel(NewsVariables.NewsOrCoronaTypesEnum.CORONA_WORLD_CASES);
        if (caseDataModel != null) {
            txt_cases_number.setText(caseDataModel.total_cases);
            txt_death_number.setText(caseDataModel.total_deaths);
            txt_new_cases_number.setText(caseDataModel.new_cases);
            txt_total_recovered_number.setText(caseDataModel.total_recovered);
            String date =caseDataModel.statistic_taken_at.concat(" GMT");
            txt_statistic_taken_at.setText(date);
        }


    }
}

