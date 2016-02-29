package com.ninise.pragmatictwitterclient.project.mvp.model.photo;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.ninise.pragmatictwitterclient.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class PhotoWorker {

    private static PhotoWorker mInstance = null;

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

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
        return directory.getAbsolutePath();
    }

    public Bitmap loadImageFromStorage(String path) {

        Bitmap profileImage = null;

        try {
            File f=new File(path, "profile.jpg");
            profileImage = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return profileImage;
    }
}
