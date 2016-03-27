package com.ninise.pragmatictwitterclient.project.mvp.presenter.settings;


import android.content.Context;

import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.settings.SettingsPreferences;

public class SettingsPresenter implements ISettingsPresenter {

    private final ISettingsView mView;

    public SettingsPresenter(ISettingsView view) {
        this.mView = view;
    }

    @Override
    public void saveChanges(Context context, boolean checked) {
        try {
            SettingsPreferences.getInstance(context).setGithubUpdatesStatus(checked);
            mView.onSuccess();
        } catch (Exception e) {
            mView.onFailed();
        }
    }

    @Override
    public void getUpdatesSwitchState(Context context) {
        if (SettingsPreferences.getInstance(context).getGithugUpdatesStatus()) {
            mView.setGitHubSwitchState(true);
        } else {
            mView.setGitHubSwitchState(false);
        }
    }
}
