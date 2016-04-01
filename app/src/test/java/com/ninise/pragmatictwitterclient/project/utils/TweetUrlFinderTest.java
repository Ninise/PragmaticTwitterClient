package com.ninise.pragmatictwitterclient.project.utils;

import junit.framework.TestCase;

import org.assertj.core.api.Assertions;

public class TweetUrlFinderTest extends TestCase {

    public void testGetUrl() throws Exception {
        Assertions.assertThat(TweetUrlFinder.getUrl("asdasd213 https://t.co/ygpJ2Mc7gr. asdasdas"))
                .isNotNull();

        Assertions.assertThat(TweetUrlFinder.getUrl("123http://google.com.ua alalal"))
                .isNotNull();

        Assertions.assertThat(TweetUrlFinder.getUrl("asdasd213 https://t.co/ygpJ2Mc7gr asdasdas"))
                .isEqualToIgnoringCase(" https://t.co/ygpJ2Mc7gr");

        Assertions.assertThat(TweetUrlFinder.getUrl("123 http://google.com.ua alalal"))
                .isEqualToIgnoringCase(" http://google.com.ua");
    }
}