package com.ninise.pragmatictwitterclient.project.mvp.model.network.auth;

import com.ninise.pragmatictwitterclient.BuildConfig;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class OAuthWorkerTest {

    private ShadowActivity mShadowActivity = new ShadowActivity();

    @Test
    public void testGetOAuth() {
        Assertions.assertThat(OAuthWorker.getInstance(mShadowActivity.getApplicationContext()).getOAuth()).isNotNull();
    }

    @Test
    public void testGetAccess() {
        Assertions.assertThat(OAuthWorker.getInstance(mShadowActivity.getApplicationContext()).getAccessToken("Some verifier")).isNotNull();
    }

}