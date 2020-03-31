package com.zeptune.nepali_swipe_news.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.models.Datum;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;

import java.util.ArrayList;

public class TestVerticalFragmentAdapter extends FragmentStateAdapter {
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
    public TestVerticalFragmentAdapter(@NonNull FragmentActivity fragmentActivity, DataumListModel dataumListModel, Context mContext, Boolean showFromTop) {
        super(fragmentActivity);
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.dataumListModel = dataumListModel;
        this.showFromTop = showFromTop;
        this.dataModels = dataumListModel.getData();
        Log.d("adaptercall", "TestVerticalAdapter: my adapter call");
    }

    public TestVerticalFragmentAdapter( @NonNull FragmentActivity fragmentActivity,DataumListModel dataumListModel, Context mContext, Boolean showFromTop, String code) {
        super(fragmentActivity);
        Log.d("adaptercall", "TestVerticalAdapter: my adapter call code");
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.dataumListModel = dataumListModel;
        this.showFromTop = showFromTop;
        this.code = code;
        this.dataModels = dataumListModel.getData();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("adaptercall", "TestVerticalAdapter: my adapter call code"+position);
//        return VerticalFragment.newInstance(dataumListModel.getData().get(position),mContext);
        return null;
    }


    @Override
    public int getItemCount() {
        Log.d("adaptercall", "TestVerticalAdapter: my adapter call code"+dataModels.size());

        return dataModels.size();
    }
}
