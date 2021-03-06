package com.ninise.pragmatictwitterclient.project.mvp.model.viewholders;

import android.view.View;

import com.ninise.pragmatictwitterclient.BuildConfig;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class CommitViewHolderTest {

    private ShadowActivity mShadowActivity;

    @Before
    public void setUp() {
        mShadowActivity = new ShadowActivity();
    }

    @Test
    public void testViewHolderShouldBeNotNull() {
        Assertions.assertThat(new CommitViewHolder(new View(mShadowActivity.getApplicationContext()))).isNotNull();
    }
}