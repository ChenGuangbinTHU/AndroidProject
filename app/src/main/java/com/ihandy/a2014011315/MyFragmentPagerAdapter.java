package com.ihandy.a2014011315;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by bingochen on 2016/8/26.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public final int COUNT = 10;
    private String[] titles = new String[]{"Tab2221", "T2", "Tb3", "Tab4", "Tab5555555555","Tab2221", "T2", "Tb3", "Tab4", "Tab5555555555"};
    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
