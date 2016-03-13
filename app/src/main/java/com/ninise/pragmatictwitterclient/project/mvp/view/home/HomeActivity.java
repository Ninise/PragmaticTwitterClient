package com.ninise.pragmatictwitterclient.project.mvp.view.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.navigation_view) NavigationView mNavigationView;
    @Bind(R.id.homeToolbar) Toolbar mToolbar;
    @Bind(R.id.drawer) DrawerLayout drawerLayout;
    @Bind(R.id.homePostTweetEditText) EditText mPostTweetEditText;
    @Bind(R.id.homePostTweetButton) Button mPostTweetButton;

    View headView;

    private boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mNavigationView.setNavigationItemSelectedListener(menuItem -> {

            if (menuItem.isChecked()) {
                menuItem.setChecked(false);
            } else {
                menuItem.setChecked(true);
            }

            drawerLayout.closeDrawers();

            return menuSelected(menuItem);
        });

        headView = mNavigationView.getHeaderView(0);

        ((TextView) headView.findViewById(R.id.userName)).setText(TwitterPreferencesProfile.getInstance(this).getUserName());
        ((TextView) headView.findViewById(R.id.userNickName)).setText(TwitterPreferencesProfile.getInstance(this).getUserNickname());

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, mToolbar, R.string.openDrawer,
                R.string.closeDrawer);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Log.d(HomeActivity.class.getSimpleName(), "Start");
        mPostTweetButton.setOnClickListener((isOver) -> {
            Log.d(HomeActivity.class.getSimpleName(), "Its works");
            PostTweet.setStatus(this, mPostTweetEditText.getText().toString()).subscribe((flag) -> {
                Toast.makeText(this, "Status updated", Toast.LENGTH_SHORT).show();
            });
        });

        try {
            ProfileImage.getProfileImage(getApplicationContext())
                    .subscribe(bitmap -> (
                            (CircularImageView) headView.findViewById(R.id.userProfileIcon)).setImageBitmap(bitmap)
                    );

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        setListFragment();
    }

    private boolean menuSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
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
