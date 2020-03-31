package com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;

@Table(name = "NewsDataListModel")
public class DatumListModelForDatabase extends Model {
    @Column(name = "NewsData")
    ArrayList<DatumForDatabase> data;
    @Column(name = "NewsMeta")
    public MetaDataForDatabase meta;
    @Column(name = "NewsType")
    public String newsType;

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public ArrayList<DatumForDatabase> getData() {
        return data;
    }

    public void setData(ArrayList<DatumForDatabase> data) {
        this.data = data;
    }

    public MetaDataForDatabase getMeta() {
        return meta;
    }

    public void setMeta(MetaDataForDatabase meta) {
        this.meta = meta;
    }
}