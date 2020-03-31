package com.zeptune.nepali_swipe_news.adapter.inUse;

import android.content.Context;
import android.content.Intent;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zeptune.nepali_swipe_news.CategoryActivity;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.all_interfaces.recyclerView_MenuInterface.RasifalInterface;
import com.zeptune.nepali_swipe_news.models.CategoryDataModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.DataRasiFalModel;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.RasiFalDatum;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Variable
    private int type;
    public static final int CATEGOTY_TYPE = 0, RASIFAL_TYPE = 1, MAIN_CATEGORY_TYPE = 2;
    private ArrayList<CategoryDataModel> categoryList;
    private ArrayList<RasiFalDatum> myRasiFalList;
    private int count = 1;


    //Class
    private Context mContext;
    private DataRasiFalModel dataRasiFalModel;
    //interface
    private RasifalInterface rasifalInterface;

    public Adapter(ArrayList<CategoryDataModel> categorylist, Context mContext, int type) {
        this.mContext = mContext;
        this.categoryList = categorylist;
        this.type = type;
    }

    public Adapter(DataRasiFalModel dataRasiFalModel, ArrayList<RasiFalDatum> myRasiFalList, Context mContext, int type, RasifalInterface rasifalInterface) {
        this.mContext = mContext;
        this.dataRasiFalModel = dataRasiFalModel;
        this.type = type;
        this.rasifalInterface = rasifalInterface;
        this.myRasiFalList = myRasiFalList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        switch (viewType) {
            case R.layout.view_rasifal_indicators:
                return new RasifalVieModel(view);
            case R.layout.view_category:
                return new CategoryListViewHolder(view);
            case R.layout.view_main_category_list:
                return new MainCategoryListViewHolder(view);

        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (type) {

            case MAIN_CATEGORY_TYPE:
                final CategoryDataModel categoryDataModel = categoryList.get(position);
                String name = categoryDataModel.getCode();
                Resources resources = mContext.getResources();
                final int id = resources.getIdentifier(name, "drawable", mContext.getPackageName());
                ((MainCategoryListViewHolder) holder).category_name.setText(categoryDataModel.getName());
                if (position == selectedPosition) {
                    ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.white));
                    ((MainCategoryListViewHolder) holder).category_img.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.third_sub_background));
                    ((MainCategoryListViewHolder) holder).category_name.setTextColor(mContext.getResources().getColor(R.color.third_sub_background));


                } else {
                    ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.third_sub_background));
                    ((MainCategoryListViewHolder) holder).category_img.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.white));
                    ((MainCategoryListViewHolder) holder).category_name.setTextColor(mContext.getResources().getColor(R.color.white));

                }
                switch (position) {
                    case 0:

                        ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.tops));
                        break;
                    case 1:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.soft_green));

                        ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.pltc));
                        break;
                    case 2:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.pink));

                        ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.sprt));
                        break;
                    case 3:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.purpe));

                        ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.scte));
                        break;
                    case 4:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deep_vlot));

                        ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.wrld));
                        break;
                    case 5:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.lightbue));

                        ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.busi));
                        break;
                    case 6:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));

                        ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.entm));
                        break;
                    case 7:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deepgreen));

                        ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.hlth));
                        break;
                    case 8:
//                        ((MainCategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deepblue));

                        ((MainCategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.oths));
                        break;
                }
                ((MainCategoryListViewHolder) holder).imageCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainCategoryListViewHolder) holder).imageCard.setSelected(true);
                        setSelectedPosition(position);
                    }
                });
                count = count + 1;
                if (selectedPosition < 0 && count == 2) {
                    selectFirstItem();

                }
                break;
            case CATEGOTY_TYPE:

                final CategoryDataModel categoryDataModel1 = categoryList.get(position);
                String name1 = categoryDataModel1.getCode();
                Resources resources1 = mContext.getResources();
                final int id1 = resources1.getIdentifier(name1, "drawable", mContext.getPackageName());
                ((CategoryListViewHolder) holder).category_name.setText(categoryDataModel1.getName());
                switch (position) {
                    case 0:
                        ((CategoryListViewHolder) holder).inner_constraint_wrapper.setBackgroundColor(mContext.getResources().getColor(R.color.soft_sky_blue));
                        ((CategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.soft_sky_blue));
                        ((CategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.tops));
                        break;
                    case 1:
                        ((CategoryListViewHolder) holder).inner_constraint_wrapper.setBackgroundColor(mContext.getResources().getColor(R.color.soft_green));
                        ((CategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.soft_green));

                        ((CategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.pltc));
                        break;
                    case 2:
                        ((CategoryListViewHolder) holder).inner_constraint_wrapper.setBackgroundColor(mContext.getResources().getColor(R.color.pink));
                        ((CategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.pink));

                        ((CategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.sprt));
                        break;
                    case 3:
                        ((CategoryListViewHolder) holder).inner_constraint_wrapper.setBackgroundColor(mContext.getResources().getColor(R.color.purpe));
                        ((CategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.purpe));

                        ((CategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.scte));
                        break;
                    case 4:
                        ((CategoryListViewHolder) holder).inner_constraint_wrapper.setBackgroundColor(mContext.getResources().getColor(R.color.deep_vlot));
                        ((CategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deep_vlot));

                        ((CategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.wrld));
                        break;
                    case 5:
                        ((CategoryListViewHolder) holder).inner_constraint_wrapper.setBackgroundColor(mContext.getResources().getColor(R.color.lightbue));
                        ((CategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.lightbue));

                        ((CategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.busi));
                        break;
                    case 6:
                        ((CategoryListViewHolder) holder).inner_constraint_wrapper.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                        ((CategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));

                        ((CategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.entm));
                        break;
                    case 7:
                        ((CategoryListViewHolder) holder).inner_constraint_wrapper.setBackgroundColor(mContext.getResources().getColor(R.color.deepgreen));
                        ((CategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deepgreen));

                        ((CategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.hlth));
                        break;
                    case 8:
                        ((CategoryListViewHolder) holder).inner_constraint_wrapper.setBackgroundColor(mContext.getResources().getColor(R.color.deepblue));
                        ((CategoryListViewHolder) holder).imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deepblue));

                        ((CategoryListViewHolder) holder).category_img.setBackground(mContext.getResources().getDrawable(R.drawable.oths));
                        break;
                }
                ((CategoryListViewHolder) holder).imageCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, CategoryActivity.class);
                        intent.putExtra("categoryCode", categoryDataModel1);
                        mContext.startActivity(intent);
                    }
                });
                break;
            case RASIFAL_TYPE:
                ArrayList<RasiFalDatum> myRasifals = myRasiFalList;
                ((RasifalVieModel) holder).rasifal_name.setText(myRasifals.get(position).getMain_title());
                switch (position) {
                    case 0:
                        ((RasifalVieModel) holder).rasifal_imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.soft_green));
                        ((RasifalVieModel) holder).rasifal_img.setBackground(mContext.getResources().getDrawable(R.drawable.tops));
                        break;
                    case 1:
                        ((RasifalVieModel) holder).rasifal_imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.soft_sky_blue));
                        ((RasifalVieModel) holder).rasifal_img.setBackground(mContext.getResources().getDrawable(R.drawable.pltc));
                        break;
                    case 2:
                        ((RasifalVieModel) holder).rasifal_imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.pink));
                        ((RasifalVieModel) holder).rasifal_img.setBackground(mContext.getResources().getDrawable(R.drawable.sprt));
                        break;
                    case 3:
                        ((RasifalVieModel) holder).rasifal_imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.purpe));
                        ((RasifalVieModel) holder).rasifal_img.setBackground(mContext.getResources().getDrawable(R.drawable.scte));
                        break;
                    case 4:
                        ((RasifalVieModel) holder).rasifal_imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deep_vlot));
                        ((RasifalVieModel) holder).rasifal_img.setBackground(mContext.getResources().getDrawable(R.drawable.wrld));
                        break;
                    case 5:
                        ((RasifalVieModel) holder).rasifal_imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.lightbue));
                        ((RasifalVieModel) holder).rasifal_img.setBackground(mContext.getResources().getDrawable(R.drawable.busi));
                        break;
                    case 6:
                        ((RasifalVieModel) holder).rasifal_imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));

                        ((RasifalVieModel) holder).rasifal_img.setBackground(mContext.getResources().getDrawable(R.drawable.entm));
                        break;
                    case 7:
                        ((RasifalVieModel) holder).rasifal_imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deepgreen));
                        ((RasifalVieModel) holder).rasifal_img.setBackground(mContext.getResources().getDrawable(R.drawable.hlth));
                        break;
                    case 8:
                        ((RasifalVieModel) holder).rasifal_imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deepblue));
                        ((RasifalVieModel) holder).rasifal_img.setBackground(mContext.getResources().getDrawable(R.drawable.oths));
                        break;
                    case 9:
                        ((RasifalVieModel) holder).rasifal_imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.lightbue));
                        ((RasifalVieModel) holder).rasifal_img.setBackground(mContext.getResources().getDrawable(R.drawable.oths));
                        break;
                    case 10:
                        ((RasifalVieModel) holder).rasifal_imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.deep_vlot));
                        ((RasifalVieModel) holder).rasifal_img.setBackground(mContext.getResources().getDrawable(R.drawable.oths));
                        break;
                    case 11:
                        ((RasifalVieModel) holder).rasifal_imageCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.soft_sky_blue));
                        ((RasifalVieModel) holder).rasifal_img.setBackground(mContext.getResources().getDrawable(R.drawable.oths));
                        break;


                }
                ((RasifalVieModel) holder).rasifal_imageCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RasiFalDatum rasifalData = myRasifals.get(position);
                        rasifalInterface.getRasifals(rasifalData.getMain_title(), rasifalData.getTitle(), rasifalData.getContent());
                    }
                });
                break;

        }
    }

    @Override
    public int getItemCount() {
        if (type == CATEGOTY_TYPE) {
            return categoryList.size();
        } else if (type == RASIFAL_TYPE) {
            if (myRasiFalList != null) {
                return myRasiFalList.size();

            } else {
                return 0;
            }

        } else if (type == MAIN_CATEGORY_TYPE) {
            return categoryList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (type == RASIFAL_TYPE) {
            return R.layout.view_rasifal_indicators;

        } else if (type == CATEGOTY_TYPE) {
            return R.layout.view_category;
        } else if (type == MAIN_CATEGORY_TYPE) {
            return R.layout.view_main_category_list;
        }
        return R.layout.view_category;
    }

    public class CategoryListViewHolder extends RecyclerView.ViewHolder {
        ImageView category_img;
        ConstraintLayout category_item_relative_layout, inner_constraint_wrapper;
        TextView category_name;
        CardView imageCard;

        public CategoryListViewHolder(@NonNull View itemView) {
            super(itemView);
            category_name = itemView.findViewById(R.id.category_name);
            category_img = itemView.findViewById(R.id.category_img);
//            category_item_relative_layout = itemView.findViewById(R.id.category_item_relative_layout);
            inner_constraint_wrapper = itemView.findViewById(R.id.inner_constraint_wrapper);
            imageCard = itemView.findViewById(R.id.imageCard);

        }
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

    public class RasifalVieModel extends RecyclerView.ViewHolder {
        CardView rasifal_imageCard;
        ImageView rasifal_img;
        TextView rasifal_name;

        public RasifalVieModel(@NonNull View itemView) {
            super(itemView);
            rasifal_imageCard = itemView.findViewById(R.id.rasifal_imageCard);
            rasifal_img = itemView.findViewById(R.id.rasifal_img);
            rasifal_name = itemView.findViewById(R.id.rasifal_name);
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

}
