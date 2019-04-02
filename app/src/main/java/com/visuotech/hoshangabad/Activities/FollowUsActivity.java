package com.visuotech.hoshangabad.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.visuotech.hoshangabad.Activities.Election.Act_election;
import com.visuotech.hoshangabad.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FollowUsActivity extends AppCompatActivity {
    Context context;
    Activity activity;
    WebView webView;
    TextView tv_facebook, tv_twitter, tv_insta;
    RelativeLayout rl_fb, rl_twit, rt_insta;
    ProgressBar progressbar;
    AdView adView;
    View view1,view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followus_content_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Follow Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        webView = findViewById(R.id.wv);
        progressbar = findViewById(R.id.progressbar);
        tv_facebook = findViewById(R.id.tv_facebook);
        tv_twitter = findViewById(R.id.tv_twit);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);


        rl_twit = findViewById(R.id.rl_twit);
        rl_fb = findViewById(R.id.rl_fb);


        rl_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rl_fb.setBackgroundColor(getResources().getColor(R.color.sociald));
                rl_twit.setBackgroundColor(getResources().getColor(R.color.sociall));
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                webView.setWebChromeClient(new WebChromeClient());
                webView.setWebViewClient(new CustomWebViewClient());
                webView.getSettings().setAppCacheEnabled(true);
                webView.getSettings().setJavaScriptEnabled(true);
                //webView.getSettings().setPluginsEnabled(true);
                webView.loadUrl("https://m.facebook.com/collectorhoshangabad/");

            }
        });
        rl_twit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_twit.setBackgroundColor(getResources().getColor(R.color.sociald));
                rl_fb.setBackgroundColor(getResources().getColor(R.color.sociall));
                view2.setVisibility(View.VISIBLE);
                view1.setVisibility(View.GONE);
                webView.setWebChromeClient(new WebChromeClient());
                webView.setWebViewClient(new CustomWebViewClient());
                webView.getSettings().setAppCacheEnabled(true);
                webView.getSettings().setJavaScriptEnabled(true);
                //webView.getSettings().setPluginsEnabled(true);
                webView.loadUrl("https://twitter.com/dmhoshangabad");

            }
        });


        init();
    }


    public void init() {
        rl_fb.setBackgroundColor(getResources().getColor(R.color.sociald));
        rl_twit.setBackgroundColor(getResources().getColor(R.color.sociall));
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new CustomWebViewClient());
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setPluginsEnabled(true);
        webView.loadUrl("https://m.facebook.com/collectorhoshangabad/");


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
        Intent i = new Intent(FollowUsActivity.this, Act_home.class);
        startActivity(i);
        finish();
        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(FollowUsActivity.this, Act_home.class);
        startActivity(i);
        finish();
    }


}
