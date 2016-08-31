package com.ihandy.a2014011315;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

/**
 * Created by bingochen on 2016/8/29.
 */
public class TabFragment extends Fragment {

    public static String args_page = "args_page";
    public static String category = "category";
    private int m_page;
    private News n;
    private static Vector<JSONNews> newsVector = new Vector<>();

    public static TabFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(args_page, page);
        args.putString(category,NewsClassify.getClassify().get(page));
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_page = getArguments().getInt("args_page");
    }

    @Nullable
    @Override
/*
    对于一个没有被载入或者想要动态载入的界面，都需要使用LayoutInflater.inflate()来载入；
            2、对于一个已经载入的界面，就可以使用Activiyt.findViewById()方法来获得其中的界面元素*/
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page,container,false);
        n = new News();
        n.setURL(getArguments().getString("category"));
        Thread thread = new Thread(n);
        thread.setPriority(9);
        thread.start();
        try {

            thread.join();
        } catch (InterruptedException e) {
            Log.d("fuck","interrupted");
            e.printStackTrace();
        }

        LinearLayout ll = (LinearLayout)view.findViewById(R.id.tab_linear);

        newsVector = n.getJsonNewsVector();
        //TextView t = (TextView)view.findViewById(R.id.textView);

        //t.setText(n.test());

        ImageByte ib = new ImageByte(getImgUrls());
        Thread t = new Thread(ib);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for(int i = 0;i < newsVector.size();i++)
        {
            JSONNews news = newsVector.get(i);
            TextView tv = new TextView(ll.getContext());
            tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,200));
            //tv.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
           // tv.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            Log.d("fuck_view",news.getNewsCategory() + ":" + news.getNewsTitle());
            //tv.setText(news.getNewsTitle());
            tv.setVisibility(View.VISIBLE);
            tv.setTextColor(Color.rgb(0,0,0));
            tv.setBackgroundColor(Color.rgb(255,255,255));


//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            tv.setText(ib.getImage().toString());
            ll.addView(tv);
        }
        return view;
    }



    public static String[] getImgUrls()
    {
        int size = newsVector.size();
        String [] urls = new String[size];
        for(int i = 0;i < size;i++)
        {
            urls[i] = newsVector.get(i).getNewsImgsUrl()[0];
        }
        return urls;
    }



}

class ImageByte implements Runnable
{

    private Vector<byte[]> imageByteVector = new Vector<>();

    private String[] urls;

    private News news;

    ImageByte(String[] urls){
        this.urls = urls;
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
            imageByteVector.add(b);
            Log.d("fuck",i + ":" + b.toString());
        }
        //Log.d("fuck",imageByte.toString());
    }
}


