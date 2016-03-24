package com.ninise.pragmatictwitterclient.project.mvp.presenter.home.fragment;


import android.content.Context;

import com.ninise.pragmatictwitterclient.project.mvp.model.adapters.TweetsAdapter;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter.GetRecentTweets;
import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.twitter.Tweet;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class TweetListPresenter implements ITweetListPresenter {

    private ITweetListView mView;

    public TweetListPresenter(ITweetListView view) {
        this.mView = view;
    }

    @Override
    public void getTweetList(Context context) {
        List<Tweet> arr = new ArrayList<>();
        GetRecentTweets.getTweets(context).subscribe(
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

}