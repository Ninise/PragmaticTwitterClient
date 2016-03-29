package com.ninise.pragmatictwitterclient.project.mvp.model.pojos.twitter;


public class Tweet {

    private final String tweetContrib;
    private final String tweetTime;
    private final String tweetMessage;
    private final String twwetImgUrl;

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
        return twwetImgUrl;
    }

    public static class Builder {
        private String tweetContrib;
        private String tweetTime;
        private String tweetMessage;
        private String tweetImgUrl;

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

        public Tweet build() {
            return new Tweet(this);
        }
    }

    private Tweet(Builder builder) {
        tweetContrib = builder.tweetContrib;
        tweetMessage = builder.tweetMessage;
        tweetTime = builder.tweetTime;
        twwetImgUrl = builder.tweetImgUrl;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "tweetContrib='" + tweetContrib + '\'' +
                ", tweetTime='" + tweetTime + '\'' +
                ", tweetMessage='" + tweetMessage + '\'' +
                ", twwetImgUrl='" + twwetImgUrl + '\'' +
                '}';
    }
}
