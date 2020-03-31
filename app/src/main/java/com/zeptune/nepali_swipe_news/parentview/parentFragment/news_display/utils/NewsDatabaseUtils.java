package com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils;

import android.text.TextUtils;
import android.util.Log;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.zeptune.nepali_swipe_news.models.DataumListModel;
import com.zeptune.nepali_swipe_news.models.Datum;
import com.zeptune.nepali_swipe_news.models.MetaData;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class.DatumForDatabase;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class.DatumListModelForDatabase;
import com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class.MetaDataForDatabase;
import com.zeptune.nepali_swipe_news.test_news_pakages.utils.NewsVariables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsDatabaseUtils {
    private DatumListModelForDatabase dataListModelForDatabase=new DatumListModelForDatabase();
    private DatabaseChangeListener databaseChangeListener;

    public NewsDatabaseUtils() {

    }

    public NewsDatabaseUtils(DatumListModelForDatabase notificationDataModel) {
        this.dataListModelForDatabase = notificationDataModel;

    }

    public String getFirstStringFromDatumNews(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        DatumListModelForDatabase dataumListModel = new Select().from(DatumListModelForDatabase.class).where("NewsType=?", newsOrCoronaTypesEnum.getNewsTypeCode()).executeSingle();
        return dataumListModel.getData().get(0).getTitle();
    }

    public void updateDatabase(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum, DataumListModel latestModule) {
        new Update(DatumListModelForDatabase.class)
                .set("NewsData=?", latestModule.getData(), "NewsMeta=?", latestModule.getMeta())
                .where("NewsType=?", newsOrCoronaTypesEnum.getNewsTypeCode())
                .execute();
        databaseChangeListener.onDatabaseChange();
    }

    public DatumListModelForDatabase getDataumListModel(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        return new Select().from(DatumListModelForDatabase.class).where("NewsType=?", newsOrCoronaTypesEnum.getNewsTypeCode()).executeSingle();
    }

    public DatumForDatabase getNewsDatum(NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        return new Select().from(DatumForDatabase.class).where("NewsType=?", newsOrCoronaTypesEnum.getNewsTypeCode()).executeSingle();
    }

    public void addNewsData(DataumListModel dataumListModel, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        addMetaDatas(dataumListModel, newsOrCoronaTypesEnum);
        addNewsDatums(dataumListModel, newsOrCoronaTypesEnum);
//        databaseChangeListener.onDatabaseChange();
    }


    private void addNewsDatums(DataumListModel dataumListModel, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        ArrayList<Datum> datumList = dataumListModel.getData();
            for (int i = 0; i < datumList.size(); i++) {
                Datum datum = datumList.get(i);
                DatumForDatabase datumForDatabase = new DatumForDatabase();
                datumForDatabase.setTitle(datum.getTitle());
                datumForDatabase.setDescription(datum.getDescription());
                datumForDatabase.setFullLink(datum.getFullLink());
                datumForDatabase.setImage(datum.getImage());
                datumForDatabase.setPublished(datum.getPublished());
                datumForDatabase.setSource(datum.getSource());
                datumForDatabase.save();
            }
        List<DatumForDatabase> list = new Select().from(DatumForDatabase.class).execute();
        Log.d("pppppppp", "addNewsDatums: "+list.size());


    }

    private void addMetaDatas(DataumListModel dataumListModel, NewsVariables.NewsOrCoronaTypesEnum newsOrCoronaTypesEnum) {
        if (dataListModelForDatabase.getMeta() != null) {
            MetaData metaDataFromCloud = dataumListModel.getMeta();
            new Update(DatumListModelForDatabase.class)
                    .set("current_page=?", metaDataFromCloud.getCurrent_page(),
                            "fromSource=?", metaDataFromCloud.getFrom(),
                            "last_page=?", metaDataFromCloud.getLast_page(),
                            "path=?", metaDataFromCloud.getPath(),
                            "per_page=?", metaDataFromCloud.getPer_page(),
                            "toSource=?", metaDataFromCloud.getTo(),
                            "total=?", metaDataFromCloud.getTotal()
                    )
                    .where("NewsType=?", newsOrCoronaTypesEnum.getNewsTypeCode())
                    .execute();
        } else {
            MetaDataForDatabase metaData = new MetaDataForDatabase();
            MetaData metaDataFromCloud = dataumListModel.getMeta();
            metaData.setCurrent_page(metaDataFromCloud.getCurrent_page());
            metaData.setFrom(metaDataFromCloud.getFrom());
            metaData.setLast_page(metaDataFromCloud.getLast_page());
            metaData.setPath(metaDataFromCloud.getPath());
            metaData.setPer_page(metaDataFromCloud.getPer_page());
            metaData.setTo(metaDataFromCloud.getTo());
            metaData.setTotal(metaDataFromCloud.getTotal());
            metaData.setNewsTypeId(newsOrCoronaTypesEnum.getNewsTypeCode());
            metaData.save();

        }
        MetaDataForDatabase metaDataForDatabase=new Select().from(MetaDataForDatabase.class).executeSingle();
        Log.d("pppppppp", "addNewsDatums: "+metaDataForDatabase.getPath());
    }


    public void deleteNotification(String notificationId) {
        if (!TextUtils.isEmpty(notificationId)) {
            new Delete().from(DatumForDatabase.class).where("notificationId=?", notificationId).execute();
            databaseChangeListener.onDatabaseChange();
        }
    }


    public List<DatumListModelForDatabase> getAllSingleItemSellList() {
        List<DatumListModelForDatabase> list = new Select().from(DatumListModelForDatabase.class).execute();
        Collections.reverse(list);
        return list;
    }

    public List<DatumListModelForDatabase> getAll() {
        List<DatumListModelForDatabase> list = new Select().from(DatumListModelForDatabase.class).execute();
        Collections.reverse(list);
        return list;
    }

    public void deleteAllSellItemList() {
        new Delete().from(DatumForDatabase.class).execute();
    }

    public DatumForDatabase getSpecificNotification(String notificationId) {
        return new Select().from(DatumForDatabase.class).where("notificationId=?", notificationId).executeSingle();
    }

    public void addDatabaseChangeListener(DatabaseChangeListener databaseChangeListener) {
        /**
         * Called every time an add or delete action is performed
         */
        this.databaseChangeListener = databaseChangeListener;
    }

    public interface DatabaseChangeListener {
        void onDatabaseChange();
    }
}
