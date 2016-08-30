package com.ihandy.a2014011315;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by bingochen on 2016/8/29.
 */
public class News implements Runnable{
    private JSONTokener jsonTokener = null;

    private URL url = null;
    private String result;
    private String base_url = "http://assignment.crazz.cn/news/en/category?timestamp=";
    private Vector titles = new Vector();
    private JSONObject jsonObject;

    public void setURL(String time)
    {
        try {
            url = new URL(base_url + time);
            Log.d("url",url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void getJsonNews()
    {
        try{
            result = "";
            //Log.d("fuck_result","???");
            URLConnection tc = url.openConnection();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(tc.getInputStream()));
            //Log.d("fuck","3");
            String inputLine;

            while((inputLine = buffer.readLine()) != null)
            {
                result += inputLine;
                //Log.d("fuck_result",inputLine);
            }

            buffer.close();

            //tc.disconnect();???


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Log.d("fuck","fuck");
        jsonTokener = new JSONTokener(result);


    }

    public Vector<String> getTitles()
    {
        return titles;
    }


    void parseJSON()
    {
        try {
            Log.d("fuck",result);

            jsonObject = new JSONObject(result);
            JSONObject jsonCategories = jsonObject.getJSONObject("data");
            JSONObject jsonTitle = jsonCategories.getJSONObject("categories");
            Iterator<String> it = jsonTitle.keys();
            while(it.hasNext())
            {
                String key = (String)it.next();
                //Log.d("fuck_title",str);
                String value = jsonTitle.getString(key);
                //Log.d("fuck_title",value);
                titles.add(value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("fuck","you");
        }

    }


    @Override
    public void run() {
        getJsonNews();
        parseJSON();
        //wait();
        Log.d("fuck","解析成功");
    }
}
