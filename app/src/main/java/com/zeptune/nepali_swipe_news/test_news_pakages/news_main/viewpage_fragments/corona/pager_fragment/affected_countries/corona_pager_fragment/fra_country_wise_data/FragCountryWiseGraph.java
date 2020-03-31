package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.corona_pager_fragment.fra_country_wise_data;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseListModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.CountryWiseBarModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.ListUtils;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.adapter.CountryWiseListAdapter;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.corona_pager_fragment.BaseFragCoronaWorldData;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.bottom_sheet_detail_display.CoronaDetailBottomSheet;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.serach.BaseSearchBtmSheetFrag;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.serach.BtmSheetSearchFramgnent;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCountryWiseDatabaseUtils;

import java.util.ArrayList;

public class FragCountryWiseGraph extends BaseFragCoronaWorldData implements View.OnClickListener {
    private View view;
    BarChart barChart, bar_chart_country_wise_second;
    BarData barData;
    String[] labels = {""};
    BarDataSet barDataSet;
    BarData barDataSecond;
    String[] labelsSecond = {""};
    BarDataSet barDataSetSecond;
    ArrayList<BarEntry> barEntries;
    ArrayList<BarEntry> barEntriesSecond;
    TextView btn_Death_wise;
    TextView btn_cases_wise;
    private Context mContext;
    RadioGroup toggle_radio;
    RadioButton radio_death, radio_case;
    CountryWiseListModel countryWiseListModel;
    ArrayList<CountryWiseBarModel> countryWiseBarDeathModelsList;
    ArrayList<CountryWiseBarModel> countryWiseBarCasesModelsList;
    ArrayList<CountryWiseBarModel> countryWiseBarNewDeathModelsList;
    private RecyclerView country_list_recycler_View;
    private FloatingActionButton search_country;
    private CountryWiseListAdapter countryWiseListAdapter;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_country_wise_graph, container, false);
        initUI();
        initObj();
        setUpRecyclerView();
        return view;
    }

    private void initUI() {
        barChart = view.findViewById(R.id.bar_chart_country_wise);
        bar_chart_country_wise_second = view.findViewById(R.id.bar_chart_country_wise_second);
        btn_Death_wise = view.findViewById(R.id.btn_Death_wise);
        btn_cases_wise = view.findViewById(R.id.btn_cases_wise);
        toggle_radio = view.findViewById(R.id.toggle_radio);
        radio_death = view.findViewById(R.id.radio_death);
        radio_case = view.findViewById(R.id.radio_case);

        search_country = view.findViewById(R.id.search_country);
        country_list_recycler_View = view.findViewById(R.id.country_list_recycler_View);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            barChart.setNestedScrollingEnabled(true);
        }

        search_country.setOnClickListener(new View.OnClickListener()
{
            @Override
            public void onClick (View v){
                openRasifalBottomSheet();
            }
        });

        radioButtonClickListner();

    }
    public void notifyRecyclerAdapter(CountryWiseListModel countryWiseListModelll) {
        countryWiseListModel.countries_stat.clear();
        countryWiseListModel.countries_stat.addAll(countryWiseListModelll.countries_stat);
        countryWiseListModel.statistic_taken_at = countryWiseListModelll.statistic_taken_at;
        CountryWiseListModel databaseCountrywiseListModel = CoroCountryWiseDatabaseUtils.saveCountryWiseModel(NewsVariables.NewsOrCoronaTypesEnum.CORONA_COUNTRY_WISE_STATE);
        if (databaseCountrywiseListModel != null) {
            countryWiseListModel.countries_stat = databaseCountrywiseListModel.countries_stat;
            countryWiseListModel.statistic_taken_at = databaseCountrywiseListModel.statistic_taken_at;
            countryWiseListAdapter.notifyDataSetChanged();
        }

    }
    private void radioButtonClickListner() {
        toggle_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (radio_case.isChecked()) {
                    if (bar_chart_country_wise_second.getVisibility() == View.GONE) {
                        bar_chart_country_wise_second.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.GONE);
                    }
                    radio_case.setTextColor(mContext.getResources().getColor(R.color.white));
                    radio_death.setTextColor(mContext.getResources().getColor(R.color.black));
                } else if (radio_death.isChecked()) {
                    if (barChart.getVisibility() == View.GONE) {
                        barChart.setVisibility(View.VISIBLE);
                        bar_chart_country_wise_second.setVisibility(View.GONE);
                    }
                    radio_death.setTextColor(mContext.getResources().getColor(R.color.white));
                    radio_case.setTextColor(mContext.getResources().getColor(R.color.black));

                }
            }
        });
    }

    public void openCountryDetail(int position) {
        Bundle goldBundle = new Bundle();
        goldBundle.putInt("index", position);
        CoronaDetailBottomSheet btmSheetFragGoldPrice =
                CoronaDetailBottomSheet.newInstance(goldBundle);
        btmSheetFragGoldPrice.show(getActivity().getSupportFragmentManager(),
                "BottomSheetCoronaDetail");
    }


    private void openRasifalBottomSheet() {

        BtmSheetSearchFramgnent btmSheetSearchFramgnent =
                BtmSheetSearchFramgnent.newInstance();
        btmSheetSearchFramgnent.show(getActivity().getSupportFragmentManager(),
                "BottomSheetSearch");
        btmSheetSearchFramgnent.setBtmSheetSearchFragment(new BaseSearchBtmSheetFrag.BtmSheetSearchListener() {
            @Override
            public void onSearchResultClick(int index) {
                openCountryDetail(index);
            }
        });

    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        countryWiseListModel = new CountryWiseListModel();
        CountryWiseListModel databaseCountrywiseListModel = CoroCountryWiseDatabaseUtils.saveCountryWiseModel(NewsVariables.NewsOrCoronaTypesEnum.CORONA_COUNTRY_WISE_STATE);
        if (databaseCountrywiseListModel != null) {
            countryWiseListModel.countries_stat = databaseCountrywiseListModel.countries_stat;
            countryWiseListModel.statistic_taken_at = databaseCountrywiseListModel.statistic_taken_at;
        }
        countryWiseListAdapter = new CountryWiseListAdapter(countryWiseListModel, getActivity().getSupportFragmentManager());
        country_list_recycler_View.setLayoutManager(layoutManager);
        country_list_recycler_View.setAdapter(countryWiseListAdapter);
    }

    public void setBaseFragCountryWiseData(CoronaWorldDataListener coronaWorldDataListener) {
        super.setUpBaseFragCoronaWorldData(coronaWorldDataListener);
        super.notifyUpdated();
    }

    private void initObj() {
        countryWiseBarDeathModelsList = ListUtils.getDeathList();
        countryWiseBarCasesModelsList = ListUtils.getCasesList();
        countryWiseBarNewDeathModelsList = ListUtils.getNewDeathList();
        barEntriesSecond = new ArrayList<>();
        barEntries = new ArrayList<>();

//        setDeathListBar(countryWiseBarDeathModelsList,"Death By corona in each country");
        setDeathListBar(countryWiseBarDeathModelsList, "Death By corona in each country");
        setCasesListBarSecond(countryWiseBarCasesModelsList, "Cases of corona in each country");
    }

    public void setCasesListBarSecond(ArrayList<CountryWiseBarModel> countryWiseBarModelsList, String lableBarDataSet) {
        if (countryWiseBarModelsList != null) {
            if (barDataSecond == null) {
                labelsSecond = new String[countryWiseBarModelsList.size()];
                for (int i = 0; i < countryWiseBarModelsList.size(); i++) {
                    float barValue = countryWiseBarModelsList.get(i).barYValue;
                    String country = countryWiseBarModelsList.get(i).countryName;
                    CountryWiseBarModel countryWiseDataModel = countryWiseBarModelsList.get(i);
                    barEntriesSecond.add(new BarEntry(i, barValue, countryWiseDataModel));
                    labelsSecond[i] = country;
                }
                barDataSetSecond = new BarDataSet(barEntriesSecond, lableBarDataSet);
                barDataSetSecond.setStackLabels(labels);
                barDataSecond = new BarData(barDataSetSecond);
                bar_chart_country_wise_second.setData(barDataSecond);
                barDataSetSecond.setColors(ColorTemplate.VORDIPLOM_COLORS);
                barDataSetSecond.setValueTextColor(Color.BLACK);
                bar_chart_country_wise_second.zoom(25, 0, 25, 0);
                bar_chart_country_wise_second.disableScroll();
                bar_chart_country_wise_second.setDoubleTapToZoomEnabled(false);
                bar_chart_country_wise_second.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

            } else {
                barEntriesSecond.clear();
                labelsSecond = new String[countryWiseBarModelsList.size()];
                for (int i = 0; i < countryWiseBarModelsList.size(); i++) {
                    float barValue = countryWiseBarModelsList.get(i).barYValue;
                    String country = countryWiseBarModelsList.get(i).countryName;
                    CountryWiseBarModel countryWiseDataModel = countryWiseBarModelsList.get(i);
                    barEntriesSecond.add(new BarEntry(i, barValue, countryWiseDataModel));
                    labelsSecond[i] = country;
                }

                barDataSecond.notifyDataChanged();

            }
            bar_chart_country_wise_second.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    CountryWiseBarModel countryWiseBarModel = (CountryWiseBarModel) e.getData();
                    int a = Math.round(e.getX());
                    Log.d("mymill", "onValueSelected: " + a);

                    Bundle goldBundle = new Bundle();
                    goldBundle.putInt("index", a);
                    CoronaDetailBottomSheet btmSheetFragGoldPrice =
                            CoronaDetailBottomSheet.newInstance(goldBundle);
                    btmSheetFragGoldPrice.show(getActivity().getSupportFragmentManager(),
                            "BottomSheetCoronaDetail");
                }

                @Override
                public void onNothingSelected() {

                }
            });
        }


//        if (countryWiseBarModelsList != null) {
//            labelsSecond = new String[countryWiseBarModelsList.size()];
//            for (int i = 0; i < countryWiseBarModelsList.size(); i++) {
//                float barValue = countryWiseBarModelsList.get(i).barYValue;
//                String country = countryWiseBarModelsList.get(i).countryName;
//                CountryWiseBarModel countryWiseDataModel = countryWiseBarModelsList.get(i);
//                barEntriesSecond.add(new BarEntry(i, barValue, countryWiseDataModel));
//                labelsSecond[i] = country;
//            }
//
//            barDataSetSecond = new BarDataSet(barEntriesSecond, lableBarDataSet);
//            barDataSetSecond.setStackLabels(labelsSecond);
//            barDataSecond = new BarData(barDataSetSecond);
//            bar_chart_country_wise_second.setData(barDataSecond);
//            barDataSetSecond.setColors(ColorTemplate.VORDIPLOM_COLORS);
//            barDataSetSecond.setValueTextColor(Color.BLACK);
//            bar_chart_country_wise_second.zoom(25, 0, 25, 0);
//            bar_chart_country_wise_second.disableScroll();
//            bar_chart_country_wise_second.setDoubleTapToZoomEnabled(false);
//            bar_chart_country_wise_second.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
//            bar_chart_country_wise_second.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//                @Override
//                public void onValueSelected(Entry e, Highlight h) {
//                    CountryWiseBarModel countryWiseBarModel = (CountryWiseBarModel) e.getData();
//                    int a = Math.round(e.getX());
//                    Log.d("mymill", "onValueSelected: " + a);
//
//                        Bundle goldBundle = new Bundle();
//                        goldBundle.putInt("index", a);
//                        CoronaDetailBottomSheet btmSheetFragGoldPrice =
//                                CoronaDetailBottomSheet.newInstance(goldBundle);
//                        btmSheetFragGoldPrice.show(getActivity().getSupportFragmentManager(),
//                                "BottomSheetCoronaDetail");
//                    }
//
//                @Override
//                public void onNothingSelected() {
//
//                }
//            });
//        }
    }


    public void setDeathListBar(ArrayList<CountryWiseBarModel> countryWiseBarModelsList, String lableBarDataSet) {
        if (countryWiseBarModelsList != null) {
            countryWiseBarDeathModelsList = countryWiseBarModelsList;
            if (barData == null) {
                labels = new String[countryWiseBarModelsList.size()];
                for (int i = 0; i < countryWiseBarModelsList.size(); i++) {
                    float barValue = countryWiseBarModelsList.get(i).barYValue;
                    String country = countryWiseBarModelsList.get(i).countryName;
                    CountryWiseBarModel countryWiseDataModel = countryWiseBarModelsList.get(i);
                    barEntries.add(new BarEntry(i, barValue, countryWiseDataModel));
                    labels[i] = country;
                }
                barDataSet = new BarDataSet(barEntries, lableBarDataSet);
                barDataSet.setStackLabels(labels);
                barData = new BarData(barDataSet);
                barChart.setData(barData);
                barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barChart.zoom(25, 0, 25, 0);
                barChart.disableScroll();
                barChart.setDoubleTapToZoomEnabled(false);
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

            } else {
                barEntries.clear();
                labels = new String[countryWiseBarModelsList.size()];
                for (int i = 0; i < countryWiseBarModelsList.size(); i++) {
                    float barValue = countryWiseBarModelsList.get(i).barYValue;
                    String country = countryWiseBarModelsList.get(i).countryName;
                    CountryWiseBarModel countryWiseDataModel = countryWiseBarModelsList.get(i);
                    barEntries.add(new BarEntry(i, barValue, countryWiseDataModel));
                    labels[i] = country;
                }
                barData.notifyDataChanged();

//                barChart.zoom(25, 0, 25, 0);

            }
            barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    CountryWiseBarModel countryWiseBarModel = (CountryWiseBarModel) e.getData();
                    int a = Math.round(e.getX());
                    Log.d("mymill", "onValueSelected: " + a);

                    Bundle goldBundle = new Bundle();
                    goldBundle.putInt("index", a);
                    CoronaDetailBottomSheet btmSheetFragGoldPrice =
                            CoronaDetailBottomSheet.newInstance(goldBundle);
                    btmSheetFragGoldPrice.show(getActivity().getSupportFragmentManager(),
                            "BottomSheetCoronaDetail");
                }

                @Override
                public void onNothingSelected() {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Death_wise:
                setDeathWiseUI();
                break;
            case R.id.btn_cases_wise:
                setCaseWiseUI();
                break;
        }
    }


    private void setCaseWiseUI() {
        if (barChart.getVisibility() == View.VISIBLE) {
            barChart.setVisibility(View.GONE);
        }
        if (bar_chart_country_wise_second.getVisibility() == View.GONE) {
            bar_chart_country_wise_second.setVisibility(View.VISIBLE);
        }
        btn_cases_wise.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        btn_cases_wise.setTextColor(mContext.getResources().getColor(R.color.black));
        btn_Death_wise.setTextColor(mContext.getResources().getColor(R.color.white));
        btn_Death_wise.setBackgroundColor(mContext.getResources().getColor(R.color.lignt_red));

    }

    private void setDeathWiseUI() {
        btn_Death_wise.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        btn_Death_wise.setTextColor(mContext.getResources().getColor(R.color.black));
        btn_cases_wise.setTextColor(mContext.getResources().getColor(R.color.white));
        btn_cases_wise.setBackgroundColor(mContext.getResources().getColor(R.color.deep_vlot));
        if (bar_chart_country_wise_second.getVisibility() == View.VISIBLE) {
            bar_chart_country_wise_second.setVisibility(View.GONE);
        }
        if (barChart.getVisibility() == View.GONE) {
            barChart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
