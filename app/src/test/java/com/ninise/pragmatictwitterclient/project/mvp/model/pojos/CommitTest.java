package com.ninise.pragmatictwitterclient.project.mvp.model.pojos;

import com.ninise.pragmatictwitterclient.BuildConfig;
import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.github.Commit;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class CommitTest {

    private String testStr = "SomeString";
    private Commit mCommit = new Commit();

    @Test
    public void testGetMessage() throws Exception {
        mCommit.setMessage(testStr);
        Assertions.assertThat(mCommit.getMessage()).isEqualToIgnoringCase(testStr);
    }

    @Test
    public void testSetMessage() throws Exception {
        mCommit.setMessage(testStr);
        Assertions.assertThat(mCommit.getMessage()).isEqualToIgnoringCase(testStr);
    }
}