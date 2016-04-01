package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter;

import com.ninise.pragmatictwitterclient.BuildConfig;

import junit.framework.TestCase;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SearchTweetsTest extends TestCase {

    private ShadowActivity mShadowActivity = new ShadowActivity();
    private String query = "Ukraine";

    @Test
    public void testSearchForTweets() throws Exception {
        Assertions.assertThat(SearchTweets.searchForTweets(mShadowActivity.getApplicationContext(), query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()).isNotNull();
    }
}