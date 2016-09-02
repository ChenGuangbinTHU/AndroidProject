package com.ihandy.a2014011315;

import android.app.Activity;
import android.content.Intent;
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



    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("fuck_intent","oncreate");
        setContentView(R.layout.web_view);
        intent = getIntent();

        WebView wb = (WebView)findViewById(R.id.webView);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        ImageButton loveButton = (ImageButton)findViewById(R.id.love);
        ImageButton shareButton = (ImageButton)findViewById(R.id.share);



        loveButton.setImageDrawable(getResources().getDrawable(R.mipmap.love));
        shareButton.setImageDrawable(getResources().getDrawable(R.mipmap.share));

        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //loveButton.setOnClickListener();



        sourceUrl = intent.getStringExtra("sourceUrl");
        sourceName = intent.getStringExtra("sourceName");
        Log.d("fuck_intent",sourceUrl);
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
