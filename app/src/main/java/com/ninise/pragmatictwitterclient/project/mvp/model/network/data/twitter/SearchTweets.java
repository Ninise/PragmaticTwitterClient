package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter;

import android.content.Context;
import android.util.Log;

import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.twitter.TwitterPreferencesAuth;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

import java.util.List;

import rx.Observable;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class SearchTweets {

    public static Observable<List<Status>> searchForTweets(Context context, String query) {
        return Observable.create(subscriber -> {
            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(Constants.TWITTER_KEY);
                builder.setOAuthConsumerSecret(Constants.TWITTER_SECRET);

                AccessToken accessToken = new AccessToken(
                        TwitterPreferencesAuth.getInstance(context).getOAuthAccessToken(),
                        TwitterPreferencesAuth.getInstance(context).getOAuthAccessTokenSecret()
                );

                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
                Query q = new Query(query);
                QueryResult result = null;
                result = twitter.search(q);
                subscriber.onNext(result.getTweets());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        });
    }
}
