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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvc.control.auth.OAuthWorker;


public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private AppCompatButton signInButton;

    public LoginFragment() {}

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        signInButton = (AppCompatButton) v.findViewById(R.id.loginSignInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OAuthWorker.getInstance(getActivity()).auth().execute();
            }
        });

        return v;
    }
}
