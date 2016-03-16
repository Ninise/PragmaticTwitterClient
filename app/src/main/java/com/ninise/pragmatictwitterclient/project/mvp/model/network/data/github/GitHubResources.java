package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.github;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubResources {

    String ENDPOINT = "https://api.github.com";

    @GET("/users/{user}")
    Observable<GitHubUser> getUserData(@Path("user") String user);

    @GET("/users/{user}/repos")
    Observable<GitHubUser[]> getUserRepos(@Path("user") String user);

    @GET("/repos/{user}/PragmaticTwitterClient/commits")
    Observable<List<Commit>> getUserCommit(@Path("user") String user);
}
