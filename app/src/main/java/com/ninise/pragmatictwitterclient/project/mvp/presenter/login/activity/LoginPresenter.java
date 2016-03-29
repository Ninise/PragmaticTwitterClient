package com.ninise.pragmatictwitterclient.project.mvp.presenter.login.activity;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.NetworkConnection;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.auth.OAuthWorker;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.settings.SettingsPreferences;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.twitter.TwitterPreferencesProfile;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView mView;

    public LoginPresenter(ILoginView view) {
        this.mView = view;
    }

    @Override
    public void attemptLogin(Context context) {
        if (NetworkConnection.getInstance(context).isNetworkConnectionOn()) {

            OAuthWorker.getInstance(context.getApplicationContext()).getOAuth()
                    .subscribe(oauth_url -> {
                        if (oauth_url != null) {
                            Dialog auth_dialog = new Dialog(context);
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

                                        OAuthWorker.getInstance(context.getApplicationContext())
                                                .getAccessToken(uri.getQueryParameter(Constants.AUTH_VERIFIER))
                                                .subscribe(user -> {
                                                    user.getOriginalProfileImageURL();
                                                    TwitterPreferencesProfile.getInstance(context.getApplicationContext()).setUserNickname(user.getScreenName());
                                                    TwitterPreferencesProfile.getInstance(context.getApplicationContext()).setUserName(user.getName());
                                                    TwitterPreferencesProfile.getInstance(context.getApplicationContext()).setUserImageUrl(user.getOriginalProfileImageURL());
                                                });

                                        mView.navigateToHomeActivity();
                                    }

                                    if (url.contains(Constants.DENIED)) {
                                        auth_dialog.dismiss();
                                        mView.loginFailedPermissionDenied();
                                    }

                                }
                            });

                            auth_dialog.show();
                            auth_dialog.setCancelable(true);
                        }
                    });
        } else {
            mView.loginFailedNetworkNotFound();
        }
    }

    @Override
    public void showCommitFragment(Context context) {
        if (SettingsPreferences.getInstance(context).getGithubUpdatesStatus())
            mView.showLastUpdates();
    }
}
