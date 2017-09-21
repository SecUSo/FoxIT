package com.foxyourprivacy.f0x1t.slides;

import android.os.Bundle;

import java.util.regex.Pattern;

/**
 * Created by noah on 04.06.17.
 */

public class CertSlide extends Slide {
    @Override
    public void fillLayout() {

    }

    @Override
    public void setArguments(Bundle arg) {
        super.setArguments(arg);
        slideInfo = slideInfo.replaceFirst(Pattern.quote("[CERT]"), "");
    }
}
