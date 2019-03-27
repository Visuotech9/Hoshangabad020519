package com.visuotech.hoshangabad.Activities.Election;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.visuotech.hoshangabad.R;

public class Act_voter_info extends AppCompatActivity {
    private WebView wv1;
    ProgressBar progressbar;
    boolean desktop=false;
//    String url="https://api.androidhive.info/webview/index.html";
    String url="http://164.100.196.163/searchengine/SearchHN.aspx";
    private float m_downX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_voter_info);

        wv1=(WebView)findViewById(R.id.webView);
        progressbar=(ProgressBar)findViewById(R.id.progressbar);
        wv1.setWebViewClient(new MyBrowser());
         progressbar.setVisibility(View.GONE);
//        initWebView();
//        wv1.loadUrl(url);
//
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setLoadWithOverviewMode(true);
        wv1.getSettings().setUseWideViewPort(true);

        wv1.getSettings().setSupportZoom(true);
        wv1.getSettings().setBuiltInZoomControls(true);
        wv1.getSettings().setDisplayZoomControls(false);

        wv1.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wv1.setScrollbarFadingEnabled(false);
        wv1.loadUrl(url);
    }

    private void initWebView() {
        wv1.setWebChromeClient(new MyWebChromeClient(this));
        wv1.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressbar.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wv1.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
                invalidateOptionsMenu();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressbar.setVisibility(View.GONE);
                invalidateOptionsMenu();
            }
        });
        wv1.clearCache(true);
        wv1.clearHistory();
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setHorizontalScrollBarEnabled(false);
        wv1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // save the x
                        m_downX = event.getX();
                    }
                    break;

                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        // set x so that it doesn't move
                        event.setLocation(m_downX, event.getY());
                    }
                    break;
                }

                return false;
            }
        });
    }

    private void back() {
        if (wv1.canGoBack()) {
            wv1.goBack();
        }
    }

    private void forward() {
        if (wv1.canGoForward()) {
            wv1.goForward();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }


    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
        }
    }


}
