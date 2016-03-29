package com.ninise.pragmatictwitterclient.project.mvp.model.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninise.pragmatictwitterclient.R;

public class TweetViewHolder extends RecyclerView.ViewHolder {

    public final CardView cv;
    public final TextView tweetMessageTextView;
    public final ImageView tweetImageView;
    public final TextView tweetTimeTextView;
    public final TextView tweetContribTextView;

    public TweetViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cv);
        tweetMessageTextView = (TextView) itemView.findViewById(R.id.cardViewTweetMessage);
        tweetImageView = (ImageView) itemView.findViewById(R.id.cardViewTweetImage);
        tweetTimeTextView = (TextView) itemView.findViewById(R.id.cardViewTweetTime);
        tweetContribTextView = (TextView) itemView.findViewById(R.id.cardViewContrib);
    }
}
