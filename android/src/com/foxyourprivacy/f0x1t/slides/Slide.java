package com.foxyourprivacy.f0x1t.slides;

import android.os.Bundle;

/**
 * Created by Tim on 25.06.2016.
 */
public abstract class Slide extends android.support.v4.app.Fragment {
    //Strings which describes which slide shall be next, null if its the succeeding number
    public int nextSlide = -99;
    public int backSlide = -99;
    public String type = "textimage";
    protected String slideInfo;

    @Override
    /**
     * Enables to pass arguments to the fragment
     * @author Tim
     */
    public void setArguments(Bundle arg) {
        //fetches the slide information
        slideInfo = arg.getString("slide");
        //Log.d("Slide-setArguments", slideInfo);
        //TODO next und back aus slide bekommen
        //fetches which slide is the succeeding and previous Slide

    }

    public int next() {
        return nextSlide;
    }

    public int back() {
        return backSlide;
    }

    public boolean isLessonSolved() {
        return true;
    }

    public abstract void fillLayout();

    public String getSlideInfo() {
        return slideInfo;
    }


}