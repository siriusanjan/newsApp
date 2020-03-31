package com.zeptune.nepali_swipe_news.date_converter.converter;

import android.text.TextUtils;
import android.util.Log;

import com.zeptune.nepali_swipe_news.date_converter.interface_date.NepaliDateInterface;
import com.zeptune.nepali_swipe_news.date_converter.models.NepaliDateHasmap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ConvertBsToAd {
    private int dayOfWeek = Calendar.WEDNESDAY;
    private NepaliDateInterface myNepaliDateInterface;
    NepaliDateHasmap nepaliDateHasmap = new NepaliDateHasmap();

    public ConvertBsToAd(String nepYear, String nepMonth, String nepDay, NepaliDateInterface nepaliDateInterface) {
        if (!TextUtils.isEmpty(nepYear) && !TextUtils.isEmpty(nepMonth) && !TextUtils.isEmpty(nepDay) && nepaliDateInterface != null) {
            this.myNepaliDateInterface = nepaliDateInterface;
            BsDateAfterCoonversion(nepYear, nepMonth, nepDay);
        }
    }

    public String BsDateAfterCoonversion(String nepaliYear, String nepaliMonth, String nepaliDay) {
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
        int nepYear = Integer.parseInt(nepaliYear);

        int nepMonth = Integer.parseInt(nepaliMonth);

        int nepDay = Integer.parseInt(nepaliDay);

        long totalNepDaysCount = 0;

        int engYear = 1943;
        int engMonth = 4;
        int engDay = 14;

        int endDayOfMonth = 0;

        int[] daysInMonth = new int[]{0, 31, 28, 31, 30, 31, 30, 31,
                31, 30, 31, 30, 31};
        int[] daysInMonthOfLeapYear = new int[]{0, 31, 29, 31, 30,
                31, 30, 31, 31, 30, 31, 30, 31};

        // count total getSelectedDays in-terms of year
        int startingNepYear = 2000;
        for (int i = startingNepYear; i < nepYear; i++) {
            for (int j = 1; j <= 12; j++) {
                totalNepDaysCount += nepaliMapString.get(i)[j];
            }
        }

        // count total getSelectedDays in-terms of month
        int startingNepMonth = 1;
        for (int j = startingNepMonth; j < nepMonth; j++) {
            totalNepDaysCount += nepaliMapString.get(nepYear)[j];
        }

        // count total getSelectedDays in-terms of date
        int startingNepDay = 1;
        totalNepDaysCount += nepDay - startingNepDay;
        Log.d("totaldaysdifference", "onClick: AD fragment " + totalNepDaysCount);
        while (totalNepDaysCount != 0) {
            if (isLeapYear(engYear)) {
                endDayOfMonth = daysInMonthOfLeapYear[engMonth];
            } else {
                endDayOfMonth = daysInMonth[engMonth];
            }
            engDay++;
            dayOfWeek++;
            if (engDay > endDayOfMonth) {
                engMonth++;
                engDay = 1;
                if (engMonth > 12) {
                    engYear++;
                    engMonth = 1;
                }
            }
            if (dayOfWeek > 7) {
                dayOfWeek = 1;
            }
            totalNepDaysCount--;

        }
        String thisEngMonth = englishMonthListing().get(engMonth - 1);
        if (myNepaliDateInterface != null) {
            myNepaliDateInterface.setConvertedDate(String.valueOf(engYear), thisEngMonth, String.valueOf(engMonth), String.valueOf(engDay), dayOfTheWeekList().get(dayOfWeek - 1), false);
            dayOfWeek = Calendar.WEDNESDAY;
        }
        switch (dayOfWeek) {
            case 1:

                convertedDate = +engDay + " "
                        + englishMonthListing().get(engMonth - 1) + ", " + engYear + "\nSunday";

                dayOfWeek = Calendar.WEDNESDAY;
                break;
            case 2:
                convertedDate = +engDay + " "
                        + englishMonthListing().get(engMonth - 1) + ", " + engYear + "\nMonday";

                dayOfWeek = Calendar.WEDNESDAY;
                break;
            case 3:
                convertedDate = +engDay + " "
                        + englishMonthListing().get(engMonth - 1) + ", " + engYear + "\nTuesday";

                dayOfWeek = Calendar.WEDNESDAY;
                break;
            case 4:

                convertedDate = +engDay + " "
                        + englishMonthListing().get(engMonth - 1) + ", " + engYear + "\nWednesday";

                dayOfWeek = Calendar.WEDNESDAY;
                break;
            case 5:

                convertedDate = +engDay + " "
                        + englishMonthListing().get(engMonth - 1) + ", " + engYear + "\nThursday";

                dayOfWeek = Calendar.WEDNESDAY;
                break;
            case 6:

                convertedDate = +engDay + " "
                        + englishMonthListing().get(engMonth - 1) + ", " + engYear + "\nFriday";

                dayOfWeek = Calendar.WEDNESDAY;
                break;
            case 7:
                convertedDate = +engDay + " "
                        + englishMonthListing().get(engMonth) + ", " + engYear + "\nSaturday";
                dayOfWeek = Calendar.WEDNESDAY;
                break;

        }
        return convertedDate;
    }


    private boolean isLeapYear(int year) {
        // TODO Auto-generated method stub
        if (year % 100 == 0) {
            return year % 400 == 0;
        } else {
            return year % 4 == 0;
        }
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

    private ArrayList<String> englishMonthListing() {
        ArrayList<String> nepaimontlist = new ArrayList<>();
        nepaimontlist.add(0, "Januray");
        nepaimontlist.add(1, "February");
        nepaimontlist.add(2, "March");
        nepaimontlist.add(3, "April");
        nepaimontlist.add(4, "May");
        nepaimontlist.add(5, "June");
        nepaimontlist.add(6, "July");
        nepaimontlist.add(7, "August");
        nepaimontlist.add(8, "September");
        nepaimontlist.add(9, "October");
        nepaimontlist.add(10, "November");
        nepaimontlist.add(11, "December");
        return nepaimontlist;
    }
}
