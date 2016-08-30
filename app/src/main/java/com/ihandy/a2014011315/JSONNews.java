package com.ihandy.a2014011315;

import org.json.JSONObject;

/**
 * Created by bingochen on 2016/8/31.
 */
public class JSONNews {

    private String newsCategory;
    private String newsCountry;
    private long newsFetchedTime;
    private String[] newsImgsUrl;
    private String newsLocaleCategory;
    private String newsId;
    private String newsOrigin;
    private String newsSourceName;
    private String newsSourceUrl;
    private String newsTitle;
    private long newsUpdateTime;

    public String getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }

    public String getNewsCountry() {
        return newsCountry;
    }

    public void setNewsCountry(String newsCountry) {
        this.newsCountry = newsCountry;
    }

    public long getNewsFetchedTime() {
        return newsFetchedTime;
    }

    public void setNewsFetchedTime(long newsFetchedTime) {
        this.newsFetchedTime = newsFetchedTime;
    }

    public String[] getNewsImgsUrl() {
        return newsImgsUrl;
    }

    public void setNewsImgsUrl(String[] newsImgsUrl) {
        this.newsImgsUrl = newsImgsUrl;
    }

    public String getNewsLocaleCategory() {
        return newsLocaleCategory;
    }

    public void setNewsLocaleCategory(String newsLocaleCategory) {
        this.newsLocaleCategory = newsLocaleCategory;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsOrigin() {
        return newsOrigin;
    }

    public void setNewsOrigin(String newsOrigin) {
        this.newsOrigin = newsOrigin;
    }

    public String getNewsSourceName() {
        return newsSourceName;
    }

    public void setNewsSourceName(String newsSourceName) {
        this.newsSourceName = newsSourceName;
    }

    public String getNewsSourceUrl() {
        return newsSourceUrl;
    }

    public void setNewsSourceUrl(String newsSourceUrl) {
        this.newsSourceUrl = newsSourceUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public long getNewsUpdateTime() {
        return newsUpdateTime;
    }

    public void setNewsUpdateTime(long newsUpdateTime) {
        this.newsUpdateTime = newsUpdateTime;
    }

    public String getAll()
    {
        String s = "";
        s = s + newsCategory + "--" + newsCountry + "--" + newsId + "--" + newsLocaleCategory + "--" + newsOrigin + "--" + newsOrigin + "--" + newsSourceName + "--" + newsSourceUrl + "--" + newsTitle + "--" + newsUpdateTime + "--" + newsFetchedTime + "--" + newsImgsUrl +"--";
        return s;
    }
}
