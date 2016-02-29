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

import com.ninise.pragmatictwitterclient.project.mvp.model.photo.PhotoWorker;
import com.ninise.pragmatictwitterclient.project.mvp.model.preferences.TwitterPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class TwitterProfileWorker {

    private static TwitterProfileWorker mInstance = null;

    private ProgressDialog mProgress;
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


    private class LoadProfile extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(mContex);
            mProgress.setMessage("Loading Profile ...");
            mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgress.setIndeterminate(true);
            mProgress.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                mProfileIconBitmap = BitmapFactory.decodeStream(
                        (InputStream) new URL(TwitterPreferences.getInstance(mContex).getUserImageUrl()).getContent()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mProfileIconBitmap;
        }
        protected void onPostExecute(Bitmap image) {
            Bitmap image_circle = Bitmap.createBitmap(mProfileIconBitmap.getWidth(), mProfileIconBitmap.getHeight(), Bitmap.Config.ARGB_8888);

            BitmapShader shader = new BitmapShader (mProfileIconBitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Paint paint = new Paint();
            paint.setShader(shader);
            Canvas c = new Canvas(image_circle);
            c.drawCircle(image.getWidth() / 2, image.getHeight() / 2, image.getWidth() / 2, paint);

            try {
               TwitterPreferences.getInstance(mContex).setUserIconPath( PhotoWorker.setInstance(mContex).saveToInternalStorage(image_circle));
            } catch (IOException e) {
                e.printStackTrace();
            }

            mProgress.hide();

        }
    }
}
