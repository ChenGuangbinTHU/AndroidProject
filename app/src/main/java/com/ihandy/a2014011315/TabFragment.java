package com.ihandy.a2014011315;

import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    List<Map<String, Object>> list;





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
//        try {
//
//            thread.join();
//        } catch (InterruptedException e) {
//            Log.d("fuck","interrupted");
//            e.printStackTrace();
//        }

        LinearLayout ll = (LinearLayout)view.findViewById(R.id.tab_linear);

        newsVector = n.getJsonNewsVector();
        //TextView t = (TextView)view.findViewById(R.id.textView);

        //t.setText(n.test());

        ImageByte ib = new ImageByte(getImgUrls(),newsVector);
        Thread t = new Thread(ib);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        NewsListView nlv = new NewsListView(getActivity(),getArguments().getString("category"),ll.getContext());
        //ll.addView(nlv);

        nlv.init();

        ListView listView = nlv.getListView();

        list = nlv.getList();

        listView.setOnItemClickListener(new ListView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getActivity(),WebNews.class);
                intent.putExtra("sourceUrl",(String)list.get(position).get("sourceUrl"));
                intent.putExtra("sourceName",(String)list.get(position).get("sourceName"));
                intent.putExtra("love",(int)list.get(position).get("love"));

                intent.putExtra("newsId",(String)list.get(position).get("newsId"));
                Log.d("fuck_intent","here!");
                startActivity(intent);
            }
        });

        ll.addView(listView);
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



