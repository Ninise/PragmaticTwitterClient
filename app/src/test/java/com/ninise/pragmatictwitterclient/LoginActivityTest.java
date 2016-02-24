package com.ninise.pragmatictwitterclient;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;

import com.ninise.pragmatictwitterclient.project.mvp.view.login.LoginActivity;
import com.ninise.pragmatictwitterclient.project.mvp.view.login.LoginFragment;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.junit.Assert.assertEquals;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    private AppCompatTextView loginWelcomeTextView;
    private AppCompatButton signInButton;
    private Toolbar toolbar;
    private Activity activity;
    private Fragment fragment;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(LoginActivity.class);
        fragment = LoginFragment.getInstance();
        signInButton = (AppCompatButton) activity.findViewById(R.id.loginSignInButton);
        loginWelcomeTextView = (AppCompatTextView) activity.findViewById(R.id.loginWelcomeTextView);
        toolbar = (Toolbar) activity.findViewById(R.id.loginMainToolbar);

        SupportFragmentTestUtil.startFragment(fragment);
    }

    @Test
    public void fragmentCreated() {
        Assertions.assertThat(fragment.getView()).isNotNull();
    }

    @Test
    public void toolBarTitle() {
        Assertions.assertThat(toolbar.getTitle().toString()).isEqualToIgnoringCase(activity.getResources().getString(R.string.app_name));
    }

    @Test
    public void signInButtonText() {
        assertEquals(activity.getResources().getString(R.string.login_button), signInButton.getText().toString());
    }

    @Test
    public void loginWelcomeText() {
        assertEquals(activity.getResources().getString(R.string.login_hi_textview), loginWelcomeTextView.getText().toString());
    }

    @Test
    public void signInButtonClick() {
        Assertions.assertThat(signInButton.performClick()).isTrue();
    }

}
