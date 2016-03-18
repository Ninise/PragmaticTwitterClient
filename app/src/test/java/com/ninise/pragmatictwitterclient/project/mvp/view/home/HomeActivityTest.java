package com.ninise.pragmatictwitterclient.project.mvp.view.home;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ninise.pragmatictwitterclient.BuildConfig;
import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter.PostTweet;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class HomeActivityTest {

    EditText mPostTweetEditText;
    Button mPostTweetButton;
    CircularImageView mProfileCircularImageView;
    TextView mUserNicknameTextView;
    Toolbar mToolbar;

    private Activity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(HomeActivity.class);

        mToolbar = (Toolbar) activity.findViewById(R.id.homeToolbar);

        mPostTweetEditText = (EditText) activity.findViewById(R.id.homePostTweetEditText);

        mPostTweetButton = (Button) activity.findViewById(R.id.homePostTweetButton);

        mProfileCircularImageView = (CircularImageView) activity.findViewById(R.id.toolbarIcon);

        mUserNicknameTextView = (TextView) activity.findViewById(R.id.toolbarTitle);
    }

    @Test
    public void activityIsNotNull() {
        Assertions.assertThat(activity).isNotNull();
    }

    @Test
    public void activityOnBackPressed() {
        activity.onBackPressed();
        Assertions.assertThat(ShadowToast.getLatestToast()).isNotNull();
    }

    @Test
    public void toolBarHasIcon() {
        Assertions.assertThat(mProfileCircularImageView).isNotNull();
    }

    @Test
    public void toolBarHasRightTitle() {
        Assertions.assertThat(mUserNicknameTextView).isNotNull();
    }

    @Test
    public void postTweetButtonShouldSendTweet() {
        mPostTweetButton.performClick();
        Assertions.assertThat(PostTweet.setStatus(activity, "Some test")).isNotNull();
    }
}