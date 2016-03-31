package com.ninise.pragmatictwitterclient.project.mvp.presenter.home.fragment;


import android.content.Context;
import android.util.Log;

import com.ninise.pragmatictwitterclient.project.mvp.model.adapters.TweetsAdapter;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter.GetRecentTweets;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter.SearchTweets;
import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.twitter.Tweet;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import twitter4j.Status;

public class TweetListPresenter implements ITweetListPresenter {

    private ITweetListView mView;

    public TweetListPresenter(ITweetListView view) {
        this.mView = view;
    }

    @Override
    public void getTweetList(Context context) {
        List<Tweet> arr = new ArrayList<>();
        GetRecentTweets.getTweets(context)
                .subscribe(
                        statuses -> {
                            for (Status sta : statuses) {
                                arr.add(new Tweet.Builder()
                                        .tweetContrib(sta.getUser().getName())
                                        .tweetMessage(sta.getText())
                                        .tweetTime(sta.getCreatedAt().toString())
                                        .tweetImgUrl(sta.getUser().getProfileImageURL())
                                        .build());
                            }
                            mView.getAdapter(new TweetsAdapter(context, arr));
                        }
                );
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
                    Log.d("das", tweets.toString());
                    Log.d("das", tweets.get(0).toString());
                    mView.getAdapter(new TweetsAdapter(context, tweets));
                });
    }

}