package com.ninise.pragmatictwitterclient.project.mvp.model.network.data;

import android.graphics.Bitmap;

import com.ninise.pragmatictwitterclient.BuildConfig;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesProfile;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ProfileImageTest {

    private final static String TEST_IMAGE_LINK = "https://cdn1.iconfinder.com/data/icons/iconza-circle-social/64/697029-twitter-512.png";
    private ShadowActivity mShadowActivity = new ShadowActivity();
    @Test
    public void testGetProfileImage() throws Exception {
        TwitterPreferencesProfile.getInstance(mShadowActivity.getApplicationContext()).setUserImageUrl(TEST_IMAGE_LINK);

        final Bitmap[] actual = {null};
        ProfileImage.getProfileImage(mShadowActivity.getApplicationContext()).subscribe(
                bitmap -> {
                    actual[0] = bitmap;}
        );
        Assertions.assertThat(actual[0]).isNull();
    }
}