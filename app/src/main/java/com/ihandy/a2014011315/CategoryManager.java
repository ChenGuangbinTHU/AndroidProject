package com.ihandy.a2014011315;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.security.PrivilegedAction;
import java.util.List;
import java.util.Map;

/**
 * Created by bingochen on 2016/9/4.
 */
public class CategoryManager extends Activity{
    private ListView listView;

    private List<String> watch;

    private List<String> unwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoty_manage);
        listView = (ListView)findViewById(R.id.category_management);

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

        MyAdapter(Context context, List<Map<String,Object>> data){
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
