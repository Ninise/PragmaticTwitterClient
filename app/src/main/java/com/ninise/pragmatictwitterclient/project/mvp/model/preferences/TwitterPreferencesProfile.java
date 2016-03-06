package com.ninise.pragmatictwitterclient.project.mvp.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ninise.pragmatictwitterclient.project.utils.Constants;

public class TwitterPreferencesProfile {

    private static TwitterPreferencesProfile mInstance = null;
    private SharedPreferences mPreferences;
    private Context mContex;

    private TwitterPreferencesProfile(Context context) {
        this.mContex = context;
        mPreferences = mContex.getSharedPreferences(Constants.TWITTER_PREFERENCES_PROFILE, Context.MODE_PRIVATE);
    }

    public static TwitterPreferencesProfile getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TwitterPreferencesProfile(context);
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

    public void setUserName(String name) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.TWITTER_NAME, name);
        editor.apply();
    }

    public String getUserName() {
        return mPreferences.contains(Constants.TWITTER_NAME) ?
                mPreferences.getString(Constants.TWITTER_NAME, "") : "";
    }
}
