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
public class ImageByte extends Activity implements Runnable
{

    private Vector<byte[]> imageByteVector = new Vector<>();

    private String[] urls;

    private News news;

    Vector<JSONNews> newsVector;

    ImageByte(String[] urls,Vector<JSONNews> newsVector){
        this.urls = urls;
        this.newsVector = newsVector;
    }

    public Vector<byte[]> getImage()
    {
        return imageByteVector;
    }

    private byte[] getImageByte(String url1)
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



    @Override



    public void run() {
        //imageByte = getImageByte(url);

        for(int i = 0;i < urls.length;i++)
        {
            Log.d("fuck_debug",i + ":" + urls[i]);
        }

        for(int i = 0;i < urls.length;i++)
        {
            byte[] b = getImageByte(urls[i]);
            //imageByteVector.add(b);
            //Log.d("fuck",i + ":" + b.toString());
            JSONNews news = newsVector.get(i);
            news.setImageByte(b);
            news.saveToDatabase(Database.getInstance(this));
            Log.d("fuck_save",String.valueOf(news.getNewsId()));
        }
    }

    public static Bitmap getBitmapFromByte(byte[] temp){
        if(temp != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        }else{
            return null;
        }
    }

}

