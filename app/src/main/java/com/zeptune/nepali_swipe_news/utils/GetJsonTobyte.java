package com.zeptune.nepali_swipe_news.utils;

import android.content.ContentValues;

import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class GetJsonTobyte {

    public  static byte[] getByte(DataumListModel dataumListModel){
//        ArrayList<Person> persons  = new ArrayList<>();
        Gson gson = new Gson();
        ContentValues values = new ContentValues();
        return gson.toJson(dataumListModel).getBytes();
    }

    public static DataumListModel getDatalisModel(byte[] datalistByte) throws UnsupportedEncodingException {
        String str = new String(datalistByte, "UTF-8"); // for UTF-8 encoding
        Gson gson=new Gson();
        return gson.fromJson(str, DataumListModel .class);
    }
}
