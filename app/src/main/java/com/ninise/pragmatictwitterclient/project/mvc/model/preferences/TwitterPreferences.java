package com.ninise.pragmatictwitterclient.project.mvc.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ninise.pragmatictwitterclient.project.utils.Constants;

public class TwitterPreferences {

    private static TwitterPreferences mInstance = null;
    private SharedPreferences mPreferences;
    private Context mContex;
    private String uri;

    private TwitterPreferences(Context context) {
        this.mContex = context;
        mPreferences = mContex.getSharedPreferences(Constants.TWITTER_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static TwitterPreferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TwitterPreferences(context);
        }

        return mInstance;
    }

    public void setUserNickname(String nickname) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.TWITTER_NICKNAME, nickname);
        editor.apply();
    }

    public String getUserNickname() {
        return mPreferences.contains(Constants.TWITTER_NICKNAME) ?
                mPreferences.getString(Constants.TWITTER_NICKNAME, "User") : "User";
    }

    public void setOAuthVerifier(String verifier) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.TWITTER_OAUTH_VERIFIER, verifier);
        editor.apply();
    }

    public String getOAuthVerifier() {
        return mPreferences.contains(Constants.TWITTER_OAUTH_VERIFIER) ?
                mPreferences.getString(Constants.TWITTER_OAUTH_VERIFIER, "") : "";
    }

    public void setOAuthAccessTokenAndSecret(String token, String secret) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.ACCESS_TOKEN, token);
        editor.putString(Constants.ACCESS_TOKEN_SECRET, secret);
        editor.apply();
    }

    public String getOAuthAccessToken() {
        return mPreferences.contains(Constants.ACCESS_TOKEN) ?
                mPreferences.getString(Constants.ACCESS_TOKEN, "") : "";
    }

    public String getOAuthAccessTokenSecret() {
        return mPreferences.contains(Constants.ACCESS_TOKEN_SECRET) ?
                mPreferences.getString(Constants.ACCESS_TOKEN_SECRET, "") : "";
    }

    public void setUserImageUrl(String url) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.IMAGE_URL, url);
        editor.apply();
    }

    public String getUserImageUrl() {
        return mPreferences.contains(Constants.IMAGE_URL) ?
                mPreferences.getString(Constants.IMAGE_URL, "") : "";
    }

    public void setLoginOn(boolean status) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(Constants.IS_LOGIN_ON, status);
        editor.apply();
    }

    public boolean getLoginOn() {
        return mPreferences.contains(Constants.IS_LOGIN_ON) && mPreferences.getBoolean(Constants.IS_LOGIN_ON, false);
    }
}
