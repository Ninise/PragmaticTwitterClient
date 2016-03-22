package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter;

import android.content.Context;

import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.twitter.Tweet;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesAuth;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class GetRecentTweets {

    public static Observable<List<Status>> getTweets(Context context) {
        return Observable.just(context)
                .map(s -> {
                    ConfigurationBuilder builder = new ConfigurationBuilder();
                    builder.setOAuthConsumerKey(Constants.TWITTER_KEY);
                    builder.setOAuthConsumerSecret(Constants.TWITTER_SECRET);

                    AccessToken accessToken = new AccessToken(
                            TwitterPreferencesAuth.getInstance(s).getOAuthAccessToken(),
                            TwitterPreferencesAuth.getInstance(s).getOAuthAccessTokenSecret()
                    );

                    List<Status> statuses = null;
                    Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
                    try {
                        statuses = twitter.getHomeTimeline();
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }

                    return statuses;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
