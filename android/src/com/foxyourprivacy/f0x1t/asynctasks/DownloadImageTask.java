package com.foxyourprivacy.f0x1t.asynctasks;

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
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView; //the imageView the image is to be displayed in

    public DownloadImageTask(ImageView imageView) {
        this.imageView = imageView;
    }


    /**
     * load the image by url
     *
     * @param urls the url with http up front
     * @return the Bitmap to be displayed in the imageView
     * @author Tim
     */
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap theImage = null;
        try {
            //try loading the image by input stream
            InputStream in = new java.net.URL(urldisplay).openStream();
            theImage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            //display message on fail
            Log.e("MyApp", e.getMessage());
            e.printStackTrace();
        }
        return theImage;
    }

    /**
     * insert the loaded image in the imageView
     *
     * @author Tim
     */
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}




