package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesProfile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileImage {
    public static Observable<Bitmap> getProfileImage(Context context) throws MalformedURLException {
        return Observable.just(context).map(con -> {
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(
                                new URL(TwitterPreferencesProfile.getInstance(con).getUserImageUrl()).openStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return bitmap;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }
}
