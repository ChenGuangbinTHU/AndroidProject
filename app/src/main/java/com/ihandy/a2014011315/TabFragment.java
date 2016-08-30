package com.ihandy.a2014011315;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bingochen on 2016/8/29.
 */
public class TabFragment extends Fragment {

    public static String args_page = "args_page";
    public static String category = "category";
    private int m_page;
    private News n;

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
        TextView t = (TextView)view.findViewById(R.id.textView);
        t.setText(n.test());
        return view;
    }
}
