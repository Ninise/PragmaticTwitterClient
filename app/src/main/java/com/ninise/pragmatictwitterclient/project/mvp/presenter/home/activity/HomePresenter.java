package com.ninise.pragmatictwitterclient.project.mvp.presenter.home.activity;


import android.content.Context;

import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter.PostTweet;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter.ProfileImage;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.twitter.TwitterPreferencesProfile;

import java.net.MalformedURLException;

public class HomePresenter implements IHomePresenter {

    private IHomeView mView;

    public HomePresenter(IHomeView view) {
        this.mView = view;
    }

    @Override
    public void postTweet(Context context, String tweet) {
        PostTweet.setStatus(context, tweet)
                .subscribe((aVoid) -> mView.postTweeted(), (aVoid) -> mView.postTweetError());
    }

    @Override
    public void getProfilePhoto(Context context) {
        try {
            ProfileImage.getProfileImage(context, TwitterPreferencesProfile.getInstance(context).getUserImageUrl())
                    .subscribe(mView::setProfileIcon);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
