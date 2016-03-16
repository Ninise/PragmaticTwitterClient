package com.ninise.pragmatictwitterclient.project.mvp.model.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninise.pragmatictwitterclient.R;

public class TweetViewHolder extends RecyclerView.ViewHolder {

    final CardView cv;
    final TextView groupTitleTextView;
    final ImageView tweetImageView;
    final TextView tweetInfoTextView;

    public TweetViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cv);
        groupTitleTextView = (TextView) itemView.findViewById(R.id.cardViewCommitMessage);
        tweetImageView = (ImageView) itemView.findViewById(R.id.cardViewTweetImage);
        tweetInfoTextView = (TextView) itemView.findViewById(R.id.loginLatestUpdates);
    }
}
