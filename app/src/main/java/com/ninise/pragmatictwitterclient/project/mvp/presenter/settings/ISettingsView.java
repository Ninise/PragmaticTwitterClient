package com.ninise.pragmatictwitterclient.project.mvp.presenter.settings;

public interface ISettingsView {

    void onSuccess();
    void onFailed();
    void setGitHubSwitchState(boolean state);

}
