package com.ihandy.a2014011315;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Vector;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TabLayout tabLayout;
    private  ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteDatabase db = Database.getInstance(this);//初始化数据库

        NewsClassify nc = new NewsClassify();
        long longTime = System.currentTimeMillis();
        String time = String.valueOf(longTime);
        nc.setURL(time);
        Thread t = new Thread(nc);//获取新闻分类的线程
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        Vector<String> watch = Database.getWatchNewsClassify();


        Thread[] threads = new Thread[20];
        //获取新闻的线程，有多少个分类就同时开启多少个线程来进行获取
        for(int i = 0;i < watch.size();i++)
        {
            News n = new News(getBaseContext());
            n.setURL(watch.get(i));
            threads[i] = new Thread(n);
            threads[i].start();
        }

        for(int i = 0;i < watch.size();i++)
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        setContentView(R.layout.activity_text);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        //getSupportActionBar().hide();

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        viewPager = (ViewPager)findViewById(R.id.view_pager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //设置监听，当从category manager设置完后返回时进行更新
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.favorite) {
            Intent intent = new Intent(getBaseContext(),FavoriteNews.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.category_management) {
            Intent intent = new Intent(getBaseContext(),CategoryManager.class);
            startActivity(intent);

        } else if (id == R.id.about_me) {
            Intent intent= new Intent(getBaseContext(),AboutMe.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPagerAdapter.notifyDataSetChanged();
    }
}
