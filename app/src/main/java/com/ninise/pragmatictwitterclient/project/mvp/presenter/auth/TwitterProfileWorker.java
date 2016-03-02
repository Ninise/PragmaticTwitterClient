package com.ninise.pragmatictwitterclient.project.mvp.presenter.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.util.Log;

import com.ninise.pragmatictwitterclient.project.mvp.model.photo.PhotoWorker;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class TwitterProfileWorker {

    private static TwitterProfileWorker mInstance = null;

    private Context mContex;
    Bitmap mProfileIconBitmap;


    private TwitterProfileWorker(Context context) {
        this.mContex = context;
    }

    public static TwitterProfileWorker getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TwitterProfileWorker(context);
        }

        return mInstance;
    }

//    public void getPhoto() {
//        new LoadProfile().execute();
//    }


}
