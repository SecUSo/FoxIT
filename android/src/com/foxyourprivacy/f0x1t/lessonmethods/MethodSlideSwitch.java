package com.foxyourprivacy.f0x1t.lessonmethods;

import com.foxyourprivacy.f0x1t.activities.LectionActivity;

/**
 * Created by Tim on 19.07.2016.
 */
public class MethodSlideSwitch extends Method {
    @Override
    /**
     * jumps to the described slide
     * @author Tim
     * @param destination a string containing an int number describing the slide to jump to
     */
    public void callClassMethod(String destination) {

        if (activity instanceof LectionActivity) {
            ((LectionActivity) activity).jumpToSlide(destination);
        }
    }


}
