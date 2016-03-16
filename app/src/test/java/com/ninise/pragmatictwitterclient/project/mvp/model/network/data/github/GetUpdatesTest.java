package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.github;

import android.util.Log;

import com.ninise.pragmatictwitterclient.BuildConfig;
import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.Commit;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import rx.observers.TestSubscriber;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class GetUpdatesTest {

    @Test
    public void testGetCommits() throws Exception {
        TestSubscriber subscriber = new TestSubscriber();

        GetUpdates.getCommits().subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertReceivedOnNext(new ArrayList<>());
    }

    @Test
    public void testGet() {
        GetUpdates.getCommits().subscribe(commits -> Log.d("Lst", commits.toString() + "asd"));
        Assertions.assertThat(false).isTrue();
    }
}