package com.ninise.pragmatictwitterclient.project.mvp.view.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.adapters.TweetsAdapter;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.home.fragment.ITweetListView;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.home.fragment.TweetListPresenter;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;


public class TweetsListFragment extends Fragment implements ITweetListView {

    @Bind(R.id.rv) RecyclerView mRecyclerView;
    @BindString(R.string.progress_dialog_loadTitle) String mProgressDialogTitle;
    @BindString(R.string.progress_dialog_loading) String mProgressDialogMessage;

    private TweetListPresenter mPresenter;

    private static TweetsListFragment mInstance = null;

    private ProgressDialog mProgressDialog;

    public static TweetsListFragment getInstance() {
        if (mInstance == null) {
            mInstance = new TweetsListFragment();
        }

        return mInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

        RxToolbar.itemClicks((Toolbar) getActivity().findViewById(R.id.homeToolbar)).subscribe(this::menuSelected);

        mPresenter = new TweetListPresenter(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getTweets(getActivity());
    }

    private boolean menuSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.search_tweets:
                return true;
            case R.id.update_tweets:
                getTweets(getActivity());
                return true;
            case R.id.settings:
                return true;
            case R.id.about:
                return true;
            case R.id.logout:
                return true;
            default:
                Toast.makeText(getActivity(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public void getAdapter(TweetsAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        mProgressDialog.dismiss();
    }

    @Override
    public void getTweets(Context context) {
        mProgressDialog = ProgressDialog.show(getActivity(), mProgressDialogTitle, mProgressDialogMessage);
        mPresenter.getTweetList(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
