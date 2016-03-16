package com.ninise.pragmatictwitterclient.project.mvp.model.network.data.github;

import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.Commit;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetUpdates {

    public static Observable<List<Commit>> getCommits() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubResources.ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubResources service = retrofit.create(GitHubResources.class);

        return rx.Observable.just("Ninise")
                .flatMap(service::getCommits)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
