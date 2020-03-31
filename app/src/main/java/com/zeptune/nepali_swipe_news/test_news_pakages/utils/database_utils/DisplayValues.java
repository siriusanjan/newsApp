package com.zeptune.nepali_swipe_news.test_news_pakages.utils.database_utils;

import java.util.ArrayList;

public class DisplayValues {

    static ArrayList<String> monthsNepali = new ArrayList<>();

    static {
        monthsNepali.add(0, "वैशाख");
        monthsNepali.add(1, "जेष्ठ");
        monthsNepali.add(2, "आषाढ");
        monthsNepali.add(3, "श्रावण");
        monthsNepali.add(4, "भाद्र");
        monthsNepali.add(5, "आश्विन");
        monthsNepali.add(6, "कार्तिक");
        monthsNepali.add(7, "मंसिर");
        monthsNepali.add(8, "पौष");
        monthsNepali.add(9, "माघ");
        monthsNepali.add(10, "फाल्गुण");
        monthsNepali.add(11, "चैत्र");
    }

    static ArrayList<String> monthsEnglish = new ArrayList<>();

    static {
        monthsEnglish.add(0, "January");
        monthsEnglish.add(1, "February");
        monthsEnglish.add(2, "March");
        monthsEnglish.add(3, "April");
        monthsEnglish.add(4, "May");
        monthsEnglish.add(5, "June");
        monthsEnglish.add(6, "July");
        monthsEnglish.add(7, "August");
        monthsEnglish.add(8, "September");
        monthsEnglish.add(9, "October");
        monthsEnglish.add(10, "November");
        monthsEnglish.add(11, "December");
    }

    static ArrayList<String> weekdaysNepali = new ArrayList<>();

    static {
        weekdaysNepali.add(0, "आइतबार");
        weekdaysNepali.add(1, "सोमबार");
        weekdaysNepali.add(2, "मंगलबार");
        weekdaysNepali.add(3, "बुधबार");
        weekdaysNepali.add(4, "विहिबार");
        weekdaysNepali.add(5, "शुक्रबार");
        weekdaysNepali.add(6, "शनिबार");
    }

    static ArrayList<String> weekdaysNep = new ArrayList<>();

    static {
        weekdaysNep.add(0, "आइत");
        weekdaysNep.add(1, "सोम");
        weekdaysNep.add(2, "मंगल");
        weekdaysNep.add(3, "बुध");
        weekdaysNep.add(4, "विहि");
        weekdaysNep.add(5, "शुक्र");
        weekdaysNep.add(6, "शनि");
    }

    static ArrayList<String> weekdaysEnglish = new ArrayList<>();

    static {
        weekdaysEnglish.add(0, "Sunday");
        weekdaysEnglish.add(1, "Monday");
        weekdaysEnglish.add(2, "Tuesday");
        weekdaysEnglish.add(3, "Wednesday");
        weekdaysEnglish.add(4, "Thursday");
        weekdaysEnglish.add(5, "Friday");
        weekdaysEnglish.add(6, "Saturday");
    }

    static ArrayList<String> weekdaysEng = new ArrayList<>();

    static {
        weekdaysEnglish.add(0, "Sun");
        weekdaysEnglish.add(1, "Mon");
        weekdaysEnglish.add(2, "Tue");
        weekdaysEnglish.add(3, "Wed");
        weekdaysEnglish.add(4, "Thu");
        weekdaysEnglish.add(5, "Fri");
        weekdaysEnglish.add(6, "Sat");
    }

    public static String getEnglishWeekday(int weekday) {
        if (weekday < 1 || weekday > weekdaysEnglish.size()) {
            return "";
        }
        return weekdaysEnglish.get(weekday - 1);
    }

    public static String getNepaliWeekday(int weekday) {
        if (weekday < 1 || weekday > weekdaysNepali.size()) {
            return "";
        }
        return weekdaysNepali.get(weekday - 1);
    }

    public static String getNepWeekday(int weekday) {
        if (weekday < 1 || weekday > weekdaysNep.size()) {
            return "";
        }
        return weekdaysNep.get(weekday - 1);
    }

    public static String getEnglishMonth(int month) {
        if (month < 1 || month > monthsEnglish.size()) {
            return "";
        }
        return monthsEnglish.get(month - 1);
    }

    public static String getNepaliMonth(int month) {
        if (month < 1 || month > monthsNepali.size()) {
            return "";
        }
        return monthsNepali.get(month - 1);
    }

    public static String addZero(int numeral) {
        return String.format(numeral < 10 ? "0%d" : "%d", numeral);
    }

    public static String toNepali(String numeral) {
        String out = numeral;
        out = out.replaceAll("0", "०");
        out = out.replaceAll("1", "१");
        out = out.replaceAll("2", "२");
        out = out.replaceAll("3", "३");
        out = out.replaceAll("4", "४");
        out = out.replaceAll("5", "५");
        out = out.replaceAll("6", "६");
        out = out.replaceAll("7", "७");
        out = out.replaceAll("8", "८");
        out = out.replaceAll("9", "९");
        return out;
    }

    public static String toEnglish(String numeral) {
        String out = numeral;
        out = out.replaceAll("०", "0");
        out = out.replaceAll("१", "1");
        out = out.replaceAll("२", "2");
        out = out.replaceAll("३", "3");
        out = out.replaceAll("४", "4");
        out = out.replaceAll("५", "5");
        out = out.replaceAll("६", "6");
        out = out.replaceAll("७", "7");
        out = out.replaceAll("८", "8");
        out = out.replaceAll("९", "9");
        return out;
    }

    public static String englishDate(int year, int month, int day, int dayOfWeek) {
        return "A.D: "
                .concat(addZero(day))
                .concat(" ")
                .concat(getEnglishMonth(month))
                .concat(" ")
                .concat(String.valueOf(year))
                .concat(", ")
                .concat(getEnglishWeekday(dayOfWeek))
                ;
    }

    public static String nepaliDate(int year, int month, int day, int dayOfWeek) {
        return "वि.सं: "
                .concat(toNepali(addZero(day)))
                .concat(" ")
                .concat(getNepaliMonth(month))
                .concat(" ")
                .concat(toNepali(String.valueOf(year)))
                .concat(", ")
                .concat(getNepaliWeekday(dayOfWeek))
                ;
    }
}
