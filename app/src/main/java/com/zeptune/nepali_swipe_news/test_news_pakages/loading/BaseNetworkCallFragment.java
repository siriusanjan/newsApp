package com.zeptune.nepali_swipe_news.test_news_pakages.loading;

import androidx.fragment.app.Fragment;

public abstract class BaseNetworkCallFragment extends Fragment {

    private NetworkCallStatusListener networkCallStatusListener;

    void setUpNetworkCall(NetworkCallStatusListener networkCallStatusListener) {
        this.networkCallStatusListener = networkCallStatusListener;
    }

    void performOnSuccess() {
        if(networkCallStatusListener!=null){
            networkCallStatusListener.onCallCompleted(true);
        }
    }


    public interface NetworkCallStatusListener {
        void onCallCompleted(boolean isCompleted);
    }
}
