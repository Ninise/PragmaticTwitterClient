package com.ninise.pragmatictwitterclient.project.mvp.presenter.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.viewholders.TweetViewHolder;

public class TweetsAdapter extends RecyclerView.Adapter<TweetViewHolder> {

    public static final String TAG = TweetsAdapter.class.getSimpleName();

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        Log.d(TAG, "Adapter works");

        return new TweetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
