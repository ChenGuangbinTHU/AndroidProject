package com.ihandy.a2014011315;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by bingochen on 2016/8/29.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter{


    private Vector<String> titleVector = new Vector<>();

    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
//        titleList.add("tab1");
//        titleList.add("tab2");
//        titleList.add("tab3");
//        titleList.add("tab4");
//        titleList.add("tab5");
//        titleList.add("tab6");
//        titleList.add("tab7");

        News n = new News();
        long longTime = System.currentTimeMillis();
        String time = String.valueOf(longTime);
        //System.out.println(time);
        //Log.d("adebug",time);
        n.setURL(time);
        Thread t = new Thread(n);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("fuck","funish");

        titleVector = n.getTitles();

        for(int i = 0;i < titleVector.size();i++)
        {
            Log.d("titleVector",titleVector.get(i));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return TabFragment.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return titleVector.size();
    }

    public CharSequence getPageTitle(int position) {
        return titleVector.get(position);
    }

    public Vector<String> getTitleVector() {
        return titleVector;
    }

    public void setTitleVector(Vector<String> titleVector) {
        this.titleVector = titleVector;
    }
}
