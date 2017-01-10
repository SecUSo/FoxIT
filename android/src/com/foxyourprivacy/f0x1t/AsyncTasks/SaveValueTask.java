package com.foxyourprivacy.f0x1t.AsyncTasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.foxyourprivacy.f0x1t.ValueKeeper;

/**
 * Created by Ich on 09.09.2016.
 */

/**
 * Class to handle the loading of images in an extra task
 */
public class SaveValueTask extends AsyncTask<Void, Void, Void> {


    /**
     * insert the loaded image in the imageView
     *
     * @author Tim
     */
    protected void onPostExecute(Bitmap result) {
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ValueKeeper v = ValueKeeper.getInstance();
        v.saveInstance();
        return null;
    }
}




