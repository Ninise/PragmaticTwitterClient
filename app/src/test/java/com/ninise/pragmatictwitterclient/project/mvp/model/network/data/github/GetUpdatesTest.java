package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.github;

import com.ninise.pragmatictwitterclient.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.observers.TestSubscriber;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class GetUpdatesTest {

    @Test
    public void testGetCommits() throws Exception {
        TestSubscriber subscriber = new TestSubscriber();

        GetUpdates.getCommits().subscribe(subscriber);
        subscriber.assertNoErrors();
    }
}