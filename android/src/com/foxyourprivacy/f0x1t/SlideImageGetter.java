package com.foxyourprivacy.f0x1t;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by noah on 13.09.17.
 */

public class SlideImageGetter implements Html.ImageGetter {
    Context con;
    TextView container;


    public SlideImageGetter(TextView v, Context context) {
        container = v;
        con = context;
    }

    @Override
    public Drawable getDrawable(String source) {
        DownloadDrawable ddrawable = new DownloadDrawable();

        AsyncDrawableLoader loader = new AsyncDrawableLoader(ddrawable);
        loader.execute(source);

        return ddrawable;

    }

    private class AsyncDrawableLoader extends AsyncTask<String, Void, Drawable> {
        DownloadDrawable ddrawable;

        public AsyncDrawableLoader(DownloadDrawable dd) {
            this.ddrawable = dd;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String src = params[0];
            Drawable result = null;
            try {
                result = loadDrawable(src);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            if (drawable != null) {
                ddrawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ddrawable.drawable = drawable;
                SlideImageGetter.this.container.invalidate();
                SlideImageGetter.this.container.setHeight(SlideImageGetter.this.container.getHeight() + drawable.getIntrinsicHeight());

            }

        }

        public Drawable loadDrawable(String urls) throws IOException {
            URL url = new URL(urls);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            try {

                InputStream in = new BufferedInputStream(connection.getInputStream());
                Drawable drawable = Drawable.createFromStream(in, "src");
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                connection.disconnect();
            }
        }

    }
}

