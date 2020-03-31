package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.serach.SearchResultModel;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.VH> {
    private Context mContext;
    private ArrayList<SearchResultModel> searchResultTextsList;
    private FragmentManager fragmentManager;
    private ImageView img_corona_detail_close;
    private SearchAdapterInterface searchAdapterInterface;

    public SearchAdapter(ArrayList<SearchResultModel> searchResultText, SearchAdapterInterface searchAdapterInterface, FragmentManager fragmentManager) {
        this.searchResultTextsList = searchResultText;
        this.fragmentManager = fragmentManager;
        this.searchAdapterInterface = searchAdapterInterface;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_corona_country, parent, false);
        return new SearchAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.txt_country.setText(searchResultTextsList.get(position).countryName);
        holder.txt_death.setText(searchResultTextsList.get(position).death);
        holder.txt_cases.setText(searchResultTextsList.get(position).total_cases);
        holder.txt_recover.setText(searchResultTextsList.get(position).total_recover);
        holder.layout_corona_view_wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAdapterInterface.onRecyclerViewItemClicked(searchResultTextsList.get(position).index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchResultTextsList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        CardView layout_corona_view_wrapper;
        TextView txt_country, txt_death, txt_cases, txt_recover;

        public VH(@NonNull View itemView) {
            super(itemView);
            txt_country = itemView.findViewById(R.id.txt_country);
            layout_corona_view_wrapper = itemView.findViewById(R.id.layout_corona_view_wrapper);
            txt_death = itemView.findViewById(R.id.txt_death);
            txt_cases = itemView.findViewById(R.id.txt_cases);
            txt_recover = itemView.findViewById(R.id.txt_recover);

        }
    }

    public interface SearchAdapterInterface {
        void onRecyclerViewItemClicked(int index);
    }

}
