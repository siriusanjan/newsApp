package com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel;


import java.util.ArrayList;

public class CategoryData {
    private String id;
    private String category_name;
    private String parent_category_id;
    private ArrayList<CategoryData> childList;
    private boolean haveSublist;




    public CategoryData(String id, String categoryName, String parent_category_id, boolean haveSublist) {
        this.id = id;
        this.category_name = categoryName;
        this.parent_category_id = parent_category_id;
        this.childList = new ArrayList<>();
        this.haveSublist=haveSublist;
    }
    public boolean isHaveSublist() {
        return haveSublist;
    }

    public void setHaveSublist(boolean haveSublist) {
        this.haveSublist = haveSublist;
    }
    public ArrayList<CategoryData> getChildList() {
        return childList;
    }

    public void addChildCategory(CategoryData categoryData) {
        this.childList.add(categoryData);
    }

    public boolean isParent() {
        return parent_category_id.equals("0");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getParent_category_id() {
        return parent_category_id;
    }







}

