package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter;


import android.content.Context;

import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.twitter.TwitterPreferencesAuth;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class PostTweet {

    public static Observable<Boolean> setStatus(Context context, String post) {
        return Observable.create((Observable.OnSubscribe<Boolean>) subscriber -> {
            if (!post.isEmpty()) {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(Constants.TWITTER_KEY);
                builder.setOAuthConsumerSecret(Constants.TWITTER_SECRET);

                AccessToken accessToken = new AccessToken(
                        TwitterPreferencesAuth.getInstance(context).getOAuthAccessToken(),
                        TwitterPreferencesAuth.getInstance(context).getOAuthAccessTokenSecret()
                );

                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

                try {
                    twitter.updateStatus(post);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }

                subscriber.onNext(true);
            } else {
                subscriber.onError(new Throwable());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
