package com.zeptune.nepali_swipe_news.adapter;


import android.content.Context;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.zeptune.nepali_swipe_news.all_interfaces.PagingInterface;
import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.models.Datum;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;

import java.util.ArrayList;

public class VerticalViewPagerAdapter extends FragmentStatePagerAdapter {
    Context mContext;
    int a = 0;
    ArrayList<Integer> linkLoadedList = new ArrayList<>();

    ArrayList<Datum> dataModels = new ArrayList<>();
    private final FragmentManager mFragmentManager;
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
    VerticalViewPagerAdapter verticalViewPagerAdapter = this;
    String code;


    public VerticalViewPagerAdapter(FragmentManager fm, DataumListModel dataumListModel, Context mContext, Boolean showFromTop) {
        super(fm);

        this.mFragmentManager = fm;
        this.mContext = mContext;
        this.dataumListModel = dataumListModel;
        this.showFromTop = showFromTop;
    }

    public VerticalViewPagerAdapter(FragmentManager fm, DataumListModel dataumListModel, Context mContext, Boolean showFromTop, String code) {
        super(fm);
        this.mFragmentManager = fm;
        this.mContext = mContext;
        this.dataumListModel = dataumListModel;
        this.showFromTop = showFromTop;
        this.code = code;
    }


    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d("myinstantiateitem", "instantiateItem: "+position);
        return super.instantiateItem(container, position);

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);

    }

    @Override
    public Fragment getItem(int position) {
        Log.d("currentPosition", "getItem: "+position);
        dataModels = dataumListModel.getData();
        if (code == null) {
            PagingInterface pagingInterface = (PagingInterface) mContext;
            linkLoadedList.add(position);

            if (position + 5 >= dataModels.size()) {
                if (isNetworkAvailable()) {
                    if (checkNetwork) {
                        priviousDataSize = dataModels.size();
                        pagingInterface.pageNumber(String.valueOf(Integer.parseInt(dataumListModel.getMeta().getCurrent_page()) + 1), position, null, verticalViewPagerAdapter, null);
                        checkNetwork = false;

                    }
                } else {
                    Toast.makeText(mContext, "network not available", Toast.LENGTH_SHORT).show();
                }
            }
            if (priviousDataSize < currentDataSize) {

                checkNetwork = true;

            }
            NewsInterface newsInterface = (NewsInterface) mContext;
            if (linkLoadedList.size() == 2) {
                newsInterface.newsLink(dataModels.get(0).getFullLink(), 0);
                newsInterface.newImageLink(dataModels.get(0).getImage());
                a = position;
            } else {
                if (a == 1 && position == 0) {
                    newsInterface.newsLink(dataModels.get(1).getFullLink(), 0);
                    newsInterface.newImageLink(dataModels.get(1).getImage());
                    a = position;
                } else if (a < position) {
                    newsInterface.newsLink(dataModels.get(position - 1).getFullLink(), 0);
                    newsInterface.newImageLink(dataModels.get(position - 1).getImage());
                    a = position;
                } else if (a > position) {
                    newsInterface.newsLink(dataModels.get(position + 1).getFullLink(), 0);
                    newsInterface.newImageLink(dataModels.get(position + 1).getImage());
                    a = position;
                } else {
                    newsInterface.newsLink(dataModels.get(position).getFullLink(), 0);
                    newsInterface.newImageLink(dataModels.get(position).getImage());
                    a = position;
                }
            }

        } else {
            PagingInterface pagingInterface = (PagingInterface) mContext;
            linkLoadedList.add(position);

            if (position + 5 >= dataModels.size()) {
                if (isNetworkAvailable()) {
                    if (checkNetwork) {
                        priviousDataSize = dataModels.size();
                        pagingInterface.pageNumber(String.valueOf(Integer.parseInt(dataumListModel.getMeta().getCurrent_page()) + 1), position, null, verticalViewPagerAdapter, code);
                        checkNetwork = false;

                    }
                } else {
                    Toast.makeText(mContext, "network not available", Toast.LENGTH_SHORT).show();
                }
            }
            if (priviousDataSize < currentDataSize) {

                checkNetwork = true;

            }
            NewsInterface newsInterface = (NewsInterface) mContext;
            if (linkLoadedList.size() == 2) {
                newsInterface.newsLink(dataModels.get(0).getFullLink(), 1);
                newsInterface.newImageLink(dataModels.get(0).getImage());
                a = position;
            } else {
                if (a == 1 && position == 0) {
                    newsInterface.newsLink(dataModels.get(1).getFullLink(), 1);
                    newsInterface.newImageLink(dataModels.get(1).getImage());
                    a = position;
                } else if (a < position) {
                    newsInterface.newsLink(dataModels.get(position - 1).getFullLink(), 1);
                    newsInterface.newImageLink(dataModels.get(position - 1).getImage());
                    a = position;
                } else if (a > position) {
                    newsInterface.newsLink(dataModels.get(position + 1).getFullLink(), 1);
                    newsInterface.newImageLink(dataModels.get(position + 1).getImage());
                    a = position;
                } else {
                    newsInterface.newsLink(dataModels.get(position).getFullLink(), 1);
                    newsInterface.newImageLink(dataModels.get(position).getImage());
                    a = position;
                }
            }
        }


//        verticalFragment = VerticalFragment.newInstance(dataModels.get(position), mContext);

        return verticalFragment;
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        Log.d("positionUpdated", "getItemPosition: position updated");
        return POSITION_NONE;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public int getCount() {
        currentDataSize = dataModels.size();
        return dataumListModel.getData().size();
    }


    @Override
    public void registerDataSetObserver(@NonNull DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }


}