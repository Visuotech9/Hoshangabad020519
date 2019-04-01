package com.visuotech.hoshangabad.Activities.Election;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.visuotech.hoshangabad.Activities.Act_home;
import com.visuotech.hoshangabad.Activities.FollowUsActivity;
import com.visuotech.hoshangabad.MarshMallowPermission;
import com.visuotech.hoshangabad.R;
import com.visuotech.hoshangabad.SessionParam;

public class Act_voter_web extends AppCompatActivity {
    LinearLayout container,lay,lay1,lay2;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;

    WebView webView;
    TextView tv_facebook, tv_twitter, tv_insta;
    RelativeLayout rl_fb, rl_twit, rt_insta;
    ProgressBar progressbar;

    String link,name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_voter_web);

        //-------------------------toolbar------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));

        Intent intent=getIntent();
        link=intent.getStringExtra("LINK");
        name=intent.getStringExtra("NAME");


        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);


        webView = findViewById(R.id.wv);
        progressbar = findViewById(R.id.progressbar);


        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new Act_voter_web.CustomWebViewClient());
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setPluginsEnabled(true);
        webView.loadUrl(link);



    }
    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {
            webview.setVisibility(webview.INVISIBLE);
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            progressbar.setVisibility(View.GONE);

            view.setVisibility(webView.VISIBLE);
            super.onPageFinished(view, url);

        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(Act_voter_web.this, Act_voter_info.class);
        startActivity(i);
        finish();
        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Act_voter_web.this, Act_voter_info.class);
        startActivity(i);
        finish();
    }


}
