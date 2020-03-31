package com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("news_id")
    private Integer newsId;
    @SerializedName("news_title")
    private String newsTitle;
    @SerializedName("news_short_desc")
    private String newsShortDesc;
    @SerializedName("parent_id")
    private Integer parentId;
    @SerializedName("news_status")
    private String newsStatus;
    @SerializedName("comment_status")
    private String commentStatus;
    @SerializedName("published_date")
    private String publishedDate;
    @SerializedName("news_url")
    private String newsUrl;
    @SerializedName("modified_date")
    private String modifiedDate;
    @SerializedName("category_id")
    private Integer categoryId;
    @SerializedName("author_id")
    private Integer authorId;
    @SerializedName("comment_count")
    private Integer commentCount;
    @SerializedName("image_url")
    private String imageUrl;

    public Data(Integer newsId, String newsTitle, String newsShortDesc, Integer parentId, String newsStatus, String commentStatus, String publishedDate, String newsUrl, String modifiedDate, Integer categoryId, Integer authorId, Integer commentCount, String imageUrl) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsShortDesc = newsShortDesc;
        this.parentId = parentId;
        this.newsStatus = newsStatus;
        this.commentStatus = commentStatus;
        this.publishedDate = publishedDate;
        this.newsUrl = newsUrl;
        this.modifiedDate = modifiedDate;
        this.categoryId = categoryId;
        this.authorId = authorId;
        this.commentCount = commentCount;
        this.imageUrl = imageUrl;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsShortDesc() {
        return newsShortDesc;
    }

    public void setNewsShortDesc(String newsShortDesc) {
        this.newsShortDesc = newsShortDesc;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(String newsStatus) {
        this.newsStatus = newsStatus;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
