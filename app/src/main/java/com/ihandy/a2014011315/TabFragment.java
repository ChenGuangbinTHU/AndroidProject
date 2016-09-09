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
import android.widget.ProgressBar;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

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

    Vector<String> classify;



    public static TabFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(args_page, page);
        args.putString(category,Database.getWatchNewsClassify().get(page));
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page,container,false);

        LinearLayout ll = (LinearLayout)view.findViewById(R.id.tab_linear);

        NewsListView nlv = new NewsListView(getActivity(),getArguments().getString("category"),ll.getContext());
        //ll.addView(nlv);

        nlv.init();//初始化上拉下拉刷新列表

        PullToRefreshListView listView = nlv.getListView();

        list = nlv.getList();

        listView.setOnItemClickListener(new ListView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                position = position-1;
                Intent intent = new Intent(getActivity(),WebNews.class);
                intent.putExtra("sourceUrl",(String)list.get(position).get("sourceUrl"));
                intent.putExtra("sourceName",(String)list.get(position).get("sourceName"));
                intent.putExtra("love",(int)list.get(position).get("love"));

                intent.putExtra("newsId",(String)list.get(position).get("newsId"));
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



