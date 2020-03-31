package com.zeptune.nepali_swipe_news.adapter.inUse;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.zeptune.nepali_swipe_news.BuildConfig;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.all_interfaces.ClickCallBack;
import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.models.Datum;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;
import com.zeptune.nepali_swipe_news.test_news_pakages.TestMainActivity;

import java.util.ArrayList;

public class TestVerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    LayoutInflater mInflater;
    Context mContext;
    int a = 0;
    ArrayList<Integer> linkLoadedList = new ArrayList<>();

    ArrayList<Datum> dataModels = new ArrayList<>();
    //    private final FragmentManager mFragmentManager;
    NewsInterface currrentNewsInterface;
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private static final boolean DEBUG = false;
    Fragment verticalFragment;
    DataumListModel dataumListModel;
    Boolean showFromTop;
    boolean checkNetwork = true;
    int priviousDataSize = 1;
    int currentDataSize;
    String code;
    ClickCallBack clickCallBack;

    public TestVerticalAdapter(DataumListModel dataumListModel, Context mContext, Boolean showFromTop) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.dataumListModel = dataumListModel;
        this.showFromTop = showFromTop;
        this.dataModels = dataumListModel.getData();

//        this.clickCallBack = (ClickCallBack) mContext;
    }

    public TestVerticalAdapter(DataumListModel dataumListModel, Context mContext, Boolean showFromTop, String code) {
        Log.d("adapter call", "TestVerticalAdapter: my adapter call code");
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.dataumListModel = dataumListModel;
        this.showFromTop = showFromTop;
        this.code = code;
        this.dataModels = dataumListModel.getData();
        this.clickCallBack = (ClickCallBack) mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(viewType, parent, false);
        Log.d("mypublisheddate", "onBindViewHolder: my bind value" + viewType);
        Log.d("mypublisheddate", "onBindViewHolder: my bind value" + parent);

        switch (viewType) {
            case R.layout.test_fragment_parent:
                return new MyViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (dataumListModel.getData() != null) {


            Datum datum = dataumListModel.getData().get(position);
            ((MyViewHolder) holder).description.setText(datum.getDescription());
            ((MyViewHolder) holder).title.setText(datum.getTitle());
            ((MyViewHolder) holder).newsDate.setText(datum.getPublished());
            ((MyViewHolder) holder).news_writer.setText("Swipe left for more at " + datum.getSource());
            Uri uri = Uri.parse(String.valueOf(Uri.parse(datum.getImage())));
            Glide.with(mContext).load(uri).placeholder(mContext.getResources().getDrawable(R.drawable.blurnoimg)).fitCenter().into(((MyViewHolder) holder).imgNews);
            ((MyViewHolder) holder).seeDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCallBack.onClickSeeMore(datum.getFullLink());
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.test_fragment_parent;
    }

    @Override
    public int getItemCount() {
        if (dataumListModel.getData() != null) {
            return dataumListModel.getData().size();

        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNews, imgForNoImgLink;
        Button seeDetails, btn_Reload;
        TextView title, description, newsDate, news_writer, txt_loading_data, txt_err_message;
        AdView adViewNew;
        ProgressBar loadingProgressBar;
        LinearLayout news_show_err_layout;
        RelativeLayout relative_news_wrapper;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgNews);
            imgForNoImgLink = itemView.findViewById(R.id.imgForNoImgLink);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            seeDetails = itemView.findViewById(R.id.seeDetails);
            newsDate = itemView.findViewById(R.id.newsDate);
            news_writer = itemView.findViewById(R.id.txt_lead_to_Detail);
            adViewNew = itemView.findViewById(R.id.adViewNew);
            relative_news_wrapper = itemView.findViewById(R.id.relative_news_wrapper);
            news_show_err_layout = itemView.findViewById(R.id.news_show_err_layout);
            loadAds();

        }

        private void loadAds() {

            if (BuildConfig.DEBUG) {
                // do something for a debug build
                MobileAds.initialize(((TestMainActivity) mContext), mContext.getString(R.string.banner_test_ads));

            } else {
                MobileAds.initialize(((TestMainActivity) mContext), mContext.getString(R.string.banner_real_ads));

            }
            AdRequest request = new AdRequest.Builder().build();
            adViewNew.loadAd(request);
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
