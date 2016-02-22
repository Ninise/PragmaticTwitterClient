package com.ninise.pragmatictwitterclient.project.mvc.view.login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvc.control.auth.OAuthWorker;
import com.ninise.pragmatictwitterclient.project.mvc.model.preferences.TwitterPreferences;
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

    public LoginFragment() {}

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
        welcomeTextView.setText(getActivity().getResources().getString(R.string.login_hi_textview) + " " +
                TwitterPreferences.getInstance(getActivity()).getUserNickname());

        signInButton = (AppCompatButton) v.findViewById(R.id.loginSignInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToTwitter();
            }
        });

        if (!isTwitterLoggedInAlready()) {
            OAuthWorker.getInstance(getActivity()).getAccess(getActivity().getIntent().getData());
        }

        return v;
    }

    private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            OAuthWorker.getInstance(getActivity()).auth();
            super.onDestroy();
        } else {
            // user already logged into twitter
            Toast.makeText(getActivity().getApplicationContext(),
                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
        }
    }

    private void logoutFromTwitter() {
        // Clear the shared preferences
        TwitterPreferences.getInstance(getActivity()).setLoginOn(false);
        TwitterPreferences.getInstance(getActivity()).setOAuthAccessTokenAndSecret("", "");

    }

    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from TwitterPreferences
        return TwitterPreferences.getInstance(getActivity()).getLoginOn();
    }

    @Override
    public void onDestroy() {
        logoutFromTwitter();
        super.onDestroy();
    }
}
