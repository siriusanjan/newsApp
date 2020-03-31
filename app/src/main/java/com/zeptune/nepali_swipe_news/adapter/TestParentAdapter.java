package com.zeptune.nepali_swipe_news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.MenuFragement;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_detail_fragment.NewsDetailFragment;

public class TestParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    MenuFragement menuFragement=new MenuFragement();
    NewsDetailFragment newsDetailFragment=new NewsDetailFragment();


    public TestParentAdapter(Context mContext,FragmentManager fragmentManager){
        this.mContext=mContext;
        this.fragmentManager=fragmentManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(viewType,parent,false);
        return new ParentFragmentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (position){
            case 0:
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.myFragmentContainer,menuFragement);
                break;
            case 1:
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.myFragmentContainer,newsDetailFragment);
                break;
            case 2:
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.myFragmentContainer,menuFragement);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.view_parent_fragment_hoster;
    }
    private class ParentFragmentsViewHolder extends RecyclerView.ViewHolder {
        FrameLayout parent_fragments_container;
        public ParentFragmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_fragments_container=itemView.findViewById(R.id.parent_fragments_container);
        }
    }
}
