package com.ninise.pragmatictwitterclient.project.mvp.presenter.settings;

import android.content.Context;

public interface ISettingsPresenter  {

    void saveChanges(Context context, boolean checked);
    void getUpdatesSwitchState(Context context);
}
