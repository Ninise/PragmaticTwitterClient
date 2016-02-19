package com.ninise.pragmatictwitterclient.project.mvc.control.auth;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.ninise.pragmatictwitterclient.project.mvc.model.preferences.TwitterPreferences;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class OAuthWorker {

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

    public AsyncTask<String, String, String> auth() {
        return new TokenGet();
    }

    public AsyncTask<String, String, Boolean> getAccess() {
        return new AccessTokenGet();
    }

    private class TokenGet extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                requestToken = twitter.getOAuthRequestToken();
                oauth_url = requestToken.getAuthorizationURL();
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            return oauth_url;
        }

        @Override
        protected void onPostExecute(final String oauth_url) {
            if (oauth_url != null) {
                Intent broadcast = new Intent(Constants.AUTH_RECEIVER);
                broadcast.putExtra(Constants.AUTH_URL, oauth_url);
                mContext.sendBroadcast(broadcast);
            }
        }
    }

    private class AccessTokenGet extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... args) {

            try {

                accessToken = twitter.getOAuthAccessToken(requestToken, TwitterPreferences.getInstance(mContext).getOAuthVerifier());
                TwitterPreferences.getInstance(mContext).setOAuthAccessTokenAndSecret(accessToken.getToken(), accessToken.getTokenSecret());
                User user = twitter.showUser(accessToken.getUserId());
                TwitterPreferences.getInstance(mContext).setUserNickname(user.getName());
                TwitterPreferences.getInstance(mContext).setUserImageUrl(user.getOriginalProfileImageURL());

            } catch (TwitterException e) {
                e.printStackTrace();
            }

            return true;
        }
        @Override
        protected void onPostExecute(Boolean response) {

        }

    }
}


