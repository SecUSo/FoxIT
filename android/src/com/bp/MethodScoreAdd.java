package com.bp;

import android.util.Log;

/**
 * Created by Tim on 30.07.2016.
 */
public class MethodScoreAdd extends Method{

    /**
     *callClassMethod to manipulate the score used by lections with QuizSlides
     * @author Tim
     * @param points number of points to be added
     */
    @Override
    public void callClassMethod(String points){
        try{
        if(activity instanceof LectionActivity){
            ((LectionActivity)activity).lection.score+= Integer.parseInt(points);
        }}catch(NumberFormatException e){
            Log.d("error", "the score was changed by a not number");
        }
    }
}
