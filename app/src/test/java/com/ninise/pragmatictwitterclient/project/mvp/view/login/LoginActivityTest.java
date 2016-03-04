package com.ninise.pragmatictwitterclient.project.mvp.view.login;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.ninise.pragmatictwitterclient.BuildConfig;
import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.auth.OAuthWorker;
import com.ninise.pragmatictwitterclient.project.mvp.view.login.LoginActivity;
import com.ninise.pragmatictwitterclient.project.mvp.view.login.LoginFragment;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import org.robolectric.shadows.ShadowToast;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    private AppCompatTextView loginWelcomeTextView;
    private AppCompatButton signInButton;
    private ImageView loginAppImageView;
    private ImageView loginAppNameImageView;
    private Toolbar toolbar;

    private Activity activity;

    private Fragment fragment;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(LoginActivity.class);
        fragment = LoginFragment.getInstance();
        toolbar = (Toolbar) activity.findViewById(R.id.loginMainToolbar);

        signInButton = (AppCompatButton) activity.findViewById(R.id.loginSignInButton);

        loginWelcomeTextView = (AppCompatTextView) activity.findViewById(R.id.loginWelcomeTextView);
        loginAppImageView = (ImageView) activity.findViewById(R.id.loginAppImageView);
        loginAppNameImageView = (ImageView) activity.findViewById(R.id.loginAppNameImageView);

        SupportFragmentTestUtil.startFragment(fragment);
    }

    @Test
    public void activityCreated() {
        Assertions.assertThat(activity).isNotNull();
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
    public void toolBarLogo() {
        Assertions.assertThat(toolbar.getLogo()).isNotNull();
    }

    @Test
    public void loginWelcomeText() {
        assertEquals(activity.getResources().getString(R.string.login_hi_textview) +
                " User. " +
                activity.getResources().getString(R.string.login_hi_textview_next), loginWelcomeTextView.getText().toString());
    }

    @Test
    public void signInButtonText() {
        assertEquals(activity.getResources().getString(R.string.login_button), signInButton.getText().toString());
    }

    @Test
    public void signInButtonClick() throws ExecutionException, InterruptedException {
        signInButton.performClick();

        AsyncTask auth = OAuthWorker.getInstance(activity).getAuth();

        Robolectric.flushBackgroundThreadScheduler();

        Assertions.assertThat(auth.get()).isNotNull();
    }

    @Test
    public void loginAppImageView() {
        Assertions.assertThat(loginAppImageView.getDrawable()).isNotNull();
    }

    @Test
    public void loginAppNameImageView() {
        Assertions.assertThat(loginAppNameImageView).isNotNull();
    }

    @Test
    public void toastShouldBeDisplayedOnButtonClick() {
        activity.onBackPressed();
        Assertions.assertThat(ShadowToast.getTextOfLatestToast())
                .isEqualToIgnoringCase(activity.getResources().getString(R.string.back_pressed));
    }

}
