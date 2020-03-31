package com.zeptune.nepali_swipe_news.date_converter;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.date_converter.adapter.DayMonthListAdapter;
import com.zeptune.nepali_swipe_news.date_converter.adapter.Monthname;
import com.zeptune.nepali_swipe_news.date_converter.adapter.SelectededDaysInterface;
import com.zeptune.nepali_swipe_news.date_converter.converter.ConvertAdToBs;
import com.zeptune.nepali_swipe_news.date_converter.converter.ConvertBsToAd;
import com.zeptune.nepali_swipe_news.date_converter.interface_date.NepaliDateInterface;
import com.zeptune.nepali_swipe_news.date_converter.models.DaysDataModel;
import com.zeptune.nepali_swipe_news.date_converter.models.MonthDataModels;
import com.zeptune.nepali_swipe_news.date_converter.models.NepaliDateHasmap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



import static android.view.ViewGroup.FOCUS_AFTER_DESCENDANTS;


public class FragDateConverter extends Fragment implements Monthname, SelectededDaysInterface, View.OnClickListener, NepaliDateInterface {
    private Context mContext;
    private NepaliDateHasmap nepaliDateHasmap;
    //	Spinner spinner1, spinner2, spinner3;
    private EditText english_year, english_month, english_day, nepali_year, nepali_month, nepali_day;
    private int englishYear = 1944;
    private int nepaliYear = 2000;
    private static final int MAXNepaliYear = 2089;
    private static final int MINNepaliYear = 2000;
    private static final int MAXEnglishYear = 2033;
    private static final int MINEnglishYear = 1944;
    private static final String STARTING_ENG_MONTH = "Januray";
    private static final String STARTING_NEP_MONTH = "Baishakh";
    private static final int YearStringMaxLength = 3;
    private TextView bs_day_text;
    private TextView ad_day_text;
    private TextView finalNepaliConvertedDate;
    private TextView finalEnglishConvertedDate;
    private RelativeLayout dateConverter_main_layout;
    private DayMonthListAdapter dayMonthListAdapter;
    private String thisMonthName;
    private int nepChangeFocus = 1;
    private int engChangeFocus = 1;
    private boolean onCallBack = false;
    private boolean isBSAvaialable=false;

    private String BAISHAK = "Baishak";

    private int finalMonth;
    private Date date;
    private DateFormat dayFormat, monthFormat;
    private String currentEngYear, currentEngDay, currentEngMonth;
    private String currentNepYear, currentNepMonth, currentNepDay;


    public FragDateConverter() {
        // Required empty public constructor
    }

    private BottomSheetDialog bottomSheetDialogForDays, bottomSheetDialogForMonth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View i = inflater.inflate(R.layout.fragment_date_convert, container, false);


        initUI(i);
        initObj();


        currentEngDay = dayFormat.format(date);
        currentEngMonth = getEnglishMonthListing().get(Integer.parseInt(monthFormat.format(date)) - 1).getMonthName();
        currentEngYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        englishYear = Integer.parseInt(currentEngYear);

        english_year.setText(currentEngYear);
        english_month.setText(currentEngMonth);
        english_day.setText(currentEngDay);
        new ConvertAdToBs(currentEngYear, monthFormat.format(date), currentEngDay, this);
        onCallBack = false;

        onEngYearEditTextChange();
        onEngYearFocusChange();
        onEngMonthFocusChange();
        onEngDayFocusChange();
        onEngYearClick();


        onNepYearEditTextChange();
        onNepYearFocusChange();
        onNepMonthFocusChange();
        onNepDayFocusChange();
        onNepYearClick();


        Log.d("monthlist", "onCreateView: " + getEnglishMonthListing().size());


        return i;
    }


    private void initObj() {

        nepaliDateHasmap = new NepaliDateHasmap();
        bottomSheetDialogForMonth = new BottomSheetDialog(mContext);
        bottomSheetDialogForDays = new BottomSheetDialog(mContext);
        date = new Date();
        dayFormat = new SimpleDateFormat("dd");
        monthFormat = new SimpleDateFormat("MM");


    }

    private void initUI(View i) {
        english_year = i.findViewById(R.id.english_year);
        english_month = i.findViewById(R.id.english_month);
        english_day = i.findViewById(R.id.english_day);
        nepali_day = i.findViewById(R.id.nepali_day);
        nepali_year = i.findViewById(R.id.nepali_year);
        nepali_month = i.findViewById(R.id.nepali_month);
        bs_day_text = i.findViewById(R.id.bs_day_text);
        ad_day_text = i.findViewById(R.id.ad_day_text);
        dateConverter_main_layout = i.findViewById(R.id.dateConverter_main_layout);
        dateConverter_main_layout.setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        english_year.setFocusableInTouchMode(true);


    }


    public void onNepYearEditTextChange() {
        nepali_year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > YearStringMaxLength) {
                    nepaliYear = Integer.parseInt(s.toString());
                } else {
                    nepaliYear = MINNepaliYear;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nepali_year.setTextColor(mContext.getResources().getColor(R.color.black));
                if (s.length() > YearStringMaxLength) {
                    nepaliYear = Integer.parseInt(s.toString());
                } else {
                    nepaliYear = MINNepaliYear;
                }
                if (s.length() > YearStringMaxLength) {
                    if (!onCallBack) {
                        Log.d("requestedFocus", "afterTextChanged: focus");

                        nepChangeFocus = nepChangeFocus + 1;
                        int givenYear = Integer.parseInt(s.toString());
//                        if (nepChangeFocus > 2) {
                        if (MINNepaliYear <= givenYear && MAXNepaliYear >= givenYear) {

                            nepali_month.requestFocus();
                        } else {
                            nepali_year.setTextColor(mContext.getResources().getColor(R.color.purpe));
                            Toast.makeText(mContext, "Invalid Year", Toast.LENGTH_SHORT).show();
                        }
//                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > YearStringMaxLength) {

                    if (Integer.parseInt(s.toString()) < MINNepaliYear) {
                        nepaliYear = MINNepaliYear;
                    } else if (Integer.parseInt(s.toString()) > MAXNepaliYear) {
                        nepaliYear = MAXNepaliYear;
                    }
                    nepaliYear = Integer.parseInt(s.toString());
                }

                nepali_month.setText("");
                nepali_day.setText("");


            }


        });

    }

    private void onNepYearFocusChange() {
        nepali_year.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onCallBack = false;
                if (!hasFocus) {
                    if (nepali_year.getText().length() > YearStringMaxLength) {
                        if (Integer.parseInt(nepali_year.getText().toString()) < MINNepaliYear) {
                            nepaliYear = MINNepaliYear;
                            nepali_year.setText(String.valueOf(MINNepaliYear));
                        } else if (Integer.parseInt(nepali_year.getText().toString()) > MAXNepaliYear) {
                            nepaliYear = MAXNepaliYear;
                            nepali_year.setText(String.valueOf(MAXNepaliYear));
                        }
                        nepaliYear = Integer.parseInt(nepali_year.getText().toString());
                    } else {
                        nepali_year.setText(currentNepYear);
                    }
                } else {
                    Log.d("focustraker", "onFocusChange: nepli year focus");
                    if (nepali_year.length() > 3) {

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("focustraker", "onFocusChange: nepli year selected");
                                nepali_year.setSelection(2, 4);
                            }

                        });
                    }

                }
                if (TextUtils.isEmpty(nepali_month.getText())) {
                    nepali_month.setText(currentNepMonth);
                }
                if (TextUtils.isEmpty(nepali_day.getText())) {
                    nepali_day.setText(currentNepDay);
                }
            }
        });

    }

    private void onEngYearClick() {

        english_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onNepaliYearClick", "onClick: english year click");
                onCallBack = false;
                if (english_year.length() >YearStringMaxLength) {
                    nepali_year.getSelectionEnd();
                    english_year.setSelection(0, 4);
                }

            }
        });

    }

    private void onNepYearClick() {

        nepali_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onNepaliYearClick", "onClick: nepali year click");
                onCallBack = false;
                if (nepali_year.length()>YearStringMaxLength) {
                    english_year.getSelectionEnd();
                    nepali_year.setSelection(2, 4);
                }
            }
        });

    }

    private void onNepMonthFocusChange() {
        nepali_month.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideSoftKeyboard(nepali_month);
                    if (nepaliYear < MINNepaliYear) {
                        nepaliYear = MINNepaliYear;
                        nepali_year.setText(String.valueOf(MINNepaliYear));
                    } else if (nepaliYear > MAXNepaliYear) {
                        nepaliYear = MAXNepaliYear;
                        nepali_year.setText(String.valueOf(MAXNepaliYear));
                    } else if (nepali_year.getText().length() == 0) {
                        nepaliYear = MINNepaliYear;
                        nepali_year.setText(String.valueOf(MINNepaliYear));
                    }

                    thisMonthName = nepali_month.getText().toString();
                    nepali_day.setText("");
                    openBottomSheetForMonth(nepaliYear, false);
                    nepali_month.clearFocus();
                    english_year.clearFocus();
                    english_year.setSelection(0);
                } else {
                    thisMonthName = nepali_month.getText().toString();
                }
            }
        });

    }

    private void onNepDayFocusChange() {
        nepali_day.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideSoftKeyboard(nepali_day);
                    english_year.setSelection(0);

                    if (nepaliYear < MINNepaliYear) {
                        nepaliYear = MINNepaliYear;
                        nepali_year.setText(String.valueOf(MINNepaliYear));
                    } else if (nepaliYear > MAXNepaliYear) {
                        nepaliYear = MAXNepaliYear;
                        nepali_year.setText(String.valueOf(MAXNepaliYear));
                    } else if (nepali_year.getText().length() == 0) {
                        nepaliYear = MINNepaliYear;
                        nepali_year.setText(String.valueOf(MINNepaliYear));
                    }
                    openBottomSheetForDays(nepali_month.getText().toString(), nepaliYear, false);
                    nepali_month.clearFocus();
                    nepali_day.clearFocus();
                    english_year.clearFocus();

                }
            }

        });
    }

    private void onEngYearEditTextChange() {
        english_year.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if (s.length() > YearStringMaxLength) {
                    Log.d("thisisenglishyear", "beforeTextChanged: " + s.toString());
                    englishYear = Integer.parseInt(english_year.getText().toString());


                } else {
                    englishYear = MINEnglishYear;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                english_year.setTextColor(mContext.getResources().getColor(R.color.black));
                if (s.length() > YearStringMaxLength) {
                    String thisEnglishYear = s.toString();
                    englishYear = Integer.parseInt(thisEnglishYear);
                } else {
                    englishYear = MINNepaliYear;
                }
                if (s.length() > YearStringMaxLength) {
                    engChangeFocus = engChangeFocus + 1;
                    if (!onCallBack) {
                        Log.d("requestedFocus", "afterTextChanged:dfadf focus not in call back");


                        int givenYear = Integer.parseInt(s.toString());
                        if (engChangeFocus >= 2) {
                            Log.d("requestedFocus", "afterTextChanged: inside focus english date");
                            if (MINEnglishYear <= givenYear && MAXEnglishYear >= givenYear) {

                                english_month.requestFocus();
                                hideSoftKeyboard(english_year);
                            } else {
                                english_year.setTextColor(mContext.getResources().getColor(R.color.purpe));
                                Toast.makeText(mContext, "Invalid Year", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > YearStringMaxLength) {

                    if (Integer.parseInt(s.toString()) < MINNepaliYear) {
                        englishYear = MINNepaliYear;
                    } else if (Integer.parseInt(s.toString()) > MAXNepaliYear) {
                        englishYear = MAXEnglishYear;
                    }
                    englishYear = Integer.parseInt(s.toString());
                }
                english_month.setText("");
                english_day.setText("");
            }

        });
    }

    private void onEngMonthFocusChange() {
        english_month.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    hideSoftKeyboard(english_month);

                    if (englishYear < MINEnglishYear) {
                        englishYear = MINEnglishYear;
                        english_year.setText(String.valueOf(MINEnglishYear));
                    } else if (englishYear > MAXEnglishYear) {
                        englishYear = MAXEnglishYear;
                        english_year.setText(String.valueOf(MAXEnglishYear));
                    } else if (english_year.getText().length() == 2) {
                        englishYear = MINEnglishYear;
                        english_year.setText(String.valueOf(MINEnglishYear) );
                    }

                    thisMonthName = english_month.getText().toString();
                    english_day.setText("");
                    openBottomSheetForMonth(englishYear, true);
                    english_month.clearFocus();
                    english_year.clearFocus();

                } else {
                    thisMonthName = english_month.getText().toString();
                    english_year.clearFocus();
                }
                english_year.setSelection(0);



            }
        });
    }

    private void onEngDayFocusChange() {


        english_day.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideSoftKeyboard(english_day);

                    if (englishYear < MINEnglishYear) {
                        englishYear = MINEnglishYear;
                        english_year.setText(String.valueOf(MINEnglishYear) );
                    } else if (englishYear > MAXEnglishYear) {
                        englishYear = MAXEnglishYear;
                        english_year.setText(String.valueOf(MAXEnglishYear));
                    } else if (english_year.getText().length() == 0) {
                        englishYear = MINEnglishYear;
                        english_year.setText(String.valueOf(MINEnglishYear) );
                    }
                    openBottomSheetForDays(english_month.getText().toString(), englishYear, true);
                    Log.d("myenglishmonth", "onFocusChange: english month" + english_month.getText().toString());
                    english_day.clearFocus();
                    english_year.clearFocus();
                }else {
                    english_year.clearFocus();
                }
                english_year.setSelection(0);
            }
        });
    }

    private void onEngYearFocusChange() {

        english_year.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onCallBack = false;
                if (!hasFocus) {
                    if (!TextUtils.isEmpty(english_year.getText())) {
                        if (english_year.getText().length() > YearStringMaxLength) {
                            if (Integer.parseInt(english_year.getText().toString()) < MINEnglishYear) {
                                englishYear = MINEnglishYear;
                                english_year.setText(String.valueOf(MINEnglishYear));
                            } else if (Integer.parseInt(english_year.getText().toString()) > MAXEnglishYear) {
                                englishYear = MAXEnglishYear;
                                english_year.setText(String.valueOf(MAXEnglishYear));
                            }else {
                                englishYear = Integer.parseInt(english_year.getText().toString());
                            }
                        } else {
                            english_year.setText(currentEngYear);
                        }
                        if (TextUtils.isEmpty(english_month.getText())) {
                            english_month.setText(currentEngMonth);
                        }
                        if (TextUtils.isEmpty(english_day.getText())) {
                            english_day.setText(currentEngDay);
                        }

                    }
                    english_year.setSelection(0);
                } else {
                    Log.d("focustraker", "onFocusChange: english year focus");
                    if (english_year.length() >YearStringMaxLength) {

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                english_year.setSelection(0, 4);
                                Log.d("focustraker", "onFocusChange: english year selection");
                            }

                        });
                    }

                }
            }
        });
    }

    private ArrayList<MonthDataModels> getNepaliMonthListing() {
        ArrayList<MonthDataModels> nepaimontlist = new ArrayList<>();
        nepaimontlist.add(new MonthDataModels(0, "Baishak"));
        nepaimontlist.add(new MonthDataModels(1, "Jestha"));
        nepaimontlist.add(new MonthDataModels(2, "Ashad"));
        nepaimontlist.add(new MonthDataModels(3, "Shrawan"));
        nepaimontlist.add(new MonthDataModels(4, "Bhadra"));
        nepaimontlist.add(new MonthDataModels(5, "Aswin"));
        nepaimontlist.add(new MonthDataModels(6, "Kartik"));
        nepaimontlist.add(new MonthDataModels(7, "Mangsir"));
        nepaimontlist.add(new MonthDataModels(8, "Poush"));
        nepaimontlist.add(new MonthDataModels(9, "Magh"));
        nepaimontlist.add(new MonthDataModels(10, "Falgun"));
        nepaimontlist.add(new MonthDataModels(11, "Chaitra"));
        return nepaimontlist;
    }

    private int getNepaliMonthInInt(String nepaliMonth) {
        Log.d("nepaliMonth", "getNepaliMonthInInt: " + nepaliMonth);
        HashMap<String, Integer> monthHasmap = new HashMap<>();
        monthHasmap.put("baishak", 1);
        monthHasmap.put("jestha", 2);
        monthHasmap.put("ashad", 3);
        monthHasmap.put("shrawan", 4);
        monthHasmap.put("bhadra", 5);
        monthHasmap.put("aswin", 6);
        monthHasmap.put("kartik", 7);
        monthHasmap.put("mangsir", 8);
        monthHasmap.put("poush", 9);
        monthHasmap.put("magh", 10);
        monthHasmap.put("falgun", 11);
        monthHasmap.put("chaitra", 12);
        if (nepaliMonth != null) {
            return monthHasmap.get(nepaliMonth.toLowerCase());
        } else {
            return 1;
        }
    }

    private int getEnglishMonthInt(String englishMonthName) {
        HashMap<String, Integer> monthHasmap = new HashMap<>();
        monthHasmap.put("january", 1);
        monthHasmap.put("february", 2);
        monthHasmap.put("march", 3);
        monthHasmap.put("april", 4);
        monthHasmap.put("may", 5);
        monthHasmap.put("june", 6);
        monthHasmap.put("july", 7);
        monthHasmap.put("august", 8);
        monthHasmap.put("september", 9);
        monthHasmap.put("october", 10);
        monthHasmap.put("november", 11);
        monthHasmap.put("december", 12);

        return monthHasmap.get(englishMonthName.toLowerCase());
    }

    private ArrayList<MonthDataModels> getEnglishMonthListing() {
        ArrayList<MonthDataModels> nepaimontlist = new ArrayList<>();
        nepaimontlist.add(new MonthDataModels(0, "January"));
        nepaimontlist.add(new MonthDataModels(1, "February"));
        nepaimontlist.add(new MonthDataModels(2, "March"));
        nepaimontlist.add(new MonthDataModels(3, "April"));
        nepaimontlist.add(new MonthDataModels(4, "May"));
        nepaimontlist.add(new MonthDataModels(5, "June"));
        nepaimontlist.add(new MonthDataModels(6, "July"));
        nepaimontlist.add(new MonthDataModels(7, "August"));
        nepaimontlist.add(new MonthDataModels(8, "September"));
        nepaimontlist.add(new MonthDataModels(9, "October"));
        nepaimontlist.add(new MonthDataModels(10, "November"));
        nepaimontlist.add(new MonthDataModels(11, "December"));
        return nepaimontlist;
    }

    private ArrayList<MonthDataModels> getContextWiseMonthList(Boolean englishMonthList) {
        if (englishMonthList) {
            return getEnglishMonthListing();
        } else {
            return getNepaliMonthListing();
        }
    }

    private void openBottomSheetForMonth(int year, Boolean monthListForEnglish) {
        final View modelBottomSheet = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_layout_month, null);
        final ArrayList<MonthDataModels> monthlist = getContextWiseMonthList(monthListForEnglish);
        Log.d("ooooooo", "openBottomSheetForMonth: " + monthlist.size());
        RecyclerView recyclerView = modelBottomSheet.findViewById(R.id.bottomsheetRecyclerView);
        TextView txt_year = modelBottomSheet.findViewById(R.id.txt_year);
        txt_year.setText(String.valueOf(year));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(layoutManager);
        if (monthListForEnglish) {
            dayMonthListAdapter = new DayMonthListAdapter(mContext, monthlist, 0, this, this, monthListForEnglish);
        } else {
            dayMonthListAdapter = new DayMonthListAdapter(mContext, monthlist, 0, this, this, monthListForEnglish);

        }
        recyclerView.setAdapter(dayMonthListAdapter);
        bottomSheetDialogForMonth.setContentView(modelBottomSheet);
        bottomSheetDialogForMonth.show();
        english_year.setSelection(0);


    }

    private void openBottomSheetForDays(String monthname, int finalYear, Boolean forEnglihDays) {
        final View daysBottomSheetView = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_days, null);
        RecyclerView bottomsheetRecyclervuewforDays = daysBottomSheetView.findViewById(R.id.bottomsheetRecyclervuewforDays);
        TextView txt_year = daysBottomSheetView.findViewById(R.id.txt_year);
        TextView txt_month = daysBottomSheetView.findViewById(R.id.txt_month);
        txt_year.setText(String.valueOf(finalYear));
        if (TextUtils.isEmpty(monthname) && forEnglihDays) {
            monthname = STARTING_ENG_MONTH;
            english_month.setText(monthname);
        } else if (TextUtils.isEmpty(monthname) && !forEnglihDays) {
            monthname = STARTING_NEP_MONTH;
            nepali_month.setText(monthname);
        }
        txt_month.setText(monthname);
        ArrayList<DaysDataModel> finallist = getContextWiseListForDays(monthname, finalYear, forEnglihDays);

        if (englishYear == 0) {
            englishYear = 1944;
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 7);
        bottomsheetRecyclervuewforDays.setLayoutManager(layoutManager);
        dayMonthListAdapter = new DayMonthListAdapter(finallist, mContext, 1, this, this, finalYear, forEnglihDays);

        bottomsheetRecyclervuewforDays.setAdapter(dayMonthListAdapter);
        bottomSheetDialogForDays = new BottomSheetDialog(mContext);
        bottomSheetDialogForDays.setContentView(daysBottomSheetView);
        bottomSheetDialogForDays.show();
        english_year.setSelection(0);

    }

    private ArrayList<DaysDataModel> getContextWiseListForDays(String monthName, int finalYear, Boolean forEnglishDaysList) {
        if (forEnglishDaysList) {
            return finalListOfEnglishDays(finalYear, monthName);
        } else {
            return finalListOfNepaliDays(finalYear, monthName);
        }
    }

    private ArrayList<DaysDataModel> finalListOfNepaliDays(int year, String month) {
        ArrayList<DaysDataModel> nepaliDaysListing = new ArrayList<>();
        int nepaliMonthInInt = getNepaliMonthInInt(month);
        Map<Integer, int[]> nepaliMapString = NepaliDateHasmap.getNepaliDateMap();
        int noOnepaliDays = nepaliMapString.get(year)[nepaliMonthInInt];
        for (int i = 1; i <= noOnepaliDays; i++) {
            nepaliDaysListing.add(new DaysDataModel(String.valueOf(i)));
        }
        Log.d("ttttt", "finalListOfNepaliDays: " + nepaliMonthInInt);
        finalMonth = nepaliMonthInInt;
        return nepaliDaysListing;
    }

    private ArrayList<DaysDataModel> finalListOfEnglishDays(int year, String mymonth) {
        int daysInTheMonth;
        int[] daysInMonth = new int[]{0, 31, 28, 31, 30, 31, 30, 31,
                31, 30, 31, 30, 31};
        int[] daysInMonthOfLeapYear = new int[]{0, 31, 29, 31, 30,
                31, 30, 31, 31, 30, 31, 30, 31};
        ArrayList<DaysDataModel> daysDataModels = new ArrayList<>();
        int month = getEnglishMonthInt(mymonth);
        String yearInString = String.valueOf(year);
        if (yearInString.endsWith("00")) {
            int x = year % 400;
            if (x == 0) {
                daysInTheMonth = daysInMonthOfLeapYear[month];
            } else {

                daysInTheMonth = daysInMonth[month];
            }
        } else {
            int x = year % 4;
            if (x == 0) {
                daysInTheMonth = daysInMonthOfLeapYear[month];
            } else {
                daysInTheMonth = daysInMonth[month];
            }
        }
        if (daysInTheMonth > 0) {
            for (int i = 1; i <= daysInTheMonth; i++) {
                daysDataModels.add(new DaysDataModel(String.valueOf(i)));
            }
        }
        Log.d("pppppppp", "finalList: " + daysDataModels.size());
        finalMonth = month;
        return daysDataModels;
    }

    @Override
    public void getSelectedDays(int selectedDay, int year, Boolean isEnglishYear) {
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        english_year.setSelection(0);
        bottomSheetDialogForDays.cancel();
        Log.d("converteddate", "days: " + year + finalMonth + selectedDay + "    " + selectedDay);
        String a;
        onCallBack = true;

        if (isEnglishYear) {
            new ConvertAdToBs(String.valueOf(year), String.valueOf(finalMonth), String.valueOf(selectedDay), this);
            english_day.setText(String.valueOf(selectedDay));


        } else {
            new ConvertBsToAd(String.valueOf(year), String.valueOf(finalMonth), String.valueOf(selectedDay), this);
            nepali_day.setText(String.valueOf(selectedDay));


        }


    }


    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void getMonthName(String monthname, Boolean isEnglishMonth) {
        english_year.setSelection(0);
        bottomSheetDialogForMonth.cancel();
        if (isEnglishMonth) {
            openBottomSheetForDays(monthname, englishYear, isEnglishMonth);
            english_month.setText(monthname);
        } else {
            openBottomSheetForDays(monthname, nepaliYear, false);
            nepali_month.setText(monthname);

        }
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void setConvertedDate(String year, String nepMonth, String nepNumberOfMonth, String nepDay, String dayOfTheWeek, boolean isNepali) {
        if (isNepali) {
            currentNepYear = year;
            currentNepMonth = nepMonth;
            currentNepDay = nepDay;
            nepali_year.setText(year);
            nepali_month.setText(nepMonth);
            nepali_day.setText(nepDay);
            nepaliYear = Integer.parseInt(currentNepYear);
        } else {
            currentEngYear = year;
            currentEngMonth = nepMonth;
            currentEngDay = nepDay;
            english_year.setText(year);
            english_month.setText(nepMonth);
            english_day.setText(nepDay);
            Log.d("convertedDay", "setConvertedDate: " + nepDay + nepMonth + year);
        }

        bs_day_text.setText(dayOfTheWeek);
        ad_day_text.setText(dayOfTheWeek);
    }

    private String getStringNumberOnlyFromEditText(EditText editText) {
        String getYear = editText.getText().toString();
        getYear = getYear.substring(0, getYear.length() - 2).trim();
        return getYear;
    }


    private String getStringNumberOnlyFromString(String year) {
        return year.substring(0, year.length() - 2).trim();
    }

}
