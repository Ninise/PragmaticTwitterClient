package com.ninise.pragmatictwitterclient.project.mvp.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.adapters.TweetsAdapter;


public class TweetsListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TweetsAdapter adapter;

    private static TweetsListFragment mInstance = null;

    public static TweetsListFragment getInstance() {
        if (mInstance == null) {
            mInstance = new TweetsListFragment();
        }

        return mInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new TweetsAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(adapter);

        return v;
    }
}
