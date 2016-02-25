package com.ninise.pragmatictwitterclient.project.mvp.view.home;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ninise.pragmatictwitterclient.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setListFragment();
    }

    private void setListFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.homeContainer, TweetsListFragment.getInstance());
        fragmentTransaction.commit();
    }


}
