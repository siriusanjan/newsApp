package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.affected_countries.adapter;

import android.content.Context;
import android.os.Bundle;
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
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.country_wise.CountryWiseListModel;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.corona.pager_fragment.bottom_sheet_detail_display.CoronaDetailBottomSheet;

public class CountryWiseListAdapter extends RecyclerView.Adapter<CountryWiseListAdapter.VH> {

    private Context mContext;
    private CountryWiseListModel countryWiseListModel;
    private FragmentManager fragmentManager;
    private ImageView img_corona_detail_close;

    public CountryWiseListAdapter(CountryWiseListModel countryWiseListModel, FragmentManager fragmentManager) {
        this.countryWiseListModel = countryWiseListModel;
        this.fragmentManager = fragmentManager;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_corona_country, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        if (countryWiseListModel.countries_stat.size() > 0) {
            holder.txt_country.setText(countryWiseListModel.countries_stat.get(position).country_name);
            holder.txt_death.setText(countryWiseListModel.countries_stat.get(position).deaths);
            holder.txt_cases.setText(countryWiseListModel.countries_stat.get(position).cases);
            holder.txt_recover.setText(countryWiseListModel.countries_stat.get(position).total_recovered);
            holder.layout_corona_view_wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle goldBundle = new Bundle();
                    goldBundle.putInt("index", position);
                    CoronaDetailBottomSheet btmSheetFragGoldPrice =
                            CoronaDetailBottomSheet.newInstance(goldBundle);
                    btmSheetFragGoldPrice.show(fragmentManager,
                            "BottomSheetCoronaDetail");
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        if (countryWiseListModel.countries_stat != null) {
            return countryWiseListModel.countries_stat.size();
        } else {
            return 0;
        }
    }

    public class VH extends RecyclerView.ViewHolder {
        CardView layout_corona_view_wrapper;
        TextView txt_country,txt_death,txt_cases,txt_recover;

        public VH(@NonNull View itemView) {
            super(itemView);
            txt_country = itemView.findViewById(R.id.txt_country);
            layout_corona_view_wrapper = itemView.findViewById(R.id.layout_corona_view_wrapper);
            txt_death = itemView.findViewById(R.id.txt_death);
            txt_cases = itemView.findViewById(R.id.txt_cases);
            txt_recover = itemView.findViewById(R.id.txt_recover);


        }
    }


    public interface FeaturedListAdapterCallback {
        void onOpenProductDetail(String id);
    }

}


