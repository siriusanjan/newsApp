package com.zeptune.nepali_swipe_news.date_converter.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.date_converter.models.DaysDataModel;
import com.zeptune.nepali_swipe_news.date_converter.models.MonthDataModels;

import java.util.ArrayList;


public class DayMonthListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<MonthDataModels> mymonthList;
    private ArrayList<DaysDataModel> mydaysinTheMonth;
    private Context mContext;
    private Monthname monthname;
    private SelectededDaysInterface selectededDaysInterface;
    private int myYear;
    private int type;

    private final int TYPE_MONTH = 0, TYPE_DAY = 1;

    private Boolean isEnglishMonth;

    public DayMonthListAdapter(Context mContext, ArrayList<MonthDataModels> monthList, int type, Monthname monthname, SelectededDaysInterface selectededDaysInterface, Boolean isEnglishMonth) {
        this.selectededDaysInterface = selectededDaysInterface;
        this.monthname = monthname;
        this.mymonthList = monthList;
        this.mContext = mContext;
        this.isEnglishMonth = isEnglishMonth;
        this.type = type;
        Log.d("monthlist", "adapterfordaylisteing " + mymonthList.get(3).getMonthName());
    }

    public DayMonthListAdapter(ArrayList<DaysDataModel> daysinTheMonth, Context mContext, int type, Monthname monthname, SelectededDaysInterface selectededDaysInterface, int year, Boolean isEnglishDay) {
        this.selectededDaysInterface = selectededDaysInterface;
        this.mydaysinTheMonth = daysinTheMonth;
        this.monthname = monthname;
        this.mContext = mContext;
        this.type = type;
        this.myYear = year;
        this.isEnglishMonth = isEnglishDay;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.view_month:
                return new MonthViewHolder(view);
            case R.layout.view_days:
                return new DaysViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        switch (type) {
            case TYPE_MONTH:

                ((MonthViewHolder) holder).txt_monthView.setText(mymonthList.get(position).getMonthName());
                ((MonthViewHolder) holder).monthViewLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        monthname.getMonthName(mymonthList.get(position).getMonthName(), isEnglishMonth);
                    }
                });

                break;
            case TYPE_DAY:
                ((DaysViewHolder) holder).txt_days.setText(mydaysinTheMonth.get(position).getNoOfDays());

                ((DaysViewHolder) holder).innerDaysFragment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        selectededDaysInterface.getSelectedDays(Integer.valueOf(mydaysinTheMonth.get(position).getNoOfDays()), myYear, isEnglishMonth);
                    }
                });


                break;
        }


    }

    @Override
    public int getItemViewType(int position) {

        switch (type) {
            case 0:
                return R.layout.view_month;
            case 1:
                return R.layout.view_days;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {

        if (mymonthList != null) {
            Log.d("monthlist", "getItemCount: " + mymonthList.size());
            return mymonthList.size();

        } else if (mydaysinTheMonth != null) {
            Log.d("pppppppp", "getItemCount: " + mydaysinTheMonth.size());
            return mydaysinTheMonth.size();
        } else {
            return 2;
        }

    }


    public class MonthViewHolder extends RecyclerView.ViewHolder {
        TextView txt_monthView;
        LinearLayout monthViewLayout;
        LinearLayout innerLinearLayout;

        private MonthViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_monthView = itemView.findViewById(R.id.txt_monthView);
            monthViewLayout = itemView.findViewById(R.id.monthViewCard);
        }
    }

    public class DaysViewHolder extends RecyclerView.ViewHolder {
        TextView txt_days;
        LinearLayout innerDaysFragment;

        private DaysViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_days = itemView.findViewById(R.id.txt_days);
            innerDaysFragment = itemView.findViewById(R.id.innerDaysFragment);
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
