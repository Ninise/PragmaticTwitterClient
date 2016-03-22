package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter;

import android.util.Log;

import com.ninise.pragmatictwitterclient.BuildConfig;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class GetRecentTweetsTest {

    @Test
    public void testGetTweets() throws Exception {
//        GetRecentTweets.getTweets().subscribe(
//                statuses -> Log.d("das", statuses.toString())
//        );
//        Assertions.assertThat(true).isFalse();
    }
}