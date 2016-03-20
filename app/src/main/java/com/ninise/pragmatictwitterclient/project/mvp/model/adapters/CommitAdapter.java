package com.ninise.pragmatictwitterclient.project.mvp.model.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.Pojo;
import com.ninise.pragmatictwitterclient.project.mvp.model.viewholders.CommitViewHolder;

import java.util.List;

public class CommitAdapter extends RecyclerView.Adapter<CommitViewHolder> {

    private List<Pojo> mDataSet;

    public CommitAdapter(List<Pojo> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public CommitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommitViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_commit_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CommitViewHolder holder, int position) {
        holder.CommitMessageTextView.setText(mDataSet.get(holder.getAdapterPosition()).getCommit());
        holder.CommitTimeTextView.setText(mDataSet.get(holder.getAdapterPosition()).getDateOfCommit());
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
