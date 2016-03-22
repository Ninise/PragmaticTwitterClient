package com.ninise.pragmatictwitterclient.project.mvp.model.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.twitter.Tweet;
import com.ninise.pragmatictwitterclient.project.mvp.model.viewholders.TweetViewHolder;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetViewHolder> {

    private List<Tweet> mDataSet;

    public TweetsAdapter(List<Tweet> dataSet) {
        this.mDataSet = dataSet;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_tweet_item, parent, false);

        return new TweetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        holder.tweetMessageTextView.setText(mDataSet.get(holder.getAdapterPosition()).getTweetMessage());
        holder.tweetTimeTextView.setText(mDataSet.get(holder.getAdapterPosition()).getTweetTime());
        holder.tweetContriBTextView.setText(mDataSet.get(holder.getAdapterPosition()).getTweetContrib());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
