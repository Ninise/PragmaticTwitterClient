package com.ninise.pragmatictwitterclient.project.mvp.presenter.home;

import android.content.Context;

public interface IHomePresenter {

    void postTweet(Context context, String tweet);
    void getProfilePhoto(Context context);

}
