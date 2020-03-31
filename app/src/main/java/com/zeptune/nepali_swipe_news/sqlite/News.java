package com.zeptune.nepali_swipe_news.sqlite;

public class News {
    int id;
    byte[] newsDatumByte;



    public News(){

    }
    public News(int id,byte[] newsDatumByte){
        this.id=id;
      this.newsDatumByte=newsDatumByte;
    }
    public byte[] getNewsDatumByte() {
        return newsDatumByte;
    }

    public void setNewsDatumByte(byte[] newsDatumByte) {
        this.newsDatumByte = newsDatumByte;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
