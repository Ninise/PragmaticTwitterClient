package com.ninise.pragmatictwitterclient;

import android.app.Activity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;

import com.ninise.pragmatictwitterclient.project.mvc.view.login.LoginActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    private AppCompatTextView loginTextView;
    private AppCompatButton signInButton;
    private Activity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(LoginActivity.class);
        signInButton = (AppCompatButton) activity.findViewById(R.id.loginSignInButton);
        loginTextView = (AppCompatTextView) activity.findViewById(R.id.loginWelcomeTextView);
    }

    @Test
    public void activityCreated() {
        assertEquals(activity.getResources().getString(R.string.login_button), signInButton.getText().toString());
    }
}
