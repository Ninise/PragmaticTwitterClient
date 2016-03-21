package com.ninise.pragmatictwitterclient.project.mvp.view.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding.view.RxView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesProfile;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.home.HomePresenter;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.home.IHomeView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;


public class HomeActivity extends RxAppCompatActivity implements IHomeView {

    @Bind(R.id.homeToolbar) Toolbar mToolbar;
    @Bind(R.id.homePostTweetEditText) EditText mPostTweetEditText;
    @Bind(R.id.homePostTweetButton) Button mPostTweetButton;
    @Bind(R.id.toolbarIcon) CircularImageView mProfileCircularImageView;
    @Bind(R.id.toolbarTitle) TextView mUserNicknameTextView;
    @BindString(R.string.tweet_posted) String tweetPosted;
    @BindString(R.string.tweet_not_posted) String tweetNotPosted;

    private boolean doubleBackToExitPressedOnce = false;

    private HomePresenter mPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        RxToolbar.itemClicks(mToolbar).subscribe(this::menuSelected);

        mUserNicknameTextView.setText(TwitterPreferencesProfile.getInstance(this).getUserNickname());

        RxView.clicks(mPostTweetButton).subscribe((view) -> {
            mPresenter.postTweet(this, mPostTweetEditText.getText().toString());
            mPostTweetEditText.setText("");
        });

        setListFragment();

        mPresenter = new HomePresenter(this);

        mPresenter.getProfilePhoto(this);
    }

    private boolean menuSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.search_tweets:
                return true;
            case R.id.update_tweets:
                return true;
            case R.id.settings:
                return true;
            case R.id.about:
                return true;
            case R.id.logout:
                closeApp();
                return true;
            default:
                Toast.makeText(this, "Somethings Wrong", Toast.LENGTH_SHORT).show();
                return true;
        }
    }

    private void closeApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        finish();
    }

    private void setListFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.homeContainer, TweetsListFragment.getInstance());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.back_pressed), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void postTweeted() {
        Toast.makeText(this, tweetPosted, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postTweetError() {
        Toast.makeText(this, tweetNotPosted, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProfileIcon(Bitmap icon) {
        mProfileCircularImageView.setImageBitmap(icon);
    }
}
