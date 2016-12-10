package com.bp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Ich on 09.09.2016.
 */

/**
 * Class to handle the loading of images in an extra task
 */
 class SaveValueTask extends AsyncTask<Void, Void, Void> {


    /**
     * insert the loaded image in the imageView
     * @author Tim
     */
    protected void onPostExecute(Bitmap result) {
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ValueKeeper v=ValueKeeper.getInstance();
        v.saveInstance();
        return null;
    }
}




