package com.ninise.pragmatictwitterclient.project.mvp.model.preferences;

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
public class TwitterPreferencesProfileTest {

    private final ShadowActivity mShadowActivity = new ShadowActivity();
    private TwitterPreferencesProfile mTwitterPreferencesProfile;

    private final String USER_NICKNAME = "Ninise";
    private final String USER_NAME = "Nikita";
    private final String USER_IMAGE_URL = "https://somesite.ua/some_image.png";

    @Before
    public void setUp() throws Exception {
        mTwitterPreferencesProfile = TwitterPreferencesProfile.getInstance(mShadowActivity.getApplicationContext());
    }

    @Test
    public void testGetInstance() throws Exception {
        Assertions.assertThat(TwitterPreferencesProfile.getInstance(mShadowActivity.getApplicationContext())).isNotNull();
    }

    @Test
    public void testSetUserNickname() throws Exception {
        Assertions.assertThat(mTwitterPreferencesProfile.setUserNickname(USER_NICKNAME)).isTrue();
    }

    @Test
    public void testGetUserNickname() throws Exception {
        mTwitterPreferencesProfile.setUserNickname(USER_NICKNAME);
        Assertions.assertThat(mTwitterPreferencesProfile.getUserNickname()).isEqualTo(USER_NICKNAME);
    }

    @Test
    public void testSetUserImageUrl() throws Exception {
        Assertions.assertThat(mTwitterPreferencesProfile.setUserImageUrl(USER_IMAGE_URL)).isTrue();
    }

    @Test
    public void testGetUserImageUrl() throws Exception {
        mTwitterPreferencesProfile.setUserImageUrl(USER_IMAGE_URL);
        Assertions.assertThat(mTwitterPreferencesProfile.getUserImageUrl()).isEqualTo(USER_IMAGE_URL);
    }

    @Test
    public void testSetUserName() throws Exception {
        Assertions.assertThat(mTwitterPreferencesProfile.setUserName(USER_NAME)).isTrue();
    }

    @Test
    public void testGetUserName() throws Exception {
        mTwitterPreferencesProfile.setUserName(USER_NAME);
        Assertions.assertThat(mTwitterPreferencesProfile.getUserName()).isEqualTo(USER_NAME);
    }
}