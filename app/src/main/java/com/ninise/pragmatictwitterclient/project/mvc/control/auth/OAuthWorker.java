package com.ninise.pragmatictwitterclient.project.mvc.control.auth;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ninise.pragmatictwitterclient.project.mvc.model.preferences.TwitterPreferences;
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
    private AccessToken accessToken;
    private String oauth_url;
    private Context mContext;

    private OAuthWorker(Context context) {
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        this.mContext = context;
    }

    public static OAuthWorker getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new OAuthWorker(context);
        }
        return mInstance;
    }

    public void auth() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(Constants.TWITTER_KEY);
        builder.setOAuthConsumerSecret(Constants.TWITTER_SECRET);
        Configuration configuration = builder.build();

        TwitterFactory factory = new TwitterFactory(configuration);
        twitter = factory.getInstance();


        new Thread(() -> {
            try {
                requestToken = twitter
                        .getOAuthRequestToken(Constants.CALLBACK_URL);
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse(requestToken.getAuthenticationURL())).
                        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY).
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void getAccess(final Uri uri) {
        new Thread(() -> {
            try {

                if (uri != null && uri.toString().startsWith(Constants.CALLBACK_URL)) {

                    // OAuth verifier
                    String verifier = uri
                            .getQueryParameter(Constants.AUTH_VERIFIER);
                    // Get the access token
                    AccessToken accessToken1 = twitter.getOAuthAccessToken(
                            requestToken, verifier);

                    TwitterPreferences.getInstance(mContext).setOAuthAccessTokenAndSecret(accessToken1.getToken(),
                            accessToken1.getTokenSecret());

                    TwitterPreferences.getInstance(mContext).setLoginOn(true);

                    long userID = accessToken1.getUserId();
                    User user = twitter.showUser(userID);
                    String username = user.getName();

                    TwitterPreferences.getInstance(mContext).setUserNickname(user.getScreenName());
                    Log.d(TAG, username);
                }

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }).start();
    }
}


