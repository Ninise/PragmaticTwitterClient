package com.ninise.pragmatictwitterclient.project.mvp.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ninise.pragmatictwitterclient.project.utils.Constants;

import twitter4j.auth.RequestToken;

public class TwitterPreferencesAuth {

    private static TwitterPreferencesAuth mInstance = null;

    private SharedPreferences mPreferences;

    private TwitterPreferencesAuth(Context context) {
        this.mPreferences = context.getSharedPreferences(Constants.TWITTER_PREFERENCES_AUTH, Context.MODE_PRIVATE);
    }

    public static TwitterPreferencesAuth getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TwitterPreferencesAuth(context);
        }

        return mInstance;
    }

    public boolean setOAuthAccessTokenAndSecret(String token, String secret) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.ACCESS_TOKEN, token);
        editor.putString(Constants.ACCESS_TOKEN_SECRET, secret);
        return editor.commit();
    }

    public String getOAuthAccessToken() {
        return mPreferences.contains(Constants.ACCESS_TOKEN) ?
                mPreferences.getString(Constants.ACCESS_TOKEN, "") : "";
    }

    public String getOAuthAccessTokenSecret() {
        return mPreferences.contains(Constants.ACCESS_TOKEN_SECRET) ?
                mPreferences.getString(Constants.ACCESS_TOKEN_SECRET, "") : "";
    }
}
