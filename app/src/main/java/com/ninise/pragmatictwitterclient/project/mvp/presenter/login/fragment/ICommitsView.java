package com.ninise.pragmatictwitterclient.project.mvp.presenter.login.fragment;

import com.ninise.pragmatictwitterclient.project.mvp.model.adapters.CommitAdapter;

public interface ICommitsView {

    void onReposLoadedSuccess(CommitAdapter adapter);
    void onReposLoadedFailure();

}
