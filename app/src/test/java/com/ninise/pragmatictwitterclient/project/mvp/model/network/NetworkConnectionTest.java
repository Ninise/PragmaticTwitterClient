package com.ninise.pragmatictwitterclient.project.mvp.model.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ninise.pragmatictwitterclient.BuildConfig;
import com.ninise.pragmatictwitterclient.project.mvp.view.login.LoginActivity;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowNetworkInfo;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class NetworkConnectionTest {

    private Activity activity;
    private ConnectivityManager connectivityManager;
    private ShadowConnectivityManager shadowConnectivityManager;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(LoginActivity.class);
        connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        shadowConnectivityManager = Shadows.shadowOf(connectivityManager);
    }

    @Test
    public void testIsNetworkConnectionOn() throws Exception {
        NetworkInfo networkInfo =  ShadowNetworkInfo.newInstance(
                NetworkInfo.DetailedState.CONNECTED,
                ConnectivityManager.TYPE_WIFI, 0, true, true);

        shadowConnectivityManager.setActiveNetworkInfo(networkInfo);

        Assertions.assertThat(NetworkConnection.getInstance(activity).isNetworkConnectionOn()).isTrue();
    }
}