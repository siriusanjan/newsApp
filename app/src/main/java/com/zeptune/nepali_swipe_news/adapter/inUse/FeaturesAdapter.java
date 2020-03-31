package com.zeptune.nepali_swipe_news.adapter.inUse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.all_interfaces.recyclerView_MenuInterface.RasifalInterface;
import com.zeptune.nepali_swipe_news.models.CategoryDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.News_Variabls;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.RasiFalDatum;

import java.util.ArrayList;

public class FeaturesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //Variable
    private News_Variabls.TYPE type;
    public static final int RASIFAL_TYPE_BACK = 0, RASIFAL_TYPE_FRONT = 1;
    private ArrayList<CategoryDataModel> categoryList;
    private ArrayList<RasiFalDatum> myRasiFalList;
    private int count = 1;
    private int front_back_type;


    //Class
    private Context mContext;
    //interface
    private RasifalInterface rasifalInterface;


    public FeaturesAdapter( ArrayList<RasiFalDatum> myRasiFalList, Context mContext, News_Variabls.TYPE type, int front_back_type) {
        this.mContext = mContext;
        this.type = type;
        this.front_back_type = front_back_type;
        this.myRasiFalList = myRasiFalList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        switch (viewType) {
            case R.layout.view_rasifal_front:
                return new RasifalFrontViewHolder(view);
            case R.layout.view_rasifal_details:
                return new RasifalDetailViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(myRasiFalList.size()>0) {
            RasiFalDatum rasiData = myRasiFalList.get(position);
            if (type == News_Variabls.TYPE.RASIFAL_TYPE) {
                switch (front_back_type) {
                    case 1:
                        ((RasifalFrontViewHolder) holder).txt_rasifal_title.setText(rasiData.getMain_title());
                        ((RasifalFrontViewHolder) holder).txt_rasi_description.setText(rasiData.getContent());
                        ((RasifalFrontViewHolder) holder).imageView.setBackground(mContext.getResources().getDrawable(R.drawable.aries));


                        break;
                    case 0:

                        ((RasifalDetailViewHolder) holder).txt_rasi_title.setText(rasiData.getMain_title());
                        ((RasifalDetailViewHolder) holder).txt_rasi_sub_title.setText(rasiData.getTitle());
                        ((RasifalDetailViewHolder) holder).txt_rasi_detail.setText(rasiData.getContent());
                        switch (position){
                            case 0:
                                ((RasifalDetailViewHolder) holder).img_rasi.setBackground(mContext.getResources().getDrawable(R.drawable.aries));
break;
                            case 1:
                                ((RasifalDetailViewHolder) holder).img_rasi.setBackground(mContext.getResources().getDrawable(R.drawable.taurus));
                                break;
                            case 2:
                                ((RasifalDetailViewHolder) holder).img_rasi.setBackground(mContext.getResources().getDrawable(R.drawable.gemini));
                                break;
                            case 3:
                                ((RasifalDetailViewHolder) holder).img_rasi.setBackground(mContext.getResources().getDrawable(R.drawable.cancer));
                                break;
                            case 4:
                                ((RasifalDetailViewHolder) holder).img_rasi.setBackground(mContext.getResources().getDrawable(R.drawable.lion));
                                break;
                            case 5:
                                ((RasifalDetailViewHolder) holder).img_rasi.setBackground(mContext.getResources().getDrawable(R.drawable.virgo));
                                break;
                            case 6:
                                ((RasifalDetailViewHolder) holder).img_rasi.setBackground(mContext.getResources().getDrawable(R.drawable.libra));
                                break;
                            case 7:
                                ((RasifalDetailViewHolder) holder).img_rasi.setBackground(mContext.getResources().getDrawable(R.drawable.scorpio));
                                break;
                            case 8:
                                ((RasifalDetailViewHolder) holder).img_rasi.setBackground(mContext.getResources().getDrawable(R.drawable.sagittarius));
                                break;
                            case 9:
                                ((RasifalDetailViewHolder) holder).img_rasi.setBackground(mContext.getResources().getDrawable(R.drawable.cabicar));
                                break;
                            case 10:
                                ((RasifalDetailViewHolder) holder).img_rasi.setBackground(mContext.getResources().getDrawable(R.drawable.aquarius));
                                break;
                            case 11:
                                ((RasifalDetailViewHolder) holder).img_rasi.setBackground(mContext.getResources().getDrawable(R.drawable.min));
                                break;

                        }
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (front_back_type == FeaturesAdapter.RASIFAL_TYPE_FRONT) {
            return 1;
        } else if (front_back_type == FeaturesAdapter.RASIFAL_TYPE_BACK) {
            return myRasiFalList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (front_back_type == FeaturesAdapter.RASIFAL_TYPE_FRONT) {
            return R.layout.view_rasifal_front;
        } else if (front_back_type == FeaturesAdapter.RASIFAL_TYPE_BACK) {
            return R.layout.view_rasifal_details;
        } else {
            return -1;
        }
    }

    //Rasifal ViewHolder

    /**
     * view holder for the single rasifal view
     */

    public class RasifalFrontViewHolder extends RecyclerView.ViewHolder {
        TextView txt_rasifal_title, txt_rasi_description;
        ImageView imageView;

        private RasifalFrontViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_rasi_description = itemView.findViewById(R.id.txt_rasi_description);
            txt_rasifal_title = itemView.findViewById(R.id.txt_rasifal_title);
            imageView = itemView.findViewById(R.id.img_rasi_short);
        }
    }

    /**
     * view holder for the all rasifal view
     */
    private class RasifalDetailViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_rasi;
        private TextView txt_rasi_title, txt_rasi_sub_title, txt_rasi_detail;

        private RasifalDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            img_rasi = itemView.findViewById(R.id.img_rasi);
            txt_rasi_detail = itemView.findViewById(R.id.txt_rasi_detail);
            txt_rasi_sub_title = itemView.findViewById(R.id.txt_rasi_sub_title);
            txt_rasi_title = itemView.findViewById(R.id.txt_rasi_title);
        }
    }
}
