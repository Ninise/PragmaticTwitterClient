package com.ninise.pragmatictwitterclient.project.mvp.presenter.settings;


import android.content.Context;

import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.settings.SettingsPreferences;

public class SettingsPresenter implements ISettingsPresenter {

    private final ISettingsView mView;

    public SettingsPresenter(ISettingsView view) {
        this.mView = view;
    }

    @Override
    public void saveChanges(Context context, boolean checked, int countOfPosts, int countOfTweets) {
        try {
            SettingsPreferences pref = SettingsPreferences.getInstance(context);

            pref.setGithubUpdatesStatus(checked);
            pref.setCountOfTweets(countOfTweets);
            pref.setCountOfPosts(countOfPosts);

            mView.onSuccess();
        } catch (Exception e) {
            mView.onFailed();
        }
    }

    @Override
    public void getUpdatesSwitchState(Context context) {
        if (SettingsPreferences.getInstance(context).getGithubUpdatesStatus()) {
            mView.setGitHubSwitchState(true);
        } else {
            mView.setGitHubSwitchState(false);
        }
    }

    @Override
    public void getCountsSpinnersState(Context context) {
        SettingsPreferences pref = SettingsPreferences.getInstance(context);
        mView.setCountsSpinners(
                pref.getCountOfPosts(),
                pref.getCountOfTweets());
    }
}
