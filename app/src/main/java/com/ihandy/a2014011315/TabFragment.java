package com.ihandy.a2014011315;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private ListView newsListView;




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

        ImageByte ib = new ImageByte(getImgUrls(),newsVector);
        Thread t = new Thread(ib);
        t.start();




        //Cursor newsCursor = Database.getInstance(ll.getContext()).query("news",null,"category=?",new String[]{getArguments().getString("category")},null,null,null,null);
        /*
        for(int i = 0;i < newsVector.size();i++)
        {
            JSONNews news = newsVector.get(i);
            TextView tv = new TextView(ll.getContext());
            tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,200));
            //tv.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
           // tv.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            Log.d("fuck_view",news.getNewsCategory() + ":" + news.getNewsTitle());
            tv.setVisibility(View.VISIBLE);
            tv.setTextColor(Color.rgb(0,0,0));
            tv.setBackgroundColor(Color.rgb(255,255,255));
            tv.setText();
            ll.addView(tv);
        }
        */
/*
        while(newsCursor.moveToNext())
        {
//            TextView tv = new TextView(ll.getContext());
//            tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,200));
//            tv.setVisibility(View.VISIBLE);
//            tv.setTextColor(Color.rgb(0,0,0));
//            tv.setBackgroundColor(Color.rgb(255,255,255));
//            tv.setText(newsCursor.getString(10));
//            //newsCursor.get
//            ll.addView(tv);
//            Bitmap b = ImageByte.getBitmapFromByte(newsCursor.getBlob(12));
//            ImageView iv = new ImageView(ll.getContext());
//            iv.setImageBitmap(b);
//            ll.addView(iv);

            View newsView = inflater.inflate(R.layout.news,container,false);
            TextView newsTextView = (TextView)newsView.findViewById(R.id.textView1);
            ImageView newsImageView = (ImageView)newsView.findViewById(R.id.imageView1);
            newsTextView.setText(newsCursor.getString(10));
           // newsTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,200));
            newsTextView.setVisibility(View.VISIBLE);
            newsTextView.setTextColor(Color.rgb(0,0,0));
            newsTextView.setBackgroundColor(Color.rgb(255,255,255));
            newsImageView.setImageBitmap(ImageByte.getBitmapFromByte(newsCursor.getBlob(12)));
            ll.addView(newsView);
        }
*/
        NewsListView nlv = new NewsListView(getArguments().getString("category"),ll.getContext());
        //ll.addView(nlv);
        nlv.init();
        ll.addView(nlv.getListView());
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








