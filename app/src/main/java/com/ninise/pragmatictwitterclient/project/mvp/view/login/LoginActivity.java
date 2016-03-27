package com.ninise.pragmatictwitterclient.project.mvp.view.login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.login.activity.ILoginView;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.login.activity.LoginPresenter;
import com.ninise.pragmatictwitterclient.project.mvp.view.home.HomeActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

public class LoginActivity extends RxAppCompatActivity implements ILoginView {

    private boolean doubleBackToExitPressedOnce = false;

    @Bind(R.id.toolbarIcon) CircularImageView mIconCircularImageView;
    @Bind(R.id.toolbarTitle) TextView mTitleTextView;
    @Bind(R.id.loginSignInButton) AppCompatButton signInButton;
    @BindString(R.string.app_name) String mAppName;
    @BindString(R.string.permission_denied) String mPermissionDenied;
    @BindString(R.string.back_pressed) String mBackPressed;
    @BindString(R.string.not_found_wifi) String mNetworkStateFalse;
    @BindDrawable(R.drawable.app_logo) Drawable mIconBitmap;

    private LoginPresenter mLoginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.loginMainToolbar);

        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mIconCircularImageView.setImageDrawable(mIconBitmap);
        mIconCircularImageView.setBorderWidth(1);
        mIconCircularImageView.setBorderColor(Color.BLACK);
        mTitleTextView.setText(mAppName);


        RxView.clicks(signInButton).subscribe(aVoid -> {
            mLoginPresenter.attemptLogin(this);
        });

        mLoginPresenter = new LoginPresenter(this);
        mLoginPresenter.showCommitFragment(this);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, mBackPressed, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    @Override
    public void navigateToHomeActivity() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void loginFailedNetworkNotFound() {
        Toast.makeText(this, mNetworkStateFalse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailedPermissionDenied() {
        Toast.makeText(this, mPermissionDenied, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLastUpdates() {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.loginContainer, new CommitsFragment());
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

}
