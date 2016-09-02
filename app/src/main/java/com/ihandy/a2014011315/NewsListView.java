package com.ihandy.a2014011315;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bingochen on 2016/9/2.
 */
class NewsListView
{

    private List<Map<String,Object>> mData;

    private String category;

    private Context context;

    private ListView listView;

    public ListView getListView()
    {
        return listView;
    }


    NewsListView(String category, Context context)
    {
        this.category = category;
        this.context = context;
    }

    public void init()
    {
        listView = new ListView(context);
        mData = getData();
        MyAdapter adapter = new MyAdapter(context);
        listView.setAdapter(adapter);
    }

    private List<Map<String,Object>> getData(){
        Cursor newsCursor = Database.getInstance(context).query("news",null,"category=?",new String[]{category},null,null,null,null);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        while(newsCursor.moveToNext())
        {
            Bitmap image = ImageByte.getBitmapFromByte(newsCursor.getBlob(12));
            Map<String, Object> map = new HashMap<String, Object>();
            String title = newsCursor.getString(10);
            map.put("imageView1",image);
            map.put("textView1",title);
            list.add(map);
        }
        return list;
    }

    public final class ViewHolder
    {
        public ImageView img;
        public TextView title;
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {

        Log.d("fuck_list", (String)mData.get(position).get("textView1"));
    }

    public class MyAdapter extends BaseAdapter
    {

        private LayoutInflater mInflater;

        MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return mData.size();
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

            holder.img.setImageBitmap((Bitmap)mData.get(position).get("imageView1"));
            holder.title.setText((String)mData.get(position).get("textView1"));
            return convertView;
        }
    }
}