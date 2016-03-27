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

    public boolean getGithugUpdatesStatus() {
        return mPreferences.contains(Constants.SETTINGS_GITUPDATES)
                && mPreferences.getBoolean(Constants.SETTINGS_GITUPDATES, false);
    }
}
