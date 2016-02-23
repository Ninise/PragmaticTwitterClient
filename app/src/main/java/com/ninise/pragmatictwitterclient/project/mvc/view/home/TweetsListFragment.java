package com.ninise.pragmatictwitterclient.project.mvc.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninise.pragmatictwitterclient.R;


public class TweetsListFragment extends Fragment {

    private static TweetsListFragment mInstance;

    public static TweetsListFragment getInstance() {
        if (mInstance == null) {
            mInstance = new TweetsListFragment();
        }

        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_tweet_list, container, false);

        return v;
    }
}
