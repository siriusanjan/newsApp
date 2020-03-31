package com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel;

import android.os.Parcel;
import android.os.Parcelable;

public class RasiFalDatum implements Parcelable {
    public String content;
    public String title;
    public String main_title;


    protected RasiFalDatum(Parcel in) {
        content = in.readString();
        title = in.readString();
        main_title = in.readString();
    }

    public static final Creator<RasiFalDatum> CREATOR = new Creator<RasiFalDatum>() {
        @Override
        public RasiFalDatum createFromParcel(Parcel in) {
            return new RasiFalDatum(in);
        }

        @Override
        public RasiFalDatum[] newArray(int size) {
            return new RasiFalDatum[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMain_title() {
        return main_title;
    }

    public void setMain_title(String main_title) {
        this.main_title = main_title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(title);
        dest.writeString(main_title);
    }
}
