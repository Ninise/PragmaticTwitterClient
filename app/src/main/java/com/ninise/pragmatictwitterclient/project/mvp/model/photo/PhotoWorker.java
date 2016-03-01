package com.ninise.pragmatictwitterclient.project.mvp.model.photo;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.ninise.pragmatictwitterclient.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class PhotoWorker {

    private static PhotoWorker mInstance = null;

    private static final String PROFILE_IMAGE_NAME = "profile.jpg";
    private static final String PROFILE_IMAGE_DIR = "TwitterImages";

    private Context mContext;

    private PhotoWorker(Context context) {
        this.mContext = context;
    }

    public static PhotoWorker setInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PhotoWorker(context);
        }

        return mInstance;
    }

    public String saveToInternalStorage(Bitmap bitmapImage) throws IOException {
        ContextWrapper cw = new ContextWrapper(mContext);

        File directory = cw.getDir(PROFILE_IMAGE_DIR, Context.MODE_PRIVATE);
        File path = new File(directory, PROFILE_IMAGE_NAME);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
        Log.d("savephoto", directory.getAbsolutePath() + "/" + PROFILE_IMAGE_NAME);

        return directory.getAbsolutePath();
    }

    public Bitmap loadImageFromStorage(String path) {
        Bitmap icon = null;
        try {
            File f = new File(path, PROFILE_IMAGE_NAME);
            icon = BitmapFactory.decodeStream(new FileInputStream(f));
            Log.d("savephoto", f.getAbsolutePath());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return icon;
    }
}
