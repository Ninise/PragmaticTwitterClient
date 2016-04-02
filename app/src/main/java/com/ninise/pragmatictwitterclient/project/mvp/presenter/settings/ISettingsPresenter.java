package com.ninise.pragmatictwitterclient.project.mvp.presenter.settings;

import android.content.Context;

public interface ISettingsPresenter  {

    void saveChanges(Context context, boolean checked, int countOfPosts, int countOfTweets);
    void getUpdatesSwitchState(Context context);
    void getCountsSpinnersState(Context context);
}
