package com.ninise.pragmatictwitterclient.project.mvp.presenter.home.fragment;

import android.content.Context;

import com.ninise.pragmatictwitterclient.project.mvp.model.adapters.TweetsAdapter;

public interface ITweetListView {

    void getAdapter(TweetsAdapter adapter);
    void getTweets(Context context);
    void search();
}
