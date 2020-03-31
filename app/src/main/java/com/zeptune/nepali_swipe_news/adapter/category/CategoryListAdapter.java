package com.zeptune.nepali_swipe_news.adapter.category;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.models.CategoryDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.RasiFalDatum;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int type;
    public static final int CATEGOTY_TYPE = 0, RASIFAL_TYPE = 1, MAIN_CATEGORY_TYPE = 2;
    private ArrayList<CategoryDataModel> categoryList;
    private ArrayList<RasiFalDatum> myRasiFalList;
    private int count = 1;
    private Context mContext;
    private OnCategoryItemClickListener onCategoryItemClickListener;


    public CategoryListAdapter(ArrayList<CategoryDataModel> categorylist, Context mContext, int type, OnCategoryItemClickListener onCategoryItemClickListener) {
        this.mContext = mContext;
        this.categoryList = categorylist;
        this.type = type;
        this.onCategoryItemClickListener = onCategoryItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MainCategoryListViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CategoryDataModel categoryDataModel = categoryList.get(position);
        String name = categoryDataModel.getCode();
        Resources resources = mContext.getResources();
        final int id = resources.getIdentifier(name, "drawable", mContext.getPackageName());
        ((MainCategoryListViewHolder) holder).category_name.setText(categoryDataModel.getName());
        if (position == selectedPosition) {

            ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.third_sub_background));
            ((MainCategoryListViewHolder) holder).category_img.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.white));
            ((MainCategoryListViewHolder) holder).category_name.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.white));
            ((MainCategoryListViewHolder) holder).category_img.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.third_sub_background));
            ((MainCategoryListViewHolder) holder).category_name.setTextColor(mContext.getResources().getColor(R.color.third_sub_background));

        }
        switch (position) {
            case 0:

                ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.wrld));
                break;
            case 1:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.soft_green));

                ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.tops));
                break;
            case 2:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.pink));

                ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.pltc));
                break;
            case 3:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.purpe));

                ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.sprt));
                break;
            case 4:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deep_vlot));

                ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.scte));
                break;
            case 5:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.lightbue));

                ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.wrld));
                break;
            case 6:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));

                ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.busi));
                break;
            case 7:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deepgreen));

                ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.entm));
                break;
            case 8:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deepblue));

                ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.hlth));
                break;
            case 9:
                ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.oths));
                break;
        }
        ((MainCategoryListViewHolder) holder).imageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainCategoryListViewHolder) holder).imageCard.setSelected(true);
                setSelectedPosition(position);
                onCategoryItemClickListener.onCategoryItemSelected(categoryDataModel.getCode());
            }
        });
        count = count + 1;
        if (selectedPosition < 0 && count == 2) {
            selectFirstItem();

        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.view_main_category_list;
    }

    public class MainCategoryListViewHolder extends RecyclerView.ViewHolder {
        ImageView category_img;
        TextView category_name;
        CardView imageCard;

        public MainCategoryListViewHolder(@NonNull View itemView) {
            super(itemView);
            category_name = itemView.findViewById(R.id.category_name);
            category_img = itemView.findViewById(R.id.category_img);
            imageCard = itemView.findViewById(R.id.imageCard);

        }
    }

    private int selectedPosition = -1;

    public void setSelectedPosition(int index) {
        selectedPosition = index;
        notifyDataSetChanged();
    }

    public void selectFirstItem() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setSelectedPosition(0);
            }
        }, 100);
    }

    public interface OnCategoryItemClickListener {
        void onCategoryItemSelected(String newsTypesEnum);
    }
}
