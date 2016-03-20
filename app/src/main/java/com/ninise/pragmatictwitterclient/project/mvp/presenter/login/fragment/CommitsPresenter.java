package com.ninise.pragmatictwitterclient.project.mvp.presenter.login.fragment;

import android.content.Context;

import com.ninise.pragmatictwitterclient.project.mvp.model.adapters.CommitAdapter;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.NetworkConnection;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.github.GetUpdates;
public class CommitsPresenter implements ICommitsPresenter {

    private ICommitsView mView;

    public CommitsPresenter(ICommitsView view) {
        this.mView = view;
    }

    @Override
    public void loadRecentCommits(Context context) {
        if (NetworkConnection.getInstance(context).isNetworkConnectionOn()) {
            GetUpdates.getCommits()
                    .subscribe(pojos -> {
                        mView.onReposLoadedSuccess(new CommitAdapter(pojos));
                    });
        } else {
            mView.onReposLoadedFailure();
        }
    }
}
