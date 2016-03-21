package com.ninise.pragmatictwitterclient.project.mvp.presenter.home;

import android.graphics.Bitmap;

public interface IHomeView {

    void postTweeted();
    void postTweetError();
    void setProfileIcon(Bitmap icon);

}
