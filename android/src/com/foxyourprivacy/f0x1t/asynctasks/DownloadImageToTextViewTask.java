package com.foxyourprivacy.f0x1t.asynctasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Another async task for loading images to a slide
 * Created by Tim on 09.09.2016.
 */
class DownloadImageToTextViewTask extends AsyncTask<String, Void, Bitmap> {
    private final TextView textView; //the textView the image is to be displayed in
    private final Context context;

    public DownloadImageToTextViewTask(TextView textView, Context con) {
        this.textView = textView;
        context = con;
    }


    /**
     * load the image by url
     *
     * @param urls the url with http up front
     * @return the Bitmap to be displayed in the imageView
     * @author Tim/Noah
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
            Log.e("ImageDownloadFail", e.getMessage());
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
        Log.d("after download", result.toString());
        Drawable d = new BitmapDrawable(context.getResources(), result);
        textView.setCompoundDrawables(null, null, null, d);
    }
}




