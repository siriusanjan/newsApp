package com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "AllNewsDatabase")
public class AllNewsDatabase extends Model {
    @Column
    String Json;
    @Column
    String TypeCode;

    public String getJson() {
        return Json;
    }

    public void setJson(String json) {
        this.Json = json;
    }

    public String getTypeCode() {
        return TypeCode;
    }

    public void setTypeCode(String typeCode) {
        this.TypeCode = typeCode;
    }
}
