package com.ninise.pragmatictwitterclient.project.mvc.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ninise.pragmatictwitterclient.project.utils.Constants;

public class TwitterPreferences {

    private static TwitterPreferences mInstance = null;
    private SharedPreferences mPreferences;
    private Context mContex;

    private TwitterPreferences() {
        mPreferences = mContex.getSharedPreferences(Constants.TWITTER_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static TwitterPreferences getInstance() {
        if (mInstance == null) {
            mInstance = new TwitterPreferences();
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

}
