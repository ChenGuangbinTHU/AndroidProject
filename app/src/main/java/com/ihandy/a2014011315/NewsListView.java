package com.ihandy.a2014011315;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bingochen on 2016/9/2.
 */
public class NewsListView extends Fragment
{

    private List<Map<String,Object>> mData;

    private String category;

    private Context context;

    private ListView listView;

    private FragmentActivity activity;

    public ListView getListView()
    {
        return listView;
    }

    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    private ListView favoriteListView;

    private List<Map<String, Object>> favoriteList = new ArrayList<Map<String, Object>>();


    public ListView getFavoriteListView(){
        return  favoriteListView;
    }

    public List<Map<String, Object>> getFavoriteList(){
        return favoriteList;
    }


    public void initFavorateListView(Context context)
    {
        favoriteListView = new ListView(context);

        Cursor c = Database.getInstance(context).query("news",null,"love=?",new String[]{Integer.toString(1)},null,null,null,null);


        while(c.moveToNext())
        {
            Bitmap image = ImageByte.getBitmapFromByte(c.getBlob(12));
            Map<String, Object> map = new HashMap<String, Object>();
            String title = c.getString(10);
            String sourceUrl = c.getString(9);
            String sourceName = c.getString(8);
            int love = c.getInt(13);
            String newsId = c.getString(6);
            map.put("imageView1",image);
            map.put("textView1",title);
            map.put("sourceUrl",sourceUrl);
            map.put("sourceName",sourceName);
            map.put("love",love);
            map.put("newsId",newsId);
            favoriteList.add(map);
        }

        MyAdapter adapter = new MyAdapter(context,favoriteList);

        favoriteListView.setAdapter(adapter);
    }



    NewsListView(FragmentActivity activity,String category, Context context)
    {
        this.category = category;
        this.context = context;
        this.activity = activity;
    }

    NewsListView(){};

    public void init()
    {
        listView = new ListView(context);
        mData = getData();
        MyAdapter adapter = new MyAdapter(context,mData);
        listView.setAdapter(adapter);

    }

    public List<Map<String, Object>> getList(){return list;}

    private List<Map<String,Object>> getData(){
        Cursor newsCursor = Database.getInstance(context).query("news",null,"category=?",new String[]{category},null,null,null,null);



        while(newsCursor.moveToNext())
        {
            Bitmap image = ImageByte.getBitmapFromByte(newsCursor.getBlob(12));
            Map<String, Object> map = new HashMap<String, Object>();
            String title = newsCursor.getString(10);
            String sourceUrl = newsCursor.getString(9);
            String sourceName = newsCursor.getString(8);
            int love = newsCursor.getInt(13);
            String newsId = newsCursor.getString(6);
            map.put("imageView1",image);
            map.put("textView1",title);
            map.put("sourceUrl",sourceUrl);
            map.put("sourceName",sourceName);
            map.put("love",love);
            map.put("newsId",newsId);
            list.add(map);
        }
        return list;
    }

    public final class ViewHolder
    {
        public ImageView img;
        public TextView title;
        public String sourceUrl;
        public String sourceName;
    }



    public class MyAdapter extends BaseAdapter
    {

        private LayoutInflater mInflater;

        private List<Map<String,Object>> data;

        MyAdapter(Context context,List<Map<String,Object>> data){
            this.mInflater = LayoutInflater.from(context);
            this.data = data;
        }


        @Override
        public int getCount() {
            return data.size();
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

                convertView = mInflater.inflate(R.layout.news,null);
                holder.img = (ImageView)convertView.findViewById(R.id.imageView1);
                holder.title = (TextView)convertView.findViewById(R.id.textView1);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.img.setImageBitmap((Bitmap)data.get(position).get("imageView1"));
            holder.title.setText((String)data.get(position).get("textView1"));
            holder.sourceUrl = (String)data.get(position).get("sourceUrl");
            holder.sourceName = (String)data.get(position).get("sourceName");
            return convertView;
        }
    }
}