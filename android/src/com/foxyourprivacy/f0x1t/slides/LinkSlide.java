package com.foxyourprivacy.f0x1t.slides;

import android.os.Bundle;

import java.util.regex.Pattern;

/**
 * A slide that contains a webview to show a Website of interest
 * Created by noah on 04.06.17.
 */

//TODO implement webview
public class LinkSlide extends Slide {
    @Override
    public void fillLayout() {

    }

    @Override
    public void setArguments(Bundle arg) {
        super.setArguments(arg);
        slideInfo = slideInfo.replaceFirst(Pattern.quote("[LINK]"), "");
    }
}
