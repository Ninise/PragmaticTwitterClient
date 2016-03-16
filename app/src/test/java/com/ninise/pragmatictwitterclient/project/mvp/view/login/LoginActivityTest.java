package com.ninise.pragmatictwitterclient.project.mvp.view.login;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.ninise.pragmatictwitterclient.BuildConfig;
import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.model.network.auth.OAuthWorker;

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


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    private AppCompatTextView loginWelcomeTextView;
    private AppCompatButton signInButton;
    private Toolbar toolbar;

    private Activity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(LoginActivity.class);
        toolbar = (Toolbar) activity.findViewById(R.id.loginMainToolbar);

        signInButton = (AppCompatButton) activity.findViewById(R.id.loginSignInButton);

        loginWelcomeTextView = (AppCompatTextView) activity.findViewById(R.id.loginLatestUpdates);
    }

    @Test
    public void activityCreated() {
        Assertions.assertThat(activity).isNotNull();
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
    public void signInButtonText() {
        assertEquals(activity.getResources().getString(R.string.login_button), signInButton.getText().toString());
    }

    @Test
    public void signInButtonClick() throws ExecutionException, InterruptedException {
        signInButton.performClick();
        Assertions.assertThat(OAuthWorker.getInstance(activity).getOAuth()).isNotNull();
    }

    @Test
    public void toastShouldBeDisplayedOnButtonClick() {
        activity.onBackPressed();
        Assertions.assertThat(ShadowToast.getTextOfLatestToast())
                .isEqualToIgnoringCase(activity.getResources().getString(R.string.back_pressed));
    }

}
