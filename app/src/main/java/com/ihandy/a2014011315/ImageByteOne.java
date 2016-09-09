package com.ihandy.a2014011315;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

/**
 * Created by bingochen on 2016/9/1.
 */
public class ImageByteOne extends Activity
{
    private String url;

    JSONNews news;

    ImageByteOne(String urls,JSONNews news){
        this.url = urls;
        this.news = news;
    }


    public static byte[] getImageByte(String url1)//获得二进制形式的图片
    {
        URL url = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            url = new URL(url1);
            URLConnection tc = url.openConnection();
            InputStream input = tc.getInputStream();

            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1 ) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public void runIt() {
        byte[] b = getImageByte(url);
        news.setImageByte(b);
        news.saveToDatabase(Database.getInstance(this));

    }

    //将二进制图片转化为Bitmap
    public static Bitmap getBitmapFromByte(byte[] temp){
        if(temp != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        }else{
            return null;
        }
    }

}

