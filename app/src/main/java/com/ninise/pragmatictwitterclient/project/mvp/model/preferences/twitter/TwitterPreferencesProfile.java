package com.ninise.pragmatictwitterclient.project.mvp.model.preferences.twitter;

import android.content.Context;
import android.content.SharedPreferences;

import com.ninise.pragmatictwitterclient.project.utils.Constants;

public class TwitterPreferencesProfile {

    private static TwitterPreferencesProfile mInstance = null;
    private SharedPreferences mPreferences;

    private TwitterPreferencesProfile(Context context) {
        mPreferences = context.getSharedPreferences(Constants.TWITTER_PREFERENCES_PROFILE, Context.MODE_PRIVATE);
    }

    public static TwitterPreferencesProfile getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TwitterPreferencesProfile(context);
        }

        return mInstance;
    }

    public boolean setUserNickname(String nickname) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.TWITTER_NICKNAME, nickname);
        return editor.commit();
    }

    public String getUserNickname() {
        return mPreferences.contains(Constants.TWITTER_NICKNAME) ?
                mPreferences.getString(Constants.TWITTER_NICKNAME, "User") : "User";
    }

    public boolean setUserImageUrl(String url) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.IMAGE_URL, url);
        return editor.commit();
    }

    public String getUserImageUrl() {
        return mPreferences.contains(Constants.IMAGE_URL) ?
                mPreferences.getString(Constants.IMAGE_URL, "") : "";
    }

    public boolean setUserName(String name) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.TWITTER_NAME, name);
        return editor.commit();
    }

    public String getUserName() {
        return mPreferences.contains(Constants.TWITTER_NAME) ?
                mPreferences.getString(Constants.TWITTER_NAME, "") : "";
    }
}
