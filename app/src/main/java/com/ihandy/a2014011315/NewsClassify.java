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
public class NewsClassify implements Runnable{
    private URL url = null;
    private String result;
    private String base_url = "http://assignment.crazz.cn/news/en/category?timestamp=";
    private Vector titles = new Vector();
    private static Vector classify = new Vector();
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

    public void getJsonNewsClassify()
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

            //tc.disconnect();???


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Log.d("fuck","fuck");
        //jsonTokener = new JSONTokener(result);


    }

    public Vector<String> getTitles()
    {
        return titles;
    }

    public static Vector<String> getClassify()
    {
        return classify;
    }


    void parseJSONClassify()
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
                classify.add(key);
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
        getJsonNewsClassify();
        parseJSONClassify();
        for(int i = 0;i < classify.size();i++)
        {
            Log.d("fuck_classify",classify.get(i) + ":" + titles.get(i));
        }
        Log.d("fuck","解析成功");
    }
}
