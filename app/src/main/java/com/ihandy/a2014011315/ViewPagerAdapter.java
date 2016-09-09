package com.ihandy.a2014011315;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import java.util.Vector;

/**
 * Created by bingochen on 2016/8/29.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {


    public ViewPagerAdapter(FragmentManager fm){
        super(fm);



        for(int i = 0;i < Database.getWatchNewsTitle().size();i++)
        {
            Log.d("titleVector",Database.getWatchNewsTitle().get(i));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return TabFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return Database.getWatchNewsTitle().size();
    }

    public CharSequence getPageTitle(int position) {
        return Database.getWatchNewsTitle().get(position);
    }

    public Vector<String> getTitleVector() {
        return Database.getWatchNewsTitle();
    }

    @Override
    public long getItemId(int position) {
        int hashCode = Database.getWatchNewsTitle().get(position).hashCode();
        return hashCode;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
