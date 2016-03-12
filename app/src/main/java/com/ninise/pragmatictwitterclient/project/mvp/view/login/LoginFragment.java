package com.ninise.pragmatictwitterclient.project.mvp.view.login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.NetworkConnection;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesProfile;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.auth.OAuthWorker;
import com.ninise.pragmatictwitterclient.project.mvp.view.home.HomeActivity;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import rx.functions.Action1;


public class LoginFragment extends Fragment {

    @Bind(R.id.loginSignInButton) AppCompatButton signInButton;
    @Bind(R.id.loginWelcomeTextView) AppCompatTextView welcomeTextView;
    @BindString(R.string.login_hi_textview_next) String loginTextView;
    @BindString(R.string.login_hi_textview) String loginHi;
    @BindString(R.string.login_welcome_back) String loginWelcomeBack;


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

        ButterKnife.bind(this, v);

        if (TwitterPreferencesProfile.getInstance(getActivity()).getUserNickname().equals("User")) {
            welcomeTextView.setText(loginHi + " " +
                    TwitterPreferencesProfile.getInstance(getActivity()).getUserNickname() +
                    ". " + loginTextView);
        } else {
            welcomeTextView.setText(loginHi + " " +
                    TwitterPreferencesProfile.getInstance(getActivity()).getUserNickname() +
                    ". " + loginWelcomeBack);
        }

        signInButton.setOnClickListener(v1 -> {
            if (NetworkConnection.getInstance(getActivity()).isNetworkConnectionOn()) {

                OAuthWorker.getInstance(getActivity()).getOAuth().subscribe(new Action1<String>() {
                    @Override
                    public void call(String oauth_url) {
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

                                        auth_dialog.dismiss();

                                        OAuthWorker.getInstance(getActivity())
                                                .getAccessToken(uri.getQueryParameter(Constants.AUTH_VERIFIER))
                                                .subscribe(user -> {

                                            TwitterPreferencesProfile.getInstance(getActivity()).setUserNickname(user.getScreenName());
                                            TwitterPreferencesProfile.getInstance(getActivity()).setUserName(user.getName());
                                            TwitterPreferencesProfile.getInstance(getActivity()).setUserImageUrl(user.getOriginalProfileImageURL());

                                            getActivity().startActivity(new Intent(getActivity(), HomeActivity.class));
                                            getActivity().finish();

                                        });
                                    }

                                    if (url.contains(Constants.DENIED)) {
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
                });
            } else {
                Toast.makeText(getActivity(), Constants.NETWORK_STATE_IS_FALSE, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
