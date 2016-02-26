package com.ninise.pragmatictwitterclient.project.mvp.view.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferences;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.homeToolBar);
        setSupportActionBar(toolbar);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {

            if(menuItem.isChecked()) menuItem.setChecked(false);
            else menuItem.setChecked(true);

            drawerLayout.closeDrawers();

            return false;
        });

        View headView = navigationView.getHeaderView(0);

        ((TextView) headView.findViewById(R.id.userName)).setText(TwitterPreferences.getInstance(this).getUserName());
        ((TextView) headView.findViewById(R.id.userNickName)).setText(TwitterPreferences.getInstance(this).getUserNickname());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.openDrawer,
                R.string.closeDrawer);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        setListFragment();
    }

    private void setListFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.homeContainer, TweetsListFragment.getInstance());
        fragmentTransaction.commit();
    }


}
