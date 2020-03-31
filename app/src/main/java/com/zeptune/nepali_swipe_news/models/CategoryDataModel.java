package com.zeptune.nepali_swipe_news.models;

import java.io.Serializable;

public class CategoryDataModel implements Serializable {
    private String code;
    private String name;

    public CategoryDataModel(String code, String name){
        this.name=name;
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
