package com.ninise.pragmatictwitterclient.project.mvp.view.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.NetworkConnection;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferences;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.auth.OAuthWorker;
import com.ninise.pragmatictwitterclient.project.utils.Constants;


public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private AppCompatButton signInButton;
    private AppCompatTextView welcomeTextView;

    private static LoginFragment mInstance = null;

    public static LoginFragment getInstance() {
        if (mInstance == null) {
            return new LoginFragment();
        }

        return mInstance;
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
        signInButton.setOnClickListener(v1 ->  {
            if (NetworkConnection.getInstance(getActivity()).isNetworkConnectionOn()) {
                OAuthWorker.getInstance(getActivity()).getAuth();
            } else {
                Toast.makeText(getActivity(), Constants.NETWORK_STATE_IS_FALSE, Toast.LENGTH_SHORT).show();
            }
        });

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


}
