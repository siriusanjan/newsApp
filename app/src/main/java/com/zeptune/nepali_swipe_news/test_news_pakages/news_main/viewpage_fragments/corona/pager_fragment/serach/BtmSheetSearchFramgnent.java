package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.serach;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseListModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.adapter.SearchAdapter;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils.CoronaDatabaseUtils.CoroCountryWiseDatabaseUtils;

import java.util.ArrayList;

public class BtmSheetSearchFramgnent extends BaseSearchBtmSheetFrag {
    private View view;
    private EditText search_text;
    private Context mContext;
    private LinearLayout search_linear_layout;
    private ArrayList<CountryWiseDataModel> countries_statT0Search;
    private ArrayList<SearchResultModel> searchResultTextsList;
    private SearchAdapter searchAdapter;
    private RecyclerView recycler_view_search_result;


    public static BtmSheetSearchFramgnent newInstance() {
        return new BtmSheetSearchFramgnent();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_btm_search, container, false);
        initUI();
        initObj();
        return view;
    }

    private void initUI() {
        search_text = view.findViewById(R.id.search_text);
        search_linear_layout = view.findViewById(R.id.search_linear_layout);
        recycler_view_search_result = view.findViewById(R.id.recycler_view_search_result);
    }

    private void initObj() {
        countries_statT0Search = new ArrayList<>();
        searchResultTextsList = new ArrayList<>();
        setSearchArrayList();
        setSearchResultRecycleView();
        setSearchEditTextListener();
    }

    private void setSearchResultRecycleView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        searchAdapter = new SearchAdapter(searchResultTextsList, new SearchAdapter.SearchAdapterInterface() {
            @Override
            public void onRecyclerViewItemClicked(int index) {
                BtmSheetSearchFramgnent.super.performSearchResultClick(index);
                if (getDialog() != null) {
                    getDialog().dismiss();
                }
            }
        }, getActivity().getSupportFragmentManager());
        recycler_view_search_result.setLayoutManager(layoutManager);
        recycler_view_search_result.setAdapter(searchAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        showSoftKeyboard(search_text);
        search_text.requestFocus();
    }

    public void setBtmSheetSearchFragment(BtmSheetSearchListener btmSheetSearchListener) {
        super.setBtmSheetSearchListener(btmSheetSearchListener);
    }

    private void setSearchEditTextListener() {
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (searchResultTextsList.size() > 0) {
                    searchResultTextsList.clear();
                    searchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for (int i = 0; i < countries_statT0Search.size(); i++) {
                    if ((countries_statT0Search.get(i).country_name.toLowerCase().trim()).contains(s.toString().toLowerCase().trim())) {
                        searchResultTextsList.add(new SearchResultModel(countries_statT0Search.get(i).country_name, countries_statT0Search.get(i).deaths, countries_statT0Search.get(i).cases, countries_statT0Search.get(i).total_recovered, i));
                    }
                }
                searchAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                view.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }

    private void setSearchArrayList() {
        CountryWiseListModel databaseCountrywiseListModel = CoroCountryWiseDatabaseUtils.saveCountryWiseModel(NewsVariables.NewsOrCoronaTypesEnum.CORONA_COUNTRY_WISE_STATE);
        if (databaseCountrywiseListModel != null) {
            if (databaseCountrywiseListModel.countries_stat.size() > 0) {
                if (countries_statT0Search == null) {
                    countries_statT0Search = databaseCountrywiseListModel.countries_stat;
                } else {
                    countries_statT0Search.addAll(databaseCountrywiseListModel.countries_stat);
                }
            }
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
