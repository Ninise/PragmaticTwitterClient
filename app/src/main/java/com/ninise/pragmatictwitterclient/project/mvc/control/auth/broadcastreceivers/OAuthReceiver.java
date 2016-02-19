package com.ninise.pragmatictwitterclient.project.mvc.control.auth.broadcastreceivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ninise.pragmatictwitterclient.project.mvc.view.login.WebActivity;
import com.ninise.pragmatictwitterclient.project.utils.Constants;

public class OAuthReceiver extends BroadcastReceiver {

    public static final String TAG = OAuthReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, intent.getStringExtra(Constants.AUTH_URL));

        Intent webIntent = new Intent(context, WebActivity.class);
        webIntent.putExtra(Constants.AUTH_URL, intent.getStringExtra(Constants.AUTH_URL));
        webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(webIntent);
    }
}
