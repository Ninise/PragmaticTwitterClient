package com.ninise.pragmatictwitterclient.project.mvp.presenter.home.activity;

import android.graphics.Bitmap;

public interface IHomeView {

    void postTweeted();
    void postTweetError();
    void setProfileIcon(Bitmap icon);

}
