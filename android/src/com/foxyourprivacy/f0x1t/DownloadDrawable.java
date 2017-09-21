package com.foxyourprivacy.f0x1t;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by noah on 13.09.17.
 */

public class DownloadDrawable extends BitmapDrawable {
    protected Drawable drawable;

    @Override
    public void draw(Canvas canv) {
        if (drawable != null) {
            drawable.draw(canv);
        }
    }
}
