package com.ninise.pragmatictwitterclient.project.mvc.view.login;

import android.annotation.SuppressLint;
import android.app.Dialog;
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

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvc.control.auth.OAuthWorker;
import com.ninise.pragmatictwitterclient.project.mvc.model.preferences.TwitterPreferences;


public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private AppCompatButton signInButton;
    private AppCompatTextView welcomeTextView;

    private boolean flag = false;

    public LoginFragment() {}

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (flag) {
           OAuthWorker.getInstance(getActivity()).getAccess().execute();
            flag = false;
        }
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
                OAuthWorker.getInstance(getActivity()).auth().execute();
                flag = true;
            }
        });

        return v;
    }
}
