package com.ninise.pragmatictwitterclient.project.mvp.model.pojos.twitter;


import com.ninise.pragmatictwitterclient.project.utils.TweetUrlFinder;

public class Tweet {

    private final String tweetContrib;
    private final String tweetTime;
    private final String tweetMessage;
    private final String tweetImgUrl;
    private final String tweetSource;

    public String getTweetContrib() {
        return tweetContrib;
    }

    public String getTweetTime() {
        return tweetTime;
    }

    public String getTweetMessage() {
        return tweetMessage;
    }

    public String getTweetImgUrl() {
        return tweetImgUrl;
    }

    public String getTweetSource() {
        return tweetSource;
    }

    public static class Builder {
        private String tweetContrib;
        private String tweetTime;
        private String tweetMessage;
        private String tweetImgUrl;
        private String tweetSource;

        public Builder tweetContrib(String val) {
            tweetContrib = val;
            return this;
        }

        public Builder tweetTime(String val) {
            tweetTime = val;
            return this;
        }

        public Builder tweetMessage(String val) {
            tweetMessage = val;
            return this;
        }

        public Builder tweetImgUrl(String val) {
            tweetImgUrl = val;
            return this;
        }

        public Builder tweetSource(String val) {
            tweetSource = TweetUrlFinder.getUrl(val).trim();
            return this;
        }

        public Tweet build() {
            return new Tweet(this);
        }
    }

    private Tweet(Builder builder) {
        tweetContrib = builder.tweetContrib;
        tweetMessage = builder.tweetMessage;
        tweetTime = builder.tweetTime;
        tweetImgUrl = builder.tweetImgUrl;
        tweetSource = builder.tweetSource;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "tweetContrib='" + tweetContrib + '\'' +
                ", tweetTime='" + tweetTime + '\'' +
                ", tweetMessage='" + tweetMessage + '\'' +
                ", tweetImgUrl='" + tweetImgUrl + '\'' +
                ", tweetSource='" + tweetSource + '\'' +
                '}';
    }
}
