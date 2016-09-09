package com.ihandy.a2014011315;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
import java.util.Vector;

/**
 * Created by bingochen on 2016/8/30.
 */
public class News implements Runnable{

    private final String base_url = "http://assignment.crazz.cn/news/query?locale=en&category=";


    private URL url;

    private String result = "";

    private String category;

    private Vector<JSONNews> jsonNewsVector = new Vector();

    private String test = "";

    Context context;

    News(Context activity)
    {
        this.context = activity;
    }



    public Vector<JSONNews> getJsonNewsVector()
    {
        return jsonNewsVector;
    }

    public void setURL(String category)
    {
        this.category =category;
        try {
            url = new URL(base_url + category);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setCompleteUrl(String url1,String category)
    {
        this.category = category;
        try {
            url = new URL(url1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void getJsonNews()//获取JSON格式的新闻
    {
        try{
            result = "";
            URLConnection tc = url.openConnection();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(tc.getInputStream()));
            String inputLine;

            while((inputLine = buffer.readLine()) != null)
            {
                result += inputLine;
            }



            buffer.close();



        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void parseJSONNews()//解析JSON格式的新闻并且保存
    {
        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONObject jsonData = jsonObject.getJSONObject("data");

            JSONArray jsonArray = jsonData.getJSONArray("news");




            for(int i = 0;i < jsonArray.length();i++)
            {
                JSONNews jsonNews = new JSONNews();

                JSONObject news =  jsonArray.getJSONObject(i);



                jsonNews.setNewsId(news.getString("news_id"));
                Cursor c = Database.getInstance(context).query("news",null,"newsId=?",new String[]{news.getString("news_id")},null,null,null,null);
                if(c.moveToFirst() == true)
                {
                    continue;
                }

                jsonNews.setNewsCategory(category);
                jsonNews.setNewsCountry(news.getString("country"));
                jsonNews.setNewsFetchedTime(news.getLong("fetched_time"));


                JSONArray jsonImg = news.getJSONArray("imgs");
                String []newImgsUrl = new String[10];
                for(int j = 0;j < jsonImg.length();j++)
                {
                    JSONObject img = (JSONObject) jsonImg.get(j);
                    newImgsUrl[j] = img.getString("url");
                }
                jsonNews.setNewsImgsUrl(newImgsUrl);


                jsonNews.setNewsLocaleCategory(news.getString("locale_category"));
                jsonNews.setNewsOrigin(news.getString("origin"));

                if(news.isNull("source"))
                {
                    jsonNews.setNewsSourceName("");
                    jsonNews.setNewsSourceUrl("");

                }
                else
                {
                    JSONObject jsonSource = news.getJSONObject("source");
                    jsonNews.setNewsSourceName(jsonSource.getString("name"));
                    jsonNews.setNewsSourceUrl(jsonSource.getString("url"));
                }


                jsonNews.setNewsTitle(news.getString("title"));


/*change
                jsonNewsVector.add(jsonNews);
                */
                jsonNews.saveToDatabase(Database.getInstance(context));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i = 0;i < jsonNewsVector.size();i++)
        {
            //test += jsonNewsVector.size() + '\n';
            test += jsonNewsVector.get(i).getAll();
            test += '\n';
        }

    }

    public String test()
    {
        return test;
    }



    @Override
    public void run() {
        getJsonNews();
        parseJSONNews();


    }
}
