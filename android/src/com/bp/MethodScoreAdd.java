package com.bp;

import android.util.Log;

/**
 * Created by Tim on 30.07.2016.
 */
public class MethodScoreAdd extends Method{

    /**
     *method to manipulate the score used by lections with QuizSlides
     * @author Tim
     * @param points number of points to be added
     */
    @Override
    public void method(String points){
        try{
        if(activity instanceof LectionActivity){
            ((LectionActivity)activity).lection.score+= Integer.parseInt(points);
        }}catch(NumberFormatException e){
            Log.d("error", "Es wurde der Score mit einer nicht-Zahl verändert");
        }
    }
}
