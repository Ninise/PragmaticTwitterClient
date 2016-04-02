package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter;

import android.content.Context;

import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.settings.SettingsPreferences;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.twitter.TwitterPreferencesAuth;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

import java.util.List;

import rx.Observable;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class GetRecentTweets {

    public static Observable<List<Status>> getTweets(Context context) {
        return Observable.create(subscriber -> {
                    ConfigurationBuilder builder = new ConfigurationBuilder();
                    builder.setOAuthConsumerKey(Constants.TWITTER_KEY);
                    builder.setOAuthConsumerSecret(Constants.TWITTER_SECRET);

                    AccessToken accessToken = new AccessToken(
                            TwitterPreferencesAuth.getInstance(context).getOAuthAccessToken(),
                            TwitterPreferencesAuth.getInstance(context).getOAuthAccessTokenSecret()
                    );

                    List<Status> statuses = null;
                    Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
                    try {
                        statuses = twitter.getHomeTimeline(new Paging().count(
                                (SettingsPreferences.getInstance(context).getCountOfTweets() + 1) * 10
                        ));
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }

                    subscriber.onNext(statuses);
                });
    }
}
