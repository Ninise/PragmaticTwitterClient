package com.ninise.pragmatictwitterclient.project.mvp.presenter.auth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferences;
import com.ninise.pragmatictwitterclient.project.mvp.view.home.HomeActivity;
import com.ninise.pragmatictwitterclient.project.mvp.view.login.LoginActivity;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class OAuthWorker {

    public static final String TAG = OAuthWorker.class.getSimpleName();

    private static OAuthWorker mInstance = null;

    private Twitter twitter;
    private RequestToken requestToken = null;
    private String oauth_url;
    private String oauth_verifier;

    private Context mContex;

    public OAuthWorker(Context contex) {
        this.mContex = contex;
    }

    public static OAuthWorker getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new OAuthWorker(context);
        }

        return mInstance;
    }

    public void getAuth() {
        new GetAuth().execute();
    }

    private class GetAuth extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(Constants.TWITTER_KEY);
            builder.setOAuthConsumerSecret(Constants.TWITTER_SECRET);
            Configuration configuration = builder.build();

            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
        }

        @Override
        protected String doInBackground(String ... args) {
            try {
                requestToken = twitter
                        .getOAuthRequestToken(Constants.CALLBACK_URL);
                oauth_url = requestToken.getAuthenticationURL();
                Log.e(TAG, oauth_url);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return oauth_url;
        }

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        protected void onPostExecute(String oauth_url) {
            super.onPostExecute(oauth_url);
            if (oauth_url != null) {

                Dialog auth_dialog = new Dialog(mContex);
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
                            oauth_verifier = uri.getQueryParameter(Constants.AUTH_VERIFIER);

                            auth_dialog.dismiss();

                            new GetAccessToken().execute();
                        } if (url.contains(Constants.DENIED)) {
                            auth_dialog.dismiss();
                            Toast.makeText(
                                    mContex,
                                    mContex.getResources().getString(R.string.permission_denied),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                auth_dialog.show();
                auth_dialog.setCancelable(true);
            }
        }
    }

    private class GetAccessToken extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                AccessToken accessToken = twitter.getOAuthAccessToken(
                        requestToken, oauth_verifier);

                TwitterPreferences.getInstance(mContex).setOAuthAccessTokenAndSecret(accessToken.getToken(),
                        accessToken.getTokenSecret());

                TwitterPreferences.getInstance(mContex).setLoginOn(true);

                long userID = accessToken.getUserId();
                User user = twitter.showUser(userID);
                String username = user.getName();

                Log.d(TAG, username);


                TwitterPreferences.getInstance(mContex).setUserNickname(user.getScreenName());
                TwitterPreferences.getInstance(mContex).setUserName(user.getName());
            } catch (TwitterException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mContex.startActivity(new Intent(mContex, HomeActivity.class));
            ((Activity) mContex).finish();
        }
    }
}
