package com.zeptune.nepali_swipe_news.test_news_pakages.loading;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;

import java.text.ParseException;

public class LoadedTimeUtils {
  static   String npDate;

    public static boolean canLoadData(Context context) throws ParseException {
        String storedTie = LastLoadedStorage.getInstance(context).getLastLoadTimeStamp();
        if (TextUtils.isEmpty(storedTie)) {
            LastLoadedStorage.getInstance(context).setLastLoadTimeStamp(String.valueOf(System.currentTimeMillis()));
            return true;
        }
        long unixTime = Long.parseLong(storedTie);//set your start time
        long endDate = System.currentTimeMillis();
//        long unixTime = System.currentTimeMillis();
        if (String.valueOf(unixTime).length() == 10) {
            unixTime *= 1000;
        }
        if (unixTime < System.currentTimeMillis()) {
            long a = DateUtils.MINUTE_IN_MILLIS * 2;
            long b = System.currentTimeMillis() - unixTime;
            if (b > a) {
                LastLoadedStorage.getInstance(context).setLastLoadTimeStamp(String.valueOf(System.currentTimeMillis()));
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    public static String getNepaliDateFromMill(Context mContext, String timeMill) {
//        long dv = Long.valueOf(timeMill)*1000;// its need to be in milisecond
//        Date df = new java.util.Date(dv);
//        String vv = new SimpleDateFormat("MM dd, yyyy hh:mma",Locale.ENGLISH).format(df);
//        Calendar cl = Calendar.getInstance();
//        TimeZone tz = cl.getTimeZone();
//
//        /* debug: is it local time? */
//        Log.d("Time zone: ", tz.getDisplayName());
//
//        /* date formatter in local timezone */
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        sdf.setTimeZone(tz);
//
//        /* print your timestamp and double check it's the date you expect */
//        String localTime = sdf.format(new Date(Long.parseLong(timeMill )* 1000L)); // I assume your timestamp is in seconds and you're converting to milliseconds?
//        Log.d("Time: ", localTime);
//        long lon=cl.getTimeInMillis();
//        cl.setTimeInMillis(lon);  //here your time in miliseconds
//        String date = "" + cl.get(Calendar.DAY_OF_MONTH) + ":" + cl.get(Calendar.MONTH) + ":" + cl.get(Calendar.YEAR);
//        String year = String.valueOf(cl.get(Calendar.YEAR));
//        String month = String.valueOf(cl.get(Calendar.MONTH));
//        String day = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));
//
//        Log.d("datestatus", "getNepaliDateFromMill: "+localTime);
//        ConvertAdToBs convertAdToBs = new ConvertAdToBs(year, month, day, new NepaliDateInterface() {
//            @Override
//            public void setConvertedDate(String nepYear, String nepMonth, String nepNumberOfMonth, String nepDay, String dayOfWeek, boolean isNepali) {
//                npDate = nepDay.concat(" ").concat(nepMonth).concat(" ").concat(nepYear).concat(" ").concat(dayOfWeek);
//            }
//        });
        return "";
    }
}
