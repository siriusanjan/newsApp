package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.corona_pager_fragment;

import androidx.fragment.app.Fragment;

public abstract   class BaseFragCoronaWorldData  extends Fragment {
    CoronaWorldDataListener coronaWorldDataListener;

    public void setUpBaseFragCoronaWorldData(CoronaWorldDataListener coronaWorldDataListener) {
        this.coronaWorldDataListener = coronaWorldDataListener;
    }

    public void notifyUpdated() {
        coronaWorldDataListener.onWorldCoronaDataUpdated();
    }

    public interface CoronaWorldDataListener {
        void onWorldCoronaDataUpdated();
    }
}
