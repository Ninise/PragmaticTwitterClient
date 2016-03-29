package com.ninise.pragmatictwitterclient.project.mvp.model.pojos.twitter;

import junit.framework.TestCase;

import org.assertj.core.api.Assertions;
import org.junit.Before;


public class TweetTest extends TestCase {

    private Tweet mTweet;
    private static final String CONTRIB  = "DevNinise";
    private static final String IMG_UTL = "https://some.com/hi.jpg";
    private static final String MESSAGE = "Hi, there!";
    private static final String TIME = "2016-03-29 18:42:03";

    @Before
    public void setUp() {
        mTweet = new Tweet.Builder()
                .tweetContrib(CONTRIB)
                .tweetImgUrl(IMG_UTL)
                .tweetMessage(MESSAGE)
                .tweetTime(TIME)
                .build();
    }

    public void testGetTweetContrib() throws Exception {
        Assertions.assertThat(mTweet.getTweetContrib()).isNotEmpty();
        Assertions.assertThat(mTweet.getTweetContrib()).isNotNull();
        Assertions.assertThat(mTweet.getTweetContrib()).isEqualToIgnoringCase(CONTRIB);
    }

    public void testGetTweetTime() throws Exception {
        Assertions.assertThat(mTweet.getTweetTime()).isNotEmpty();
        Assertions.assertThat(mTweet.getTweetTime()).isNotNull();
        Assertions.assertThat(mTweet.getTweetTime()).isEqualToIgnoringCase(TIME);
    }

    public void testGetTweetMessage() throws Exception {
        Assertions.assertThat(mTweet.getTweetMessage()).isNotEmpty();
        Assertions.assertThat(mTweet.getTweetMessage()).isNotNull();
        Assertions.assertThat(mTweet.getTweetMessage()).isEqualToIgnoringCase(MESSAGE);
    }

    public void testGetTweetImgUrl() throws Exception {
        Assertions.assertThat(mTweet.getTweetImgUrl()).isNotEmpty();
        Assertions.assertThat(mTweet.getTweetImgUrl()).isNotNull();
        Assertions.assertThat(mTweet.getTweetImgUrl()).isEqualToIgnoringCase(IMG_UTL);
    }
}