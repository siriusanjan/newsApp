package com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.corona_data_model.currencyDataModel;

import android.os.Parcel;
import android.os.Parcelable;

public  class CurrencyDatum implements Parcelable {
    private String currency;
    private String value;
    private String buy;
    private String sell;


    protected CurrencyDatum(Parcel in) {
        currency = in.readString();
        value = in.readString();
        buy = in.readString();
        sell = in.readString();
    }
//
//    public static final Creator<CurrencyDatum> CREATOR = new Creator<CurrencyDatum>() {
//        @Override
//        public CurrencyDatum createFromParcel(Parcel in) {
//            return new CurrencyDatum(in);
//        }
//
//        @Override
//        public CurrencyDatum[] newArray(int size) {
//            return new CurrencyDatum[size];
//        }
//    };

    public static final Creator<CurrencyDatum> CREATOR = new Creator<CurrencyDatum>() {
        @Override
        public CurrencyDatum createFromParcel(Parcel in) {
            return new CurrencyDatum(in);
        }

        @Override
        public CurrencyDatum[] newArray(int size) {
            return new CurrencyDatum[size];
        }
    };

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public static Creator<CurrencyDatum> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currency);
        dest.writeString(value);
        dest.writeString(buy);
        dest.writeString(sell);
    }
}
