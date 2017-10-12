package com.mk.test.moviedbapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by Michael on 10/12/2017.
 */

public class ImageAsyncTaskLoader extends AsyncTaskLoader<Bitmap> {

    String mImageURL;
    Bitmap mImageBitmap;

    public ImageAsyncTaskLoader(Context context, String imageURL) {
        super(context);
        mImageURL = imageURL;
    }

    @Override
    protected void onStartLoading() {
        if (mImageBitmap != null) {
            deliverResult(mImageBitmap);
        } else {
            forceLoad();
        }
    }

    @Override
    public Bitmap loadInBackground() {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new java.net.URL(mImageURL).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void deliverResult(Bitmap bitmap) {
        mImageBitmap = bitmap;
        super.deliverResult(mImageBitmap);
    }
}
