package com.zeptune.nepali_swipe_news.date_converter.converter;

import android.text.TextUtils;
import android.util.Log;

import com.zeptune.nepali_swipe_news.date_converter.interface_date.NepaliDateInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class ConvertAdToBs {

    private static final int startingNepYear = 2000;
    private static final int startingNepMonth = 1;
    private static final int startingNepDay = 1;
    int dayOfWeek = Calendar.MONDAY;
    private int getDay = 4;
    private NepaliDateInterface myNepaliDateInterface;

    public ConvertAdToBs() {

    }

    public ConvertAdToBs(String engYear, String engMonth, String englishDay, NepaliDateInterface nepaliDateInterface) {
        if (!TextUtils.isEmpty(engYear) && !TextUtils.isEmpty(engMonth) && !TextUtils.isEmpty(englishDay) && nepaliDateInterface != null) {
            this.myNepaliDateInterface = nepaliDateInterface;
            AdDateAfterCoonversion(engYear, engMonth, englishDay);
        }
    }

    public String AdDateAfterCoonversion(String englishYear, String englishMonth, String englishDay) {
        String convertedDate = "";
        Map<Integer, int[]> nepaliMapString = new HashMap<>();

        nepaliMapString.put(2000, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 29, 31});
        nepaliMapString.put(2001, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2002, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2003, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2004, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 29, 31});
        nepaliMapString.put(2005, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2006, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2007, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2008, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30,
                29, 29, 31});
        nepaliMapString.put(2009, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2010, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2011, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2012, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30,
                29, 30, 30});
        nepaliMapString.put(2013, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2014, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2015, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2016, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30,
                29, 30, 30});
        nepaliMapString.put(2017, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2018, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2019, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 29, 31});
        nepaliMapString.put(2020, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2021, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2022, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 30});
        nepaliMapString.put(2023, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 29, 31});
        nepaliMapString.put(2024, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2025, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2026, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2027, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 29, 31});
        nepaliMapString.put(2028, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2029, new int[]{0, 31, 31, 32, 31, 32, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2030, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2031, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 29, 31});
        nepaliMapString.put(2032, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2033, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2034, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2035, new int[]{0, 30, 32, 31, 32, 31, 31, 29, 30, 30,
                29, 29, 31});
        nepaliMapString.put(2036, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2037, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2038, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2039, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30,
                29, 30, 30});
        nepaliMapString.put(2040, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2041, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2042, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2043, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30,
                29, 30, 30});
        nepaliMapString.put(2044, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2045, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2046, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2047, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2048, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2049, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 30});
        nepaliMapString.put(2050, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 29, 31});
        nepaliMapString.put(2051, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2052, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2053, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 30});
        nepaliMapString.put(2054, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 29, 31});
        nepaliMapString.put(2055, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2056, new int[]{0, 31, 31, 32, 31, 32, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2057, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2058, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 29, 31});
        nepaliMapString.put(2059, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2060, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2061, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2062, new int[]{0, 30, 32, 31, 32, 31, 31, 29, 30, 29,
                30, 29, 31});
        nepaliMapString.put(2063, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2064, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2065, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2066, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30,
                29, 29, 31});
        nepaliMapString.put(2067, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2068, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2069, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2070, new int[]{0, 31, 31, 31, 32, 31, 31, 29, 30, 30,
                29, 30, 30});
        nepaliMapString.put(2071, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2072, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2073, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 31});
        nepaliMapString.put(2074, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2075, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2076, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 30});
        nepaliMapString.put(2077, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 29, 31});
        nepaliMapString.put(2078, new int[]{0, 31, 31, 31, 32, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2079, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 29, 30,
                29, 30, 30});
        nepaliMapString.put(2080, new int[]{0, 31, 32, 31, 32, 31, 30, 30, 30, 29,
                29, 30, 30});
        nepaliMapString.put(2081, new int[]{0, 31, 31, 32, 32, 31, 30, 30, 30, 29,
                30, 30, 30});
        nepaliMapString.put(2082, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 30, 30});
        nepaliMapString.put(2083, new int[]{0, 31, 31, 32, 31, 31, 30, 30, 30, 29,
                30, 30, 30});
        nepaliMapString.put(2084, new int[]{0, 31, 31, 32, 31, 31, 30, 30, 30, 29,
                30, 30, 30});
        nepaliMapString.put(2085, new int[]{0, 31, 32, 31, 32, 30, 31, 30, 30, 29,
                30, 30, 30});
        nepaliMapString.put(2086, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 30, 30});
        nepaliMapString.put(2087, new int[]{0, 31, 31, 32, 31, 31, 31, 30, 30, 29,
                30, 30, 30});
        nepaliMapString.put(2088, new int[]{0, 30, 31, 32, 32, 30, 31, 30, 30, 29,
                30, 30, 30});
        nepaliMapString.put(2089, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 30, 30});
        nepaliMapString.put(2090, new int[]{0, 30, 32, 31, 32, 31, 30, 30, 30, 29,
                30, 30, 30});
        int engYear = Integer.parseInt(englishYear);

        int engMonth = Integer.parseInt(englishMonth);

        int engDay = Integer.parseInt(englishDay);

        int nepYear = startingNepYear;
        int nepMonth = startingNepMonth;
        int nepDay = startingNepDay;

        String startStr = "13/4/1943";
        String endStr = engDay + "/" + engMonth + "/" + engYear;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        long totalEngDaysCount;
        try {
            Date startDate = sdf.parse(startStr);
            Date endDate = sdf.parse(endStr);
            long totalEngDaysCountTimeMile = endDate.getTime() - startDate.getTime();
            totalEngDaysCount = TimeUnit.DAYS.convert(totalEngDaysCountTimeMile, TimeUnit.MILLISECONDS);


            Log.d("totaldaysdifference", "onClick: Bs fragment " + totalEngDaysCount);
            Log.d("totaldaysdifference", "onClick: Bs fragment " + endStr);

            while (totalEngDaysCount != 0) {

                int daysInIthMonth = nepaliMapString.get(nepYear)[nepMonth];

                nepDay++;
                if (nepDay > daysInIthMonth) {
                    nepMonth++;
                    nepDay = 1;
                }

                if (nepMonth > 12) {
                    nepYear++;
                    nepMonth = 1;
                }

                getDay++; // count the getSelectedDays in terms of 7 getSelectedDays
                if (getDay > 7) {
                    getDay = 1;
                }

                totalEngDaysCount--;
            }
            Log.d("totaldaysdifference", "onClick: BS fragment " + getDay);
            String thisNepMonth = nepaliMonthHasMap().get(nepMonth - 1);
            if (myNepaliDateInterface != null) {
                myNepaliDateInterface.setConvertedDate(String.valueOf(nepYear), thisNepMonth, String.valueOf(nepMonth), String.valueOf(nepDay), dayOfTheWeekList().get(getDay - 1), true);
                getDay = 4;
            }
            switch (getDay) {
                case 1:
                    convertedDate = +nepDay + " "
                            + nepaliMonthHasMap().get(nepMonth - 1) + ", " + nepYear + "\nSunday";
                    getDay = 4;
                    break;
                case 2:
                    convertedDate = +nepDay + " "
                            + nepaliMonthHasMap().get(nepMonth - 1) + ", " + nepYear + "\nMonday";
                    getDay = 4;
                    break;
                case 3:
                    convertedDate = +nepDay + " "
                            + nepaliMonthHasMap().get(nepMonth - 1) + ", " + nepYear + "\nTuesday";
                    getDay = 4;
                    break;
                case 4:
                    convertedDate = +nepDay + " "
                            + nepaliMonthHasMap().get(nepMonth - 1) + ", " + nepYear + "\nWednesday";
                    getDay = 4;
                    break;
                case 5:
                    convertedDate = +nepDay + " "
                            + nepaliMonthHasMap().get(nepMonth - 1) + ", " + nepYear + "\nThursday";
                    getDay = 4;
                    break;
                case 6:
                    convertedDate = +nepDay + " "
                            + nepaliMonthHasMap().get(nepMonth - 1) + ", " + nepYear + "\nFriday";
                    getDay = 4;
                    break;
                case 7:
                    convertedDate = +nepDay + " "
                            + nepaliMonthHasMap().get(nepMonth - 1) + ", " + nepYear + "\nSaturday";
                    getDay = 4;
                    break;

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }


    private ArrayList<String> dayOfTheWeekList() {
        ArrayList<String> dayWeek = new ArrayList<>();
        dayWeek.add(0, "Sunday");
        dayWeek.add(1, "Monday");
        dayWeek.add(2, "Tuesday");
        dayWeek.add(3, "Wednesday");
        dayWeek.add(4, "Thusday");
        dayWeek.add(5, "Friday");
        dayWeek.add(6, "Saturday");
        return dayWeek;
    }

    private ArrayList<String> nepaliMonthHasMap() {
        ArrayList<String> nepaimontlist = new ArrayList<>();
        nepaimontlist.add(0, "Baishakh");
        nepaimontlist.add(1, "Jestha");
        nepaimontlist.add(2, "Asar");
        nepaimontlist.add(3, "Shrawan");
        nepaimontlist.add(4, "Bhadau");
        nepaimontlist.add(5, "Aswin");
        nepaimontlist.add(6, "Kartik");
        nepaimontlist.add(7, "Mansir");
        nepaimontlist.add(8, "Poush");
        nepaimontlist.add(9, "Magh");
        nepaimontlist.add(10, "Falgun");
        nepaimontlist.add(11, "Chaitra");
        return nepaimontlist;
    }
}
