package com.zeptune.nepali_swipe_news.parentview.parentFragment.news_display.utils.model_class;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Table(name = "NewsData")
public class DatumForDatabase extends Model {
    @SerializedName("id")
    @Expose
    @Column
    private String newsTypeId;
    @SerializedName("title")
    @Expose
    @Column
    private String title;
    @SerializedName("description")
    @Expose
    @Column
    private String description;
    @SerializedName("full_link")
    @Expose
    @Column
    private String fullLink;
    @SerializedName("image")
    @Expose
    @Column
    private String image;
    @SerializedName("source")
    @Expose
    @Column
    private String source;
    @SerializedName("published")
    @Expose
    @Column
    private String published;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getFullLink() {
        return fullLink;
    }
    public void setFullLink(String fullLink) {
        this.fullLink = fullLink;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getPublished() {
        return published;
    }
    public void setPublished(String published) {
        this.published = published;
    }
}
