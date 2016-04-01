package com.ninise.pragmatictwitterclient.project.mvp.presenter.home.fragment;


import android.content.Context;

import com.ninise.pragmatictwitterclient.project.mvp.model.adapters.TweetsAdapter;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter.GetRecentTweets;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter.SearchTweets;
import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.twitter.Tweet;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TweetListPresenter implements ITweetListPresenter {

    private ITweetListView mView;

    public TweetListPresenter(ITweetListView view) {
        this.mView = view;
    }

    @Override
    public void getTweetList(Context context) {

        GetRecentTweets.getTweets(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .limit(15)
                .map(status -> new Tweet.Builder()
                            .tweetContrib(status.getUser().getName())
                            .tweetMessage(status.getText())
                            .tweetTime(status.getCreatedAt().toString())
                            .tweetImgUrl(status.getUser().getProfileImageURL())
                            .tweetSource(status.getText())
                            .build())
                .toList()
                .subscribe(tweets -> mView.getAdapter(new TweetsAdapter(context, tweets)));
    }

    @Override
    public void searchForTweets(Context context, String query) {
        SearchTweets.searchForTweets(context, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .limit(15)
                .map(status -> new Tweet.Builder()
                        .tweetContrib(status.getUser().getName())
                        .tweetMessage(status.getText())
                        .tweetTime(status.getCreatedAt().toString())
                        .tweetImgUrl(status.getUser().getProfileImageURL())
                        .build())
                .toList()
                .subscribe(tweets -> {
                    mView.getAdapter(new TweetsAdapter(context, tweets));
                });
    }

}