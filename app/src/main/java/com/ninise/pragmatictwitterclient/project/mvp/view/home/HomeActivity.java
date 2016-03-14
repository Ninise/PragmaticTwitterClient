package com.ninise.pragmatictwitterclient.project.mvp.view.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.PostTweet;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.ProfileImage;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesProfile;

import java.net.MalformedURLException;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.homeToolbar) Toolbar mToolbar;
    @Bind(R.id.homePostTweetEditText) EditText mPostTweetEditText;
    @Bind(R.id.homePostTweetButton) Button mPostTweetButton;
    @Bind(R.id.userProfileIcon) CircularImageView mProfileCircularImageView;
    @Bind(R.id.userNickName) TextView mUserNicknameTextView;
    @BindString(R.string.update_status) String updateStatus;

    private boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mUserNicknameTextView.setText(TwitterPreferencesProfile.getInstance(this).getUserNickname());

        mPostTweetButton.setOnClickListener((isOver) ->
            PostTweet.setStatus(this, mPostTweetEditText.getText().toString()).subscribe((flag) -> {
                Toast.makeText(this, updateStatus, Toast.LENGTH_SHORT).show();
            })
        );

        try {
            ProfileImage.getProfileImage(getApplicationContext())
                    .subscribe(bitmap -> mProfileCircularImageView.setImageBitmap(bitmap));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        setListFragment();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        return menuSelected(item);
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
}
