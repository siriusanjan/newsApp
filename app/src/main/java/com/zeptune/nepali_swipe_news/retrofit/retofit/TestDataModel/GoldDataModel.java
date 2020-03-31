package com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel;

import android.os.Parcel;
import android.os.Parcelable;

public class GoldDataModel  implements Parcelable {
    public String title;
    public String value;

    protected GoldDataModel(Parcel in) {
        title = in.readString();
        value = in.readString();
    }

    public static final Creator<GoldDataModel> CREATOR = new Creator<GoldDataModel>() {
        @Override
        public GoldDataModel createFromParcel(Parcel in) {
            return new GoldDataModel(in);
        }

        @Override
        public GoldDataModel[] newArray(int size) {
            return new GoldDataModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(value);
    }
}
