package com.ninise.pragmatictwitterclient.project.mvp.model.preferences.settings;

import com.ninise.pragmatictwitterclient.BuildConfig;

import junit.framework.TestCase;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SettingsPreferencesTest extends TestCase {

    private ShadowActivity mActivity;

    @Before
    public void setUp() {
        mActivity = new ShadowActivity();
    }

    @Test
    public void testGetInstance() throws Exception {
        Assertions.assertThat(SettingsPreferences.getInstance(mActivity.getApplicationContext())).isNotNull();
    }

    @Test
    public void testSetGithubUpdatesStatus() throws Exception {
        Assertions.assertThat(
                SettingsPreferences.getInstance(mActivity.getApplicationContext()).setGithubUpdatesStatus(true)
        ).isNotNull();
        Assertions.assertThat(
                SettingsPreferences.getInstance(mActivity.getApplicationContext()).setGithubUpdatesStatus(true)
        ).isEqualTo(true);
    }

    @Test
    public void testGetGithubUpdatesStatus() throws Exception {
        SettingsPreferences.getInstance(mActivity.getApplicationContext()).setGithubUpdatesStatus(true);
        Assertions.assertThat(
                SettingsPreferences.getInstance(mActivity.getApplicationContext()).getGithubUpdatesStatus()
        ).isNotNull();

        Assertions.assertThat(
                SettingsPreferences.getInstance(mActivity.getApplicationContext()).getGithubUpdatesStatus()
        ).isEqualTo(true);

    }
}