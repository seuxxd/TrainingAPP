package com.example.seuxxd.trainingapp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity {



    private static final String TAG = "NewsActivity";


    @BindView(R.id.news_WebView)
    WebView mNewsView;
    @BindView(R.id.news_load)
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        String mURL = getIntent().getStringExtra("url");
        WebSettings mSettings = mNewsView.getSettings();
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);
        mSettings.setDisplayZoomControls(false);
        mSettings.setJavaScriptEnabled(true);
        mNewsView.loadUrl(mURL);
        mNewsView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Toast.makeText(NewsActivity.this, "请稍等", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
//                if (Build.VERSION.SDK_INT >= 21) {
//                    view.loadUrl(request.getUrl().toString());
//                    return true;
//                }
//                else
//                    return super.shouldOverrideUrlLoading(view, request);
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });
        mNewsView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i(TAG, "onProgressChanged: " + newProgress);
                mProgressBar.setProgress(newProgress);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mNewsView.canGoBack()){
            mNewsView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
