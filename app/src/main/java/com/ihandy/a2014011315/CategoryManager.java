package com.ihandy.a2014011315;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.security.PrivilegedAction;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by bingochen on 2016/9/4.
 */
public class CategoryManager extends Activity{
    private ListView listView;

    private Vector<String> watch;//保存观看分类

    private Vector<String> unwatch;//保存未观看分类

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //从数据库的category表中读取分类
        watch = Database.getWatchNewsTitle();
        unwatch = Database.getUnWatchNewsTitle();
        setContentView(R.layout.categoty_manage);
        listView = (ListView)findViewById(R.id.manage_list);
        adapter = new MyAdapter(getBaseContext());
        listView.setAdapter(adapter);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_category);//设置菜单栏
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setTitle("Categoty Manager");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置监听，根据点击不同的位置判断是观看的分类还是未观看的分类来进行移除和添加
        listView.setOnItemClickListener(new ListView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0 ||position == watch.size()+1)
                {

                }
                else if(position > 0 && position < watch.size()+1)
                {
                    String title = watch.get(position-1);
                    ContentValues values = new ContentValues();
                    values.put("watch",0);
                    Database.getInstance(getBaseContext()).update("category",values,"title=?",new String[]{title});
                    watch = Database.getWatchNewsTitle();
                    unwatch = Database.getUnWatchNewsTitle();
                }
                else if(position > watch.size()+1)
                {
                    String title = unwatch.get(position-2-watch.size());
                    ContentValues values = new ContentValues();
                    values.put("watch",1);
                    Database.getInstance(getBaseContext()).update("category",values,"title=?",new String[]{title});
                    unwatch = Database.getUnWatchNewsTitle();
                    watch = Database.getWatchNewsTitle();
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public final class ViewHolder
    {
        public ImageView img;
        public TextView title;
    }


    public class MyAdapter extends BaseAdapter
    {

        private LayoutInflater mInflater;

        private List<Map<String,Object>> data;

        MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return watch.size()+unwatch.size()+2;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if(convertView == null)
            {
                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.category_view,null);
                holder.img = (ImageView)convertView.findViewById(R.id.category_image);
                holder.title = (TextView)convertView.findViewById(R.id.category_text);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            //根据不同的位置来渲染不同的效果
            if(position == 0)
            {
                holder.title.setText("Watch");
                holder.title.setTextColor(Color.rgb(255,0,0));
                holder.img.setVisibility(View.INVISIBLE);
            }
            else if(position < watch.size()+1)
            {
                holder.title.setText(watch.get(position-1));
                holder.title.setTextColor(Color.rgb(0,0,0));
                holder.img.setImageDrawable(getResources().getDrawable(R.drawable.down));
                holder.img.setAdjustViewBounds(true);
                holder.img.setVisibility(View.VISIBLE);
            }
            else if(position == watch.size()+1)
            {
                holder.title.setText("Unwatch");
                holder.title.setTextColor(Color.rgb(255,0,0));
                holder.img.setVisibility(View.INVISIBLE);

            }
            else
            {
                holder.title.setText(unwatch.get(position-2-watch.size()));
                holder.title.setTextColor(Color.rgb(0,0,0));
                holder.img.setImageDrawable(getResources().getDrawable(R.drawable.up));
                holder.img.setAdjustViewBounds(true);
                holder.img.setVisibility(View.VISIBLE);
            }

            return convertView;
        }
    }
}
