package com.foxyourprivacy.f0x1t.lessonmethods;

import android.util.Log;

import com.foxyourprivacy.f0x1t.activities.LessonActivity;

/**
 * Created by Tim on 30.07.2016.
 */
public class MethodScoreAdd extends Method {

    /**
     * callClassMethod to manipulate the score used by lessons with QuizSlides
     *
     * @param points number of points to be added
     * @author Tim
     */
    @Override
    public void callClassMethod(String points) {
        try {
            if (activity instanceof LessonActivity) {
                ((LessonActivity) activity).lesson.score += Integer.parseInt(points);
            }
        } catch (NumberFormatException e) {
            Log.d("error", "the score was changed by a not number");
        }
    }
}
