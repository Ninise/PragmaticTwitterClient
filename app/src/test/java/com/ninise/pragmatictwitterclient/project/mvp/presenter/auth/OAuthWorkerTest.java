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


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class OAuthWorkerTest {

    private Activity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(LoginActivity.class);
    }

    @Test
    public void authAsyncTaskShouldBeNotNull() {
        AsyncTask auth = OAuthWorker.getInstance(activity).getAuth();

        Robolectric.flushBackgroundThreadScheduler();

        Assertions.assertThat(auth).isNotNull();
    }
}