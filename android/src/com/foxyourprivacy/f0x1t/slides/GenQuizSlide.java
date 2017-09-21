package com.foxyourprivacy.f0x1t.slides;

import android.os.Bundle;

/**
 * Created by noah on 04.06.17.
 */

public class GenQuizSlide extends Slide {
    protected boolean evaluated = false;


    @Override
    public void fillLayout() {

    }

    @Override
    public void setArguments(Bundle arg) {
        super.setArguments(arg);
        slideInfo = slideInfo.replaceFirst("[QUIZ]", "");
    }


    public boolean gotEvaluated() {
        return evaluated;
    }

    public boolean evaluation() {
        return true;
    }

}
