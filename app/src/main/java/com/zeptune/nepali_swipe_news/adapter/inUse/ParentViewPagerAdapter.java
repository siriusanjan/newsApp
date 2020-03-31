package com.zeptune.nepali_swipe_news.adapter.inUse;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.features.FeaturesFragment;
import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.models.Datum;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_detail_fragment.NewsDetailFragment;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.NewsFragment;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;

import java.util.ArrayList;

public class ParentViewPagerAdapter extends FragmentStateAdapter {
    private static final int CARD_ITEM_SIZE = 10;
    FragmentActivity fragmentActivity;
    Activity activity;
    int type;

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

    public ParentViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int type) {
        super(fragmentActivity);
        this.type = type;
    }
    public ParentViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int type,String code) {
        super(fragmentActivity);
        this.type = type;
        this.code=code;
    }

    public ParentViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, DataumListModel dataumListModel, Context mContext, Boolean showFromTop, int type) {
        super(fragmentActivity);
        this.type = type;
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.dataumListModel = dataumListModel;
        this.showFromTop = showFromTop;
        this.dataModels = dataumListModel.getData();
        Log.d("adaptercall", "TestVerticalAdapter: my adapter call" + fragmentActivity);
    }

    public ParentViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, DataumListModel dataumListModel, Context mContext, Boolean showFromTop, String code, int type) {
        super(fragmentActivity);
        this.type = type;
        Log.d("adaptercall", "TestVerticalAdapter: my adapter call code" + fragmentActivity);
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
        Log.d("adaptercall", "createFragment: " + position);

        switch (type) {

            case 0:
                switch (position) {
                    case 0:
                        return new FeaturesFragment();
                    case 1:
                        return NewsFragment.newInstance(1,"");
                    case 2:
                        return new NewsDetailFragment();


                }
                break;
            case 1:
                switch (position){
                    case 0:
                        return NewsFragment.newInstance(2,code);
                        case 1:
                        return new NewsDetailFragment();

                }

        }


        return null;
    }

    @Override
    public int getItemCount() {
        if (type == 0)
            return 3;
        else {
            return 2;
        }
    }


}


//
//        extends RecyclerView.Adapter<ParentViewPagerAdapter.MyViewHolder> {
//
//    private Context context;
//    private ArrayList<String> arrayList = new ArrayList<>();
//    FragmentManager fragmentManager;
//    FragmentTransaction fragmentTransaction;
//
//    public ParentViewPagerAdapter(Context context, FragmentManager fm) {
//        this.context = context;
//        this.fragmentManager = fm;
//    }
//
//
//    @NonNull
//    @Override
//    public ParentViewPagerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ParentViewPagerAdapter.MyViewHolder holder, int position) {
//
//        switch (position) {
//            case 0:
//                fragmentTransaction = fragmentManager.beginTransaction();
//                MenuFragement menuFragement = new MenuFragement();
//                if (((MyViewHolder) holder).fragment != null) {
//                    fragmentTransaction.replace(R.id.myFragmentContainer, menuFragement).commit();
//
//                } else {
//                    fragmentTransaction.add(R.id.myFragmentContainer, menuFragement).commit();
//
//                }
//                break;
//            case 1:
//                fragmentTransaction = fragmentManager.beginTransaction();
//                NewsFragment newsFragment = new NewsFragment();
//                Bundle args = new Bundle();
//                args.putInt("type", 1);
//                args.putString("code", "");
//                newsFragment.setArguments(args);
//                holder.frameLayout.setBackgroundColor(context.getResources().getColor(R.color.common_google_signin_btn_text_dark));
//
//                if (((MyViewHolder) holder).fragment != null) {
//                    fragmentTransaction.replace(R.id.myFragmentContainer, newsFragment).commit();
//
//                } else {
//                    fragmentTransaction.add(R.id.myFragmentContainer, newsFragment).commit();
//
//                }
//                break;
//        }
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 2;
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView tvName;
//        Fragment fragment, myFragmentSecondContainer;
//        RelativeLayout frameLayout;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
////        tvName = itemView.findViewById(R.id.tvName);
//            fragment = fragmentManager.findFragmentById(R.id.myFragmentContainer);
//            frameLayout = itemView.findViewById(R.id.myFragmentContainer);
////            myFragmentSecondContainer = fragmentManager.findFragmentById(R.id.myFragmentSecondContainer);
//        }
//    }
//}

//
//@NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        if (type == 1) {
//            if (position == 0) {
//                return new MenuFragement();
//            }
//            if (position == 1) {
//                myPosition = true;
//                return (Fragment) NewsFragment.newInstance(1, "");
//            }
//        } else if (type == 2) {
//
//            if (position == 0) {
//
//                return (Fragment) NewsFragment.newInstance(2, code);
//            }
//            if (position == 1) {
//                return new NewsDetailFragment();
//            }
//        }
//        return null;
//    }
//
//
//        @Override
//        public int getItemCount () {
//            if (type == 1) {
//                return 2;
//            } else if (type == 2) {
//                return 2;
//            }
//            return -1;
//        }
//    }
