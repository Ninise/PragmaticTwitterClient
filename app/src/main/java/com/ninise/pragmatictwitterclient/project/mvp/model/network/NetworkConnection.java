package com.ninise.pragmatictwitterclient.project.mvp.model.network;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnection {

    private static NetworkConnection mInstance = null;

    private Context mContext;

    private NetworkConnection(Context context) {
        mContext = context;
    }

    public static NetworkConnection getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkConnection(context);
        }

        return mInstance;
    }

    public boolean isNetworkConnectionOn() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
