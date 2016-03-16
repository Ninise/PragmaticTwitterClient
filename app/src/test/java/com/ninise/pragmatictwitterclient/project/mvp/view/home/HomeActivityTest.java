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
import org.robolectric.shadows.ShadowToast;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class HomeActivityTest {


    private Toolbar mToolbar;

    private Activity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(HomeActivity.class);


        mToolbar = (Toolbar) activity.findViewById(R.id.homeToolbar);
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
    public void toolBarHasIcon() {
        Assertions.assertThat(mToolbar.getLogo()).isNotNull();
    }

    @Test
    public void toolBarHasRightTitle() {
        Assertions.assertThat(mToolbar.getTitle()).isEqualToIgnoringCase(activity.getResources().getString(R.string.app_name));
    }
}