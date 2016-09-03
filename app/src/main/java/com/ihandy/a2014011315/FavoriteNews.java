package com.ihandy.a2014011315;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

/**
 * Created by bingochen on 2016/9/4.
 */
public class FavoriteNews extends Activity{

    List<Map<String, Object>> list;
/*    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page,container,false);

        LinearLayout ll = (LinearLayout)view.findViewById(R.id.tab_linear);



        NewsListView nlv = new NewsListView();

        nlv.initFavorateListView(getBaseContext());

        ListView listView = nlv.getFavoriteListView();

        list = nlv.getFavoriteList();

        listView.setOnItemClickListener(new ListView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getBaseContext(),WebNews.class);
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
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_page);
        LinearLayout ll = (LinearLayout)findViewById(R.id.tab_linear);

        Toolbar toolbar = new Toolbar(getBaseContext());

        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll.addView(toolbar);

        NewsListView nlv = new NewsListView();

        nlv.initFavorateListView(getBaseContext());

        ListView listView = nlv.getFavoriteListView();

        list = nlv.getFavoriteList();

        listView.setOnItemClickListener(new ListView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getBaseContext(),WebNews.class);
                intent.putExtra("sourceUrl",(String)list.get(position).get("sourceUrl"));
                intent.putExtra("sourceName",(String)list.get(position).get("sourceName"));
                intent.putExtra("love",(int)list.get(position).get("love"));

                intent.putExtra("newsId",(String)list.get(position).get("newsId"));
                Log.d("fuck_intent","here!");
                startActivity(intent);
            }
        });

        ll.addView(listView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.fragment_page);
        LinearLayout ll = (LinearLayout)findViewById(R.id.tab_linear);

        Toolbar toolbar = new Toolbar(getBaseContext());

        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll.addView(toolbar);

        NewsListView nlv = new NewsListView();

        nlv.initFavorateListView(getBaseContext());

        ListView listView = nlv.getFavoriteListView();

        list = nlv.getFavoriteList();

        listView.setOnItemClickListener(new ListView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getBaseContext(),WebNews.class);
                intent.putExtra("sourceUrl",(String)list.get(position).get("sourceUrl"));
                intent.putExtra("sourceName",(String)list.get(position).get("sourceName"));
                intent.putExtra("love",(int)list.get(position).get("love"));

                intent.putExtra("newsId",(String)list.get(position).get("newsId"));
                Log.d("fuck_intent","here!");
                startActivity(intent);
            }
        });

        ll.addView(listView);
    }
}
