package com.ihandy.a2014011315;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Looper;
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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by bingochen on 2016/9/2.
 */
public class NewsListView extends Fragment
{

    private List<Map<String,Object>> mData = new ArrayList<>();

    private MyAdapter tabadapter;

    private String category;

    private Context context;

    private PullToRefreshListView listView;

    private FragmentActivity activity;

    public PullToRefreshListView getListView()
    {
        return listView;
    }

    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    private ListView favoriteListView;

    private List<Map<String, Object>> favoriteList = new ArrayList<Map<String, Object>>();

    private  int showNum = 24;

    public ListView getFavoriteListView(){
        return  favoriteListView;
    }

    public List<Map<String, Object>> getFavoriteList(){
        return favoriteList;
    }


    public void initFavorateListView(Context context)//初始化FavoriteNews.java中的上拉下拉列表
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
            //long updateTime = Long.parseLong(c.getString(11));
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

    public void init()//初始化ViewPager中的上拉下拉列表
    {
        listView = new PullToRefreshListView(context);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            //下拉时获取最新新闻
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask().execute();
            }
            //上拉时获取newsID最小的新闻之前的新闻
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask1().execute();

            }
        });



        mData = getData();
        tabadapter = new MyAdapter(context,mData);
        listView.setAdapter(tabadapter);

    }

    public List<Map<String, Object>> getList(){return list;}

    //从数据库读取该分类下所有新闻
    private List<Map<String,Object>> getData(){
        mData.clear();
        Cursor newsCursor = Database.getInstance(context).query("news",null,"category=?",new String[]{category},null,null,null,null);

        int count = 0;

        while(newsCursor.moveToNext())
        {
            Bitmap image = ImageByte.getBitmapFromByte(newsCursor.getBlob(12));
            Map<String, Object> map = new HashMap<String, Object>();
            String title = newsCursor.getString(10);
            String sourceUrl = newsCursor.getString(9);
            String sourceName = newsCursor.getString(8);
            int love = newsCursor.getInt(13);
            String newsId = newsCursor.getString(6);
            //long updateTime = Long.parseLong(newsCursor.getString(11));
            map.put("imageView1",image);
            map.put("textView1",title);
            map.put("sourceUrl",sourceUrl);
            map.put("sourceName",sourceName);
            map.put("love",love);
            map.put("newsId",newsId);
            //map.put("updateTime",updateTime);
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
                //holder.sourceName = (TextView)convertView.findViewById(R.id.textView_name);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            //按照NewsId进行排序
            Collections.sort(data, new Comparator<Map<String,Object>>() {
                public int compare(Map<String,Object> arg0, Map<String,Object> arg1) {
                    String a = (String)arg0.get("newsId");
                    String b = (String)arg1.get("newsId");
                    return b.compareTo(a);

                }
            });
            String text = (String)data.get(position).get("textView1");
            if(((String)data.get(position).get("sourceName")).equals("") == false)
                text =  text + '\n'+'\n'+(String)data.get(position).get("sourceName");

            holder.img.setImageBitmap((Bitmap)data.get(position).get("imageView1"));
            holder.title.setText(text);
            holder.sourceUrl = (String)data.get(position).get("sourceUrl");


            return convertView;
        }
    }

    //异步获取，用于上拉刷新
    private class GetDataTask extends AsyncTask<Void,Void,Vector<JSONNews>>{


        @Override
        protected Vector<JSONNews> doInBackground(Void... params) {
            News n = new News(getActivity());
            n.setURL(category);
            Thread t = new Thread(n);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return n.getJsonNewsVector();
        }

        @Override
        protected void onPostExecute(Vector<JSONNews> newsVector) {
            super.onPostExecute(newsVector);


            SQLiteDatabase db = Database.getInstance(getActivity());

            for(int i = 0;i < newsVector.size();i++)
            {
                JSONNews news = newsVector.get(i);
                news.saveToDatabase(db);
            }
            mData = getData();

            tabadapter.notifyDataSetChanged();
            listView.onRefreshComplete();
            Toast.makeText(activity,"Refresh Success",Toast.LENGTH_SHORT).show();
        }
    }
    //异步获取，用于下拉刷新

    private class GetDataTask1 extends AsyncTask<Void,Void,Vector<JSONNews>>{


        @Override
        protected Vector<JSONNews> doInBackground(Void... params) {
            News n = new News(activity);

            String url = "http://assignment.crazz.cn/news/query?locale=en&category="+category+"&max_news_id=" + mData.get(mData.size()-1).get("newsId");



            n.setCompleteUrl(url,category);

            Thread t = new Thread(n);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            return n.getJsonNewsVector();
        }

        @Override
        protected void onPostExecute(Vector<JSONNews> newsVector) {
            super.onPostExecute(newsVector);
            SQLiteDatabase db = Database.getInstance(getActivity());
            showNum += 10;
            for(int i = 0;i < newsVector.size();i++)
            {

                JSONNews news = newsVector.get(i);


                news.saveToDatabase(db);
            }

            int ori_size = mData.size();

            mData = getData();

            int new_size = mData.size();



            tabadapter.notifyDataSetChanged();
            listView.onRefreshComplete();
        }
    }

}