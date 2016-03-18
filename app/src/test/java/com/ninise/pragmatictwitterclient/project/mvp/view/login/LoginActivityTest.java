package com.ninise.pragmatictwitterclient.project.mvp.view.login;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
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

import java.util.concurrent.ExecutionException;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    CircularImageView mIconCircularImageView;
    TextView mTitleTextView;
    AppCompatButton mSignInButton;
    @BindDrawable(R.drawable.app_logo) Drawable mIconBitmap;

    private Activity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(LoginActivity.class);

        mIconCircularImageView = (CircularImageView) activity.findViewById(R.id.toolbarIcon);
        mIconCircularImageView.setImageDrawable(mIconBitmap);

        mTitleTextView = (TextView) activity.findViewById(R.id.toolbarTitle);
        mTitleTextView.setText(activity.getResources().getString(R.string.app_name));

        mSignInButton = (AppCompatButton) activity.findViewById(R.id.loginSignInButton);
        mSignInButton.setText(activity.getResources().getString(R.string.login_button));
    }

    @Test
    public void activityCreated() {
        Assertions.assertThat(activity).isNotNull();
    }

    @Test
    public void toolBarTitle() {
        Assertions.assertThat(mTitleTextView).isNotNull();
        Assertions.assertThat(mTitleTextView.getText().toString())
                .isEqualToIgnoringCase(activity.getResources().getString(R.string.app_name));
        Assertions.assertThat(mTitleTextView.getText().toString())
                .isNotEqualTo("Some test text");
    }

    @Test
    public void toolBarLogo() {
        Assertions.assertThat(mIconCircularImageView.getBackground())
                .isEqualTo(mIconBitmap);
        Assertions.assertThat(mIconCircularImageView).isNotNull();
    }

    @Test
    public void signInButtonText() {
        Assertions.assertThat(mSignInButton.getText().toString())
                .isEqualToIgnoringCase(activity.getResources().getString(R.string.login_button));
        Assertions.assertThat(mSignInButton.getText().toString())
                .isNotEqualTo("Some test text");
    }

    @Test
    public void signInButtonClick() throws ExecutionException, InterruptedException {
        mSignInButton.performClick();
        Assertions.assertThat(OAuthWorker.getInstance(activity).getOAuth()).isNotNull();
    }

    @Test
    public void toastShouldBeDisplayedOnButtonClick() {
        activity.onBackPressed();
        Assertions.assertThat(ShadowToast.getTextOfLatestToast())
                .isEqualToIgnoringCase(activity.getResources().getString(R.string.back_pressed));
    }

}
