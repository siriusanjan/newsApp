package com.zeptune.nepali_swipe_news.functions;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GesonConverter {


    public static String ModelToJson(Class myObj) {
        Gson gson = new Gson();
        return gson.toJson(myObj);
    }

    public static Class jsonModleClass(String json, Class jsonClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, (Type) jsonClass);
    }
}
