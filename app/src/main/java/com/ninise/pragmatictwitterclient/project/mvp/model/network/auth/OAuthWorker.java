package com.ninise.pragmatictwitterclient.project.mvp.model.network.auth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesAuth;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesProfile;
import com.ninise.pragmatictwitterclient.project.mvp.view.home.HomeActivity;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
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

    public static final String TAG = OAuthWorker.class.getSimpleName();

    private static OAuthWorker mInstance = null;

    private Twitter twitter;
    private RequestToken requestToken = null;
    private String oauth_url;

    private Context mContext;

    public OAuthWorker(Context contex) {
        this.mContext = contex;

    }

    public static OAuthWorker getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new OAuthWorker(context);
        }

        return mInstance;
    }

//    public AsyncTask getAuth() {
//        return new GetAuth().execute();
//    }

//    protected AsyncTask getAccessToken() {
//        return new GetAccessToken().execute();
//    }

    public Observable<String> getOAuth() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(Constants.TWITTER_KEY);
        builder.setOAuthConsumerSecret(Constants.TWITTER_SECRET);
        Configuration configuration = builder.build();

        TwitterFactory factory = new TwitterFactory(configuration);
        twitter = factory.getInstance();


        return Observable.defer(() -> Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    requestToken = twitter
                            .getOAuthRequestToken(Constants.CALLBACK_URL);
                    oauth_url = requestToken.getAuthenticationURL();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }

                subscriber.onNext(oauth_url);
                subscriber.onCompleted();
            }
        }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<Boolean> getAccessToken(String oauth_verifier) {
        return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Boolean>) subscriber -> {
            try {

                Log.d(TAG, requestToken.toString() + " VERIFIER " + oauth_verifier);


                AccessToken accessToken = twitter.getOAuthAccessToken(
                        requestToken, oauth_verifier);

                TwitterPreferencesAuth.getInstance(mContext).setOAuthAccessTokenAndSecret(accessToken.getToken(),
                        accessToken.getTokenSecret());

                TwitterPreferencesProfile.getInstance(mContext).setLoginOn(true);

                long userID = accessToken.getUserId();
                User user = twitter.showUser(userID);
                String username = user.getName();

                Log.d(TAG, username);


                TwitterPreferencesProfile.getInstance(mContext).setUserNickname(user.getScreenName());
                TwitterPreferencesProfile.getInstance(mContext).setUserName(user.getName());
                TwitterPreferencesProfile.getInstance(mContext).setUserImageUrl(user.getOriginalProfileImageURL());

                subscriber.onNext(true);
                subscriber.onCompleted();

            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
//    private class GetAccessToken extends AsyncTask<String, Void, Void> {
//
//        @Override
//        protected Void doInBackground(String... params) {
//            try {
//                AccessToken accessToken = twitter.getOAuthAccessToken(
//                        requestToken, oauth_verifier);
//
//                TwitterPreferencesAuth.getInstance(mContext).setOAuthAccessTokenAndSecret(accessToken.getToken(),
//                        accessToken.getTokenSecret());
//
//                TwitterPreferencesProfile.getInstance(mContext).setLoginOn(true);
//
//                long userID = accessToken.getUserId();
//                User user = twitter.showUser(userID);
//                String username = user.getName();
//
//                Log.d(TAG, username);
//
//
//                TwitterPreferencesProfile.getInstance(mContext).setUserNickname(user.getScreenName());
//                TwitterPreferencesProfile.getInstance(mContext).setUserName(user.getName());
//                TwitterPreferencesProfile.getInstance(mContext).setUserImageUrl(user.getOriginalProfileImageURL());
//
//            } catch (TwitterException e) {
//                e.printStackTrace();
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            mContext.startActivity(new Intent(mContext, HomeActivity.class));
//            ((Activity) mContext).finish();
//        }
//    }
}
