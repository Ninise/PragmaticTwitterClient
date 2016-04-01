package com.ninise.pragmatictwitterclient.project.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TweetUrlFinder {

    private static final Pattern urlPattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    public static String getUrl(String text) {
        Matcher matcher = urlPattern.matcher(text);
        int matchStart = 0;
        int matchEnd = 0;
        while (matcher.find()) {
            matchStart = matcher.start();
            matchEnd = matcher.end();
        }

        return text.substring(matchStart, matchEnd);
    }
}
