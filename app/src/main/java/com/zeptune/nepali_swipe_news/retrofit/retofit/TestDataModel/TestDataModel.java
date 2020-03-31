package com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel;

public class TestDataModel {
    String userId;
    String id;
    String title;
    boolean completed;

    public TestDataModel(String userId, String id, String title, boolean completed){
        this.userId= userId;
        this.title= title;
        this.id=id;
        this.completed=completed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
