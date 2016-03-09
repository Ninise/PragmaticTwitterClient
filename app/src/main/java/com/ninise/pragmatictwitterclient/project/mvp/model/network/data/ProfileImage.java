package com.ninise.pragmatictwitterclient.project.mvp.model.network.data;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesProfile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileImage {
    public static Observable<Bitmap> getProfileImage(Context context) throws MalformedURLException {
        return  Observable.defer(() -> Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                try {
                    TwitterPreferencesProfile.getInstance(context).getUserImageUrl();
                    subscriber.onNext(BitmapFactory.decodeStream(
                            new URL(TwitterPreferencesProfile.getInstance(context).getUserImageUrl()).openStream()
                    ));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()))
                .cache();
    }
}
