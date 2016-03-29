package com.ninise.pragmatictwitterclient.project.mvp.model.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.data.twitter.ProfileImage;
import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.twitter.Tweet;
import com.ninise.pragmatictwitterclient.project.mvp.model.viewholders.TweetViewHolder;

import java.net.MalformedURLException;
import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetViewHolder> {

    private List<Tweet> mDataSet;
    private Context mContext;

    public TweetsAdapter(Context context,List<Tweet> dataSet) {
        this.mDataSet = dataSet;
        this.mContext = context;
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
        holder.tweetContribTextView.setText(mDataSet.get(holder.getAdapterPosition()).getTweetContrib());
        try {
            ProfileImage.getProfileImage(mContext, mDataSet.get(holder.getAdapterPosition()).getTweetImgUrl())
                    .subscribe(holder.tweetImageView::setImageBitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        holder.cv.setOnClickListener(e -> {
            Tweet tweet = mDataSet.get(holder.getAdapterPosition());
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                    .setTitle(tweet.getTweetContrib())
                    .setMessage(tweet.getTweetMessage())
                    .setPositiveButton("Ok", null);
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
