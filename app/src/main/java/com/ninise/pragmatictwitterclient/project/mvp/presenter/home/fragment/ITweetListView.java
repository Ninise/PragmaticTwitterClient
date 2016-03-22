package com.ninise.pragmatictwitterclient.project.mvp.presenter.home.fragment;

import com.ninise.pragmatictwitterclient.project.mvp.model.adapters.TweetsAdapter;

public interface ITweetListView {

    void getAdapter(TweetsAdapter adapter);
}
