package com.ninise.pragmatictwitterclient.project.mvp.model.preferences.settings;


import android.content.Context;
import android.content.SharedPreferences;

import com.ninise.pragmatictwitterclient.project.utils.Constants;

public class SettingsPreferences {

    private static SettingsPreferences mInstance = null;

    private SharedPreferences mPreferences;

    private SettingsPreferences(Context context) {
        mPreferences = context.getSharedPreferences(Constants.SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static SettingsPreferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SettingsPreferences(context);
        }

        return mInstance;
    }

    public boolean setGithubUpdatesStatus(boolean checked) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(Constants.SETTINGS_GITUPDATES, checked);
        return editor.commit();
    }

    public boolean getGithubUpdatesStatus() {
        return mPreferences.contains(Constants.SETTINGS_GITUPDATES)
                && mPreferences.getBoolean(Constants.SETTINGS_GITUPDATES, false);
    }

    public boolean setCountOfTweets(int count) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(Constants.SETTINGS_COUNT_OF_TWEETS, count);
        return editor.commit();
    }

    public int getCountOfTweets() {
        return mPreferences.contains(Constants.SETTINGS_COUNT_OF_TWEETS) ?
                mPreferences.getInt(Constants.SETTINGS_COUNT_OF_TWEETS, 1) : 1;
    }

    public boolean setCountOfPosts(int count) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(Constants.SETTINGS_COUNT_OF_POSTS, count);
        return editor.commit();
    }

    public int getCountOfPosts() {
        return mPreferences.contains(Constants.SETTINGS_COUNT_OF_POSTS) ?
                mPreferences.getInt(Constants.SETTINGS_COUNT_OF_POSTS, 1) : 1;
    }
}
