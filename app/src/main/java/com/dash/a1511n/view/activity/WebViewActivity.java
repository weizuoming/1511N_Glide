package com.dash.a1511n.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dash.a1511n.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.web_view);

        String detailUrl = getIntent().getStringExtra("detailUrl");

        if (detailUrl != null) {
            webView.loadUrl(detailUrl);

            //webview一系列设置
            webView.setWebViewClient(new WebViewClient());//在当前应用打开,而不是去浏览器
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
        }


    }
}
