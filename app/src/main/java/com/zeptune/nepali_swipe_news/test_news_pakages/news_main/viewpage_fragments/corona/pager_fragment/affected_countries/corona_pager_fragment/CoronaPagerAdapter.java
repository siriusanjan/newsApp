package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.corona_pager_fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.corona_pager_fragment.fra_country_wise_data.FragCountryWiseGraph;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.corona_pager_fragment.frag_corona_world_data.FragWorldCoronaData;

public class CoronaPagerAdapter extends FragmentStatePagerAdapter {
    FragWorldCoronaData fragWorldCoronaData;
    FragCountryWiseGraph fragCountryWiseGraph;

    public CoronaPagerAdapter(@NonNull FragmentManager fm, int behavior, FragWorldCoronaData fragWorldCoronaData,FragCountryWiseGraph fragCountryWiseGraph
    ) {
        super(fm, behavior);
        this.fragWorldCoronaData = fragWorldCoronaData;
        this.fragCountryWiseGraph=fragCountryWiseGraph;
    }

    private String[] tabTitles = new String[]{"World", "Country"};

    // overriding getPageTitle()
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragWorldCoronaData;
            case 1:
                return fragCountryWiseGraph;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
