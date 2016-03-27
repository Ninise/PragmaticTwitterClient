package com.ninise.pragmatictwitterclient.project.mvp.model.preferences;

import com.ninise.pragmatictwitterclient.BuildConfig;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.twitter.TwitterPreferencesAuth;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowPreference;

import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class TwitterPreferencesAuthTest {

    ShadowActivity activity = new ShadowActivity();

    private TwitterPreferencesAuth mPreferencesAuth;

    final String testAccessToken = "2321353123gfd1sad14";
    final String testAccessSecret = "sddr3fas2144gs3413";

    @Before
    public void setUp() {
        mPreferencesAuth = TwitterPreferencesAuth.getInstance(activity.getApplicationContext());
        mPreferencesAuth.setOAuthAccessTokenAndSecret(testAccessToken, testAccessSecret);
    }

    @Test
    public void testGetInstance() throws Exception {
        Assertions.assertThat(mPreferencesAuth).isNotNull();
    }

    @Test
    public void testSetOAuthAccessTokenAndSecret() throws Exception {
        Assertions.assertThat(mPreferencesAuth.setOAuthAccessTokenAndSecret(testAccessToken, testAccessSecret)).isTrue();
    }

    @Test
    public void testGetOAuthAccessToken() throws Exception {
        Assertions.assertThat(mPreferencesAuth.getOAuthAccessToken()).isEqualTo(testAccessToken);
    }

    @Test
    public void testGetOAuthAccessTokenSecret() throws Exception {
        Assertions.assertThat(mPreferencesAuth.getOAuthAccessTokenSecret()).isEqualTo(testAccessSecret);
    }
}