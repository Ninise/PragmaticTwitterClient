package com.ninise.pragmatictwitterclient.project.mvp.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.adapters.CommitAdapter;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.login.fragment.CommitsPresenter;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.login.fragment.ICommitsView;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

public class CommitsFragment extends RxFragment implements ICommitsView {

    @Bind(R.id.rv) RecyclerView mRecyclerView;
    @BindString(R.string.not_found_wifi) String mWifiNotFound;

    private CommitsPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CommitsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);
        ButterKnife.bind(this, v);

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);

        final RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadRecentCommits(getActivity());
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onReposLoadedSuccess(CommitAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onReposLoadedFailure() {
        Toast.makeText(getActivity(), mWifiNotFound, Toast.LENGTH_SHORT).show();
    }
}
