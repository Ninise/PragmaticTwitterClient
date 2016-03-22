package com.ninise.pragmatictwitterclient.project.mvp.model.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ninise.pragmatictwitterclient.R;

public class CommitViewHolder extends RecyclerView.ViewHolder {

    final public CardView cvCommit;
    final public TextView CommitMessageTextView;
    final public TextView CommitTimeTextView;

    public CommitViewHolder(View itemView) {
        super(itemView);
        cvCommit = (CardView) itemView.findViewById(R.id.cvCommit);
        CommitMessageTextView = (TextView) itemView.findViewById(R.id.cardViewTweetMessage);
        CommitTimeTextView = (TextView) itemView.findViewById(R.id.cardViewTweetTime);
    }
}
