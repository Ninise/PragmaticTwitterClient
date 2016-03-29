package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter;

import com.ninise.pragmatictwitterclient.BuildConfig;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter.PostTweet;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import rx.Observable;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class PostTweetTest {

    private ShadowActivity mShadowActivity = new ShadowActivity();

    @Test
    public void testSetStatus() throws Exception {
        Observable<Boolean> postTweet = PostTweet.setStatus(mShadowActivity.getApplicationContext(), "Test post @pratic");
        Assertions.assertThat(postTweet.subscribe()).isNotNull();
    }
}