package com.ninise.pragmatictwitterclient.project.mvp.presenter.home.fragment;

import android.content.Context;

public interface ITweetListPresenter {

    void getTweetList(Context context);
    void searchForTweets(Context context, String query);
}
