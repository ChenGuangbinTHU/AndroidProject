package com.ihandy.a2014011315;

import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private  ViewPager viewPager;
    private SlidingMenu mMenu ;
    private Button menuButton;

    public void toggleMenu(View view)
    {
        mMenu.toggle();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteDatabase db = Database.getInstance(this);

        getSupportActionBar().hide();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.activity_text);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        viewPager = (ViewPager)findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mMenu = (SlidingMenu) findViewById(R.id.id_menu);
        menuButton = (Button)findViewById(R.id.side);
        menuButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                toggleMenu(v);
                Log.d("fuck_side","click");
            }
        });

    }
}
