package com.mydroidtechnology.embaralhado.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

public class PrivacyPolicyActivity extends NavigationControlActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_privacy_policy);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu2);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.privacy_policy);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_toolbar_home);
        }
        WebView webView = findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/privacy_policy.html");

    }

    @Override
    protected void startActivityOnBackPressed() {
        super.startMainActivity();
    }
}
