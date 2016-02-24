package com.ninise.pragmatictwitterclient.project.mvc.view.login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvc.model.preferences.TwitterPreferences;
import com.ninise.pragmatictwitterclient.project.mvc.view.home.HomeActivity;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private AppCompatButton signInButton;
    private AppCompatTextView welcomeTextView;

    private Twitter twitter;
    private RequestToken requestToken = null;
    private String oauth_url;
    private String oauth_verifier;

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        welcomeTextView = (AppCompatTextView) v.findViewById(R.id.loginWelcomeTextView);

        if (TwitterPreferences.getInstance(getActivity()).getUserNickname().equals("User")) {
            welcomeTextView.setText(getActivity().getResources().getString(R.string.login_hi_textview) + " " +
                    TwitterPreferences.getInstance(getActivity()).getUserNickname() +
                    ". " +
                    getActivity().getResources().getString(R.string.login_hi_textview_next));
        } else {
            welcomeTextView.setText(getActivity().getResources().getString(R.string.login_hi_textview) + " " +
                    TwitterPreferences.getInstance(getActivity()).getUserNickname() +
                    ". " +
                    getActivity().getResources().getString(R.string.login_welcome_back));
        }


        signInButton = (AppCompatButton) v.findViewById(R.id.loginSignInButton);
        signInButton.setOnClickListener(v1 -> new GetAuth().execute());

        return v;
    }

    private void logoutFromTwitter() {
        // Clear the shared preferences
        TwitterPreferences.getInstance(getActivity()).setLoginOn(false);
        TwitterPreferences.getInstance(getActivity()).setOAuthAccessTokenAndSecret("", "");
    }



    @Override
    public void onStop() {
        logoutFromTwitter();
        super.onStop();
    }

    private class GetAuth extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(Constants.TWITTER_KEY);
            builder.setOAuthConsumerSecret(Constants.TWITTER_SECRET);
            Configuration configuration = builder.build();

            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
        }

        @Override
        protected String doInBackground(String ... args) {
            try {
                requestToken = twitter
                        .getOAuthRequestToken(Constants.CALLBACK_URL);
                oauth_url = requestToken.getAuthenticationURL();
                Log.e(TAG, oauth_url);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return oauth_url;
        }

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        protected void onPostExecute(String oauth_url) {
            super.onPostExecute(oauth_url);
            if (oauth_url != null) {

                Dialog auth_dialog = new Dialog(getActivity());
                auth_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                auth_dialog.setContentView(R.layout.dialog_auth);
                WebView web = (WebView) auth_dialog.findViewById(R.id.loginWebView);
                web.getSettings().setJavaScriptEnabled(true);
                web.loadUrl(oauth_url);
                web.setWebViewClient(new WebViewClient() {

                    boolean authComplete = false;

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);

                        if (url.contains(Constants.AUTH_VERIFIER) && !authComplete) {
                            authComplete = true;
                            Uri uri = Uri.parse(url);
                            oauth_verifier = uri.getQueryParameter(Constants.AUTH_VERIFIER);
                            Log.e(TAG, oauth_verifier);

                            auth_dialog.dismiss();

                            new GetAccessToken().execute();
                        } if (url.contains(Constants.DENIED)) {
                            auth_dialog.dismiss();
                            Toast.makeText(
                                    getActivity(),
                                    getActivity().getResources().getString(R.string.permission_denied),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                auth_dialog.show();
                auth_dialog.setCancelable(true);
            }
        }
    }

    private class GetAccessToken extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                AccessToken accessToken = twitter.getOAuthAccessToken(
                        requestToken, oauth_verifier);

                TwitterPreferences.getInstance(getActivity()).setOAuthAccessTokenAndSecret(accessToken.getToken(),
                        accessToken.getTokenSecret());

                TwitterPreferences.getInstance(getActivity()).setLoginOn(true);

                long userID = accessToken.getUserId();
                User user = twitter.showUser(userID);
                String username = user.getName();

                Log.d(TAG, username);


                TwitterPreferences.getInstance(getActivity()).setUserNickname(user.getScreenName());
            } catch (TwitterException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(getActivity(), HomeActivity.class));
        }
    }
}
