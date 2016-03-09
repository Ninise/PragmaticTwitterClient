package com.ninise.pragmatictwitterclient.project.mvp.view.login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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
import com.ninise.pragmatictwitterclient.project.mvp.model.network.NetworkConnection;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesAuth;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesProfile;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.auth.OAuthWorker;
import com.ninise.pragmatictwitterclient.project.mvp.view.home.HomeActivity;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

import rx.Subscriber;
import rx.functions.Action1;


public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private AppCompatButton signInButton;
    private AppCompatTextView welcomeTextView;

    private static LoginFragment mInstance = null;
    private static String oauth_verifier[] = new String[1];

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

        if (TwitterPreferencesProfile.getInstance(getActivity()).getUserNickname().equals("User")) {
            welcomeTextView.setText(getActivity().getResources().getString(R.string.login_hi_textview) + " " +
                    TwitterPreferencesProfile.getInstance(getActivity()).getUserNickname() +
                    ". " +
                    getActivity().getResources().getString(R.string.login_hi_textview_next));
        } else {
            welcomeTextView.setText(getActivity().getResources().getString(R.string.login_hi_textview) + " " +
                    TwitterPreferencesProfile.getInstance(getActivity()).getUserNickname() +
                    ". " +
                    getActivity().getResources().getString(R.string.login_welcome_back));
        }


        signInButton = (AppCompatButton) v.findViewById(R.id.loginSignInButton);
        signInButton.setOnClickListener(v1 ->  {
            if (NetworkConnection.getInstance(getActivity()).isNetworkConnectionOn()) {

                OAuthWorker.getInstance(getActivity()).getOAuth().subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Over");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "OnNextWork3");
                    }

                    @Override
                    public void onNext(String oauth_url) {
                        Log.d(TAG, oauth_url);
                        Log.d(TAG, "OnNextWork1");
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
                                        oauth_verifier[0] = uri.getQueryParameter(Constants.AUTH_VERIFIER);
//                                        TwitterPreferencesAuth.getInstance(
//                                                getActivity()).setOAuthVerifier(str);
                                        auth_dialog.dismiss();

                                        OAuthWorker.getInstance(getActivity()).getAccessToken(uri.getQueryParameter(Constants.AUTH_VERIFIER)).subscribe(aBoolean -> {
                                            getActivity().startActivity(new Intent(getActivity(), HomeActivity.class));
                                            getActivity().finish();
                                        });
                                        Log.d(TAG, "OnNextWork_Contanis");
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
                });
            } else {
                Toast.makeText(getActivity(), Constants.NETWORK_STATE_IS_FALSE, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private void logoutFromTwitter() {
        // Clear the shared preferences
        TwitterPreferencesProfile.getInstance(getActivity()).setLoginOn(false);
        TwitterPreferencesAuth.getInstance(getActivity()).setOAuthAccessTokenAndSecret("", "");
    }

    @Override
    public void onStop() {
        logoutFromTwitter();
        super.onStop();
    }


}
