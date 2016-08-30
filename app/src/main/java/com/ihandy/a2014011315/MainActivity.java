package com.ihandy.a2014011315;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private  ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        viewPager = (ViewPager)findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        /*
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        viewPager.setAdapter(adapter);

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //1.MODE_SCROLLABLE模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //2.MODE_FIXED模式
        //tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        */
    }
}
