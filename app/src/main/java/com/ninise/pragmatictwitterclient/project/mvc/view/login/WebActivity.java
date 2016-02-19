package com.ninise.pragmatictwitterclient.project.mvc.view.login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvc.control.auth.OAuthWorker;
import com.ninise.pragmatictwitterclient.project.mvc.model.preferences.TwitterPreferences;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

public class WebActivity extends AppCompatActivity {

    public static final String TAG = WebActivity.class.getSimpleName();

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);


        WebView web = (WebView) findViewById(R.id.authWebDialog);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(getIntent().getStringExtra(Constants.AUTH_URL));
        web.setWebViewClient(new WebViewClient() {
            boolean authComplete = false;
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains(Constants.AUTH_VERIFIER) && !authComplete) {
                    authComplete = true;
                    Uri uri = Uri.parse(url);
                    TwitterPreferences.getInstance(getApplicationContext()).setOAuthVerifier(uri.getQueryParameter(Constants.AUTH_VERIFIER));


                    OAuthWorker.getInstance(getApplicationContext()).getAccess();

                } else if (url.contains("denied")) {
                    Toast.makeText(getApplicationContext(), "Sorry !, Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
