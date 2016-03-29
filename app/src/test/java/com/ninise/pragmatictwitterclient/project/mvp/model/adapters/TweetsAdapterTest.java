package com.ninise.pragmatictwitterclient.project.mvp.model.adapters;

import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.twitter.Tweet;

import junit.framework.TestCase;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TweetsAdapterTest extends TestCase {

    private TweetsAdapter mTweetsAdapter;

    @Before
    public void setUp() throws Exception {
        mTweetsAdapter = new TweetsAdapter(
                null,
                Arrays.asList(new Tweet.Builder().build(), new Tweet.Builder().build())
        );
    }

    @Test
    public void testOnCreateTweetsAdapter() throws Exception{
        Assertions.assertThat(mTweetsAdapter).isNotNull();
    }

    @Test
    public void testGetItemCount() throws Exception {
        Assertions.assertThat(mTweetsAdapter.getItemCount()).isNotNull();
        Assertions.assertThat(mTweetsAdapter.getItemCount()).isEqualTo(2);
    }
}