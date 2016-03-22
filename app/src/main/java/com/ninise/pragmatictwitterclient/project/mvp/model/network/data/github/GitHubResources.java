package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.github;


import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.github.Pojo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubResources {

    String ENDPOINT = "https://api.github.com";

    @GET("/repos/{user}/PragmaticTwitterClient/commits")
    Observable<List<Pojo>> getCommits(@Path("user") String user);
}
