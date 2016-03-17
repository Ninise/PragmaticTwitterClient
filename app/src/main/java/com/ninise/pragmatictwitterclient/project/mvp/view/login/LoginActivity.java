package com.ninise.pragmatictwitterclient.project.mvp.view.login;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.NetworkConnection;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.auth.OAuthWorker;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesProfile;
import com.ninise.pragmatictwitterclient.project.mvp.view.home.HomeActivity;
import com.ninise.pragmatictwitterclient.project.utils.Constants;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class LoginActivity extends RxAppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;

    @Bind(R.id.toolbarIcon) CircularImageView mIconCircularImageView;
    @Bind(R.id.toolbarTitle) TextView mTitleTextView;
    @Bind(R.id.loginSignInButton) AppCompatButton signInButton;
    @BindString(R.string.app_name) String mAppName;
    @BindString(R.string.permission_denied) String mPermissionDenied;
    @BindDrawable(R.drawable.app_logo) Drawable mIconBitmap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.loginMainToolbar);

        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mIconCircularImageView.setImageDrawable(mIconBitmap);
        mIconCircularImageView.setBorderWidth(1);
        mIconCircularImageView.setBorderColor(Color.BLACK);
        mTitleTextView.setText(mAppName);

        signInButton();
        showCommitFragment();
    }

    private void showCommitFragment() {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.loginContainer, new CommitsFragment());
        fragmentTransaction.commit();
    }

    private void signInButton() {
        signInButton.setOnClickListener(v1 -> {
            if (NetworkConnection.getInstance(this).isNetworkConnectionOn()) {

                OAuthWorker.getInstance(this.getApplicationContext()).getOAuth()
                        .compose(bindToLifecycle())
                        .subscribe(oauth_url -> {
                        if (oauth_url != null) {
                            Dialog auth_dialog = new Dialog(LoginActivity.this);
                            auth_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                            auth_dialog.setContentView(R.layout.dialog_auth);
                            WebView web = (WebView) auth_dialog.findViewById(R.id.loginWebView);
                            web.getSettings().setJavaScriptEnabled(true);
                            web.loadUrl(oauth_url);
                            web.setWebViewClient(new WebViewClient() {

                                boolean authComplete = false;

                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);

                                    if (url.contains(Constants.AUTH_VERIFIER) && !authComplete) {
                                        authComplete = true;
                                        Uri uri = Uri.parse(url);

                                        auth_dialog.dismiss();

                                        OAuthWorker.getInstance(getApplicationContext())
                                                .getAccessToken(uri.getQueryParameter(Constants.AUTH_VERIFIER))
                                                .compose(bindToLifecycle())
                                                .subscribe(user -> {

                                                    TwitterPreferencesProfile.getInstance(getApplicationContext()).setUserNickname(user.getScreenName());
                                                    TwitterPreferencesProfile.getInstance(getApplicationContext()).setUserName(user.getName());
                                                    TwitterPreferencesProfile.getInstance(getApplicationContext()).setUserImageUrl(user.getOriginalProfileImageURL());

                                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                                    finish();
                                                });
                                    }

                                    if (url.contains(Constants.DENIED)) {
                                        auth_dialog.dismiss();
                                        Toast.makeText(
                                                getApplicationContext(),
                                                mPermissionDenied,
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            auth_dialog.show();
                            auth_dialog.setCancelable(true);
                        }
                    });
            } else {
                Toast.makeText(this, Constants.NETWORK_STATE_IS_FALSE, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.back_pressed), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
