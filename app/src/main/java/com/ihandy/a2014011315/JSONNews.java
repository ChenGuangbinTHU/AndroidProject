package com.ihandy.a2014011315;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    private byte[] imageByte;
    private String newsUpdateTime;

    public void setImageByte(byte[] imageByte){this.imageByte = imageByte;}

    public byte[] getImageByte(){return  imageByte;}

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

    public String getNewsUpdateTime() {
        return newsUpdateTime;
    }

    public void setNewsUpdateTime(String newsUpdateTime) {
        this.newsUpdateTime = newsUpdateTime;
    }

    public String getAll()
    {
        String s = "";
        s = s + newsCategory + "--" + newsCountry + "--" + newsId + "--" + newsLocaleCategory + "--" + newsOrigin + "--" + newsOrigin + "--" + newsSourceName + "--" + newsSourceUrl + "--" + newsTitle + "--" + newsUpdateTime + "--" + newsFetchedTime + "--" + newsImgsUrl +"--";
        return s;
    }

    public void saveToDatabase(SQLiteDatabase db)//将新闻保存到数据库
    {
        ContentValues cv = new ContentValues();
        cv.put("category",newsCategory);
        cv.put("country",newsCountry);
        cv.put("fetchedTime",String.valueOf(newsFetchedTime));
        cv.put("newsId",newsId);
        cv.put("localeCategory",newsLocaleCategory);
        cv.put("origin",newsOrigin);
        cv.put("sourceName",newsSourceName);
        cv.put("sourceUrl",newsSourceUrl);
        cv.put("title",newsTitle);
        cv.put("updateTime",String.valueOf(newsUpdateTime));
        cv.put("imgsUrl",newsImgsUrl[0]);
        cv.put("love",0);
        byte[] imageByte = ImageByteOne.getImageByte(newsImgsUrl[0]);
        cv.put("imageByte",imageByte);

        Cursor c = db.query("news",null,"newsId=?",new String[]{newsId},null,null,null,null);

        if(c.moveToFirst() == false)
        {
            db.insert("news",null,cv);

        }



    }
}
