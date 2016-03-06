package com.ninise.pragmatictwitterclient.project.mvp.view.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferencesProfile;

import java.io.IOException;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;

    private boolean doubleBackToExitPressedOnce = false;
    Bitmap mProfileIconBitmap;
    NavigationView navigationView;
    View headView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.homeToolBar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {

            if (menuItem.isChecked()) {
                menuItem.setChecked(false);
            } else {
                menuItem.setChecked(true);
            }

            drawerLayout.closeDrawers();

            return menuSelected(menuItem);
        });

        headView = navigationView.getHeaderView(0);

        ((TextView) headView.findViewById(R.id.userName)).setText(TwitterPreferencesProfile.getInstance(this).getUserName());
        ((TextView) headView.findViewById(R.id.userNickName)).setText(TwitterPreferencesProfile.getInstance(this).getUserNickname());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.openDrawer,
                R.string.closeDrawer);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        setListFragment();
        new LoadProfile().execute();
    }

    private boolean menuSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.settings:
                return true;
            case R.id.about:
                return true;
            case R.id.logout:
                closeApp();
                return true;
            default:
                Toast.makeText(this, "Somethings Wrong", Toast.LENGTH_SHORT).show();
                return true;
        }
    }

    private void closeApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        finish();
    }

    private void setListFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.homeContainer, TweetsListFragment.getInstance());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.back_pressed), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    public class LoadProfile extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                mProfileIconBitmap = BitmapFactory.decodeStream(
                        new URL(TwitterPreferencesProfile.getInstance(getApplicationContext()).getUserImageUrl().trim()).openStream()
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mProfileIconBitmap;
        }
        protected void onPostExecute(Bitmap image) {
            ((CircularImageView) headView.findViewById(R.id.userProfileIcon)).setImageBitmap(mProfileIconBitmap);
        }
    }
}
