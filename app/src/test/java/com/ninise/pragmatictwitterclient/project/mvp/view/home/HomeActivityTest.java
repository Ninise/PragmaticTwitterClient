package com.ninise.pragmatictwitterclient.project.mvp.view.home;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ninise.pragmatictwitterclient.BuildConfig;
import com.ninise.pragmatictwitterclient.R;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAsyncTask;
import org.robolectric.shadows.ShadowToast;

import static com.ninise.pragmatictwitterclient.project.mvp.view.home.HomeActivity.*;
import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class HomeActivityTest {

    private NavigationView mNavigationView;
    private TextView mNicknameTextView;
    private TextView mUsernameTextView;
    private CircularImageView mUserIconImageView;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;

    private Activity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(HomeActivity.class);

        mDrawer = (DrawerLayout) activity.findViewById(R.id.drawer);

        mToolbar = (Toolbar) activity.findViewById(R.id.homeToolBar);
        mNavigationView = (NavigationView) activity.findViewById(R.id.navigation_view);

        final View headView = mNavigationView.getHeaderView(0);

        mNicknameTextView = (TextView) headView.findViewById(R.id.userNickName);
        mUsernameTextView = (TextView) headView.findViewById(R.id.userName);
        mUserIconImageView = (CircularImageView) headView.findViewById(R.id.userProfileIcon);
    }

    @Test
    public void activityIsNotNull() {
        Assertions.assertThat(activity).isNotNull();
    }

    @Test
    public void listFragmentShouldDisplay() {
        Assertions.assertThat(activity.findViewById(R.id.rv).getVisibility()).isEqualTo(View.VISIBLE);
    }

    @Test
    public void activityOnBackPressed() {
        activity.onBackPressed();
        Assertions.assertThat(ShadowToast.getLatestToast()).isNotNull();
    }

    @Test
    public void drawerShouldShown() {
        mDrawer.openDrawer(Gravity.LEFT);
        Assertions.assertThat(mDrawer.isShown()).isTrue();
    }

    @Test
    public void toolBarHasIcon() {
        Assertions.assertThat(mToolbar.getLogo()).isNotNull();
    }

    @Test
    public void toolBarHasRightTitle() {
        Assertions.assertThat(mToolbar.getTitle()).isEqualToIgnoringCase(activity.getResources().getString(R.string.app_name));
    }

    @Test
    public void nicknameTextViewHasNickname() {
        Assertions.assertThat(mNicknameTextView.getText()).isNotEmpty();
    }

    @Test
    public void usernameTextViewHasUsername() {
        Assertions.assertThat(mUsernameTextView.getText()).isEmpty();
    }

    @Test
    public void userIconImageViewHasIcon() {
        Assertions.assertThat(mUserIconImageView.getDrawable()).isNotNull();
    }
}