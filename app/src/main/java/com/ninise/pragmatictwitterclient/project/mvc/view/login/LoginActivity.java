package com.ninise.pragmatictwitterclient.project.mvc.view.login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.ninise.pragmatictwitterclient.R;



public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.loginMainToolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);


        switchToFragment();
    }

    private void switchToFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.loginContrainer, LoginFragment.getInstance());
        fragmentTransaction.commit();
    }
}
