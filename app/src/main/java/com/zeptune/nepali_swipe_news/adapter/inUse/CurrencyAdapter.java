package com.zeptune.nepali_swipe_news.adapter.inUse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.currencyDataModel.CurrencyDatum;

import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.VH> {
    private CurrencyAdapterListener currencyAdapterListener;
    private boolean showAll;

    private Context mContext;
    private ArrayList<CurrencyDatum> currencyData;
    private ImageView img_corona_detail_close;

    public CurrencyAdapter(ArrayList<CurrencyDatum> currencyData, boolean showAll, CurrencyAdapterListener currencyAdapterListener) {
        this.currencyData = currencyData;
        this.currencyAdapterListener = currencyAdapterListener;
        this.showAll = showAll;
    }


    @NonNull
    @Override
    public CurrencyAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_currency, parent, false);
        mContext = parent.getContext();
        return new CurrencyAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.VH holder, final int position) {

        if (currencyData.size() > 0) {
            CurrencyDatum currencyDatum = currencyData.get(position);
            holder.txt_currency.setText(currencyDatum.getCurrency());
            holder.txt_value.setText(currencyDatum.getValue());
            holder.txt_buy_value.setText(currencyDatum.getBuy());
            holder.txt_sell_value.setText(currencyDatum.getSell());
//            if (position % 2 == 0) {
//                holder.txt_columns_wrapper_view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//            }else{
//                holder.txt_columns_wrapper_view.setBackgroundColor(mContext.getResources().getColor(R.color.status_bar));
//
//            }


        }
        if (!showAll) {
            holder.txt_columns_wrapper_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currencyAdapterListener.onViewClicked();
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        if (currencyData != null) {
            if (showAll) {

                return currencyData.size();
            } else {
                return 6;
            }
        } else {
            return 0;
        }
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView txt_currency, txt_value, txt_buy_value, txt_sell_value;
        CardView txt_columns_wrapper_view;


        public VH(@NonNull View itemView) {
            super(itemView);
            txt_currency = itemView.findViewById(R.id.txt_currency);
            txt_columns_wrapper_view = itemView.findViewById(R.id.txt_columns_wrapper_view);
            txt_value = itemView.findViewById(R.id.txt_value);
            txt_buy_value = itemView.findViewById(R.id.txt_buy_value);
            txt_sell_value = itemView.findViewById(R.id.txt_sell_value);


        }
    }

    public interface CurrencyAdapterListener {
        void onViewClicked();
    }
}
