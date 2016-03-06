package com.ninise.pragmatictwitterclient.project.mvp.presenter.auth;

import android.app.Activity;
import android.os.AsyncTask;

import com.ninise.pragmatictwitterclient.BuildConfig;
import com.ninise.pragmatictwitterclient.project.mvp.view.login.LoginActivity;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class OAuthWorkerTest {

    private ShadowActivity mShadowActivity = new ShadowActivity();

    @Test
    public void authAsyncTaskShouldBeNotNull() {
        AsyncTask auth = OAuthWorker.getInstance(mShadowActivity.getApplicationContext()).getAuth();

        Robolectric.flushBackgroundThreadScheduler();

        Assertions.assertThat(auth).isNotNull();
    }
}