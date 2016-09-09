package com.ihandy.a2014011315;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * Created by bingochen on 2016/9/2.
 */
public class WebNews extends Activity{

    private String sourceUrl;

    private String sourceName;

    private Intent intent;

    private int love;

    private int loveNow;

    private String newsId;

    ImageButton loveButton;
    ImageButton shareButton;

    SQLiteDatabase db = Database.getInstance(getBaseContext());

    @Override
    protected void onResume() {
        super.onResume();
        Cursor c = db.query("news",null,"newsId=?",new String[]{newsId},null,null,null,null);

        if(c.moveToFirst()){
            loveNow = c.getInt(13);
        }


        if(loveNow == 0)
        {
            loveButton.setImageDrawable(getResources().getDrawable(R.mipmap.love));
        }

        else if(loveNow == 1)
        {
            loveButton.setImageDrawable(getResources().getDrawable(R.mipmap.love_yes));
        }
    }

    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.web_view);
        intent = getIntent();
        sourceUrl = intent.getStringExtra("sourceUrl");
        sourceName = intent.getStringExtra("sourceName");
        love = intent.getIntExtra("love",-1);
        newsId = intent.getStringExtra("newsId");
        WebView wb = (WebView)findViewById(R.id.webView);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);


        loveButton = (ImageButton)findViewById(R.id.love);
        shareButton = (ImageButton)findViewById(R.id.share);

        //根据是否为收藏来判断loveButton的图片
        if(love == 0)
        {
            loveButton.setImageDrawable(getResources().getDrawable(R.mipmap.love));
            loveNow = 0;
        }

        else if(love == 1)
        {
            loveButton.setImageDrawable(getResources().getDrawable(R.mipmap.love_yes));
            loveNow = 1;
        }

        shareButton.setImageDrawable(getResources().getDrawable(R.mipmap.share));

        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置收藏监听
        loveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loveNow == 0)
                {
                    ContentValues values = new ContentValues();
                    loveNow = 1;
                    values.put("love",1);
                    loveButton.setImageDrawable(getResources().getDrawable(R.mipmap.love_yes));
                    Database.getInstance(v.getContext()).update("news",values,"newsId=?",new String[]{newsId});
                }
                else if(loveNow == 1)
                {
                    ContentValues values = new ContentValues();
                    loveNow = 0;
                    values.put("love",0);
                    loveButton.setImageDrawable(getResources().getDrawable(R.mipmap.love));
                    Database.getInstance(v.getContext()).update("news",values,"newsId=?",new String[]{newsId});
                }
            }
        });

        //设置分享监听
        shareButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,sourceUrl);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent,getTitle()));
            }
        });


        wb.loadUrl(sourceUrl);

        wb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
