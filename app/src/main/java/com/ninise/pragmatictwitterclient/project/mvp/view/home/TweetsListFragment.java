package com.ninise.pragmatictwitterclient.project.mvp.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.adapters.TweetsAdapter;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter.GetRecentTweets;
import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.twitter.Tweet;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.home.fragment.ITweetListView;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.home.fragment.TweetListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TweetsListFragment extends Fragment implements ITweetListView {

    @Bind(R.id.rv) RecyclerView mRecyclerView;

    private TweetListPresenter mPresenter;

    private static TweetsListFragment mInstance = null;

    public static TweetsListFragment getInstance() {
        if (mInstance == null) {
            mInstance = new TweetsListFragment();
        }

        return mInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        mPresenter = new TweetListPresenter(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getTweetList(getActivity());
    }

    @Override
    public void getAdapter(TweetsAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
