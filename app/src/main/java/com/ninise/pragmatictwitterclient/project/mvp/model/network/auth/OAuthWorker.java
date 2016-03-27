package com.ninise.pragmatictwitterclient.project.mvp.model.network.auth;

import android.content.Context;

import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.twitter.TwitterPreferencesAuth;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class OAuthWorker {

    private static OAuthWorker mInstance = null;

    private Twitter twitter;
    private RequestToken requestToken = null;
    private String oauth_url;

    private Context mContext;

    public OAuthWorker(Context context) {
        this.mContext = context;

    }

    public static OAuthWorker getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new OAuthWorker(context);
        }

        return mInstance;
    }

    public Observable<String> getOAuth() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(Constants.TWITTER_KEY);
        builder.setOAuthConsumerSecret(Constants.TWITTER_SECRET);
        Configuration configuration = builder.build();

        TwitterFactory factory = new TwitterFactory(configuration);
        twitter = factory.getInstance();


        return Observable.just(twitter).map(v -> {
            try {
                requestToken = v.getOAuthRequestToken(Constants.CALLBACK_URL);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return requestToken.getAuthenticationURL();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<User> getAccessToken(String oauth_verifier) {
        return Observable.just(oauth_verifier).map(v -> {
                    AccessToken accessToken = null;
                    User user = null;
                    try {
                             accessToken = twitter.getOAuthAccessToken(requestToken, v);
                             TwitterPreferencesAuth.getInstance(mContext)
                                    .setOAuthAccessTokenAndSecret(
                                         accessToken.getToken(),
                                          accessToken.getTokenSecret()
                                    );

                             long userID = accessToken.getUserId();
                             user = twitter.showUser(userID);
                         } catch (TwitterException e) {
                              e.printStackTrace();
                         }
                    return user;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
