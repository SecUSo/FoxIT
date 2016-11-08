package com.bp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ich on 25.06.2016.
 */
public class TextEvaluationSlide extends EvaluationSlide {
    View view;

    String questionText;


    //points given for a correct solution
    String points="0";


    /**
     *@author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view=inflater.inflate(R.layout.layout_slide_question_text,container,false);
        fillLayout();
        return view;
    }


    @Override
    /**
     * @author Tim
     */
    void fillLayout() {
        TextView text=(TextView) view.findViewById(R.id.question_text);
        text.setText(parameter.get("text"));
        questionText=parameter.get("text");




        points=parameter.get("points");


    }

    @Override
    /**checks if the answer is right
     * @author Tim
     */
    public boolean evaluation(){
        EditText textField= (EditText) view.findViewById(R.id.answer_text_field);
        String answer=textField.getText().toString();


        ValueKeeper v=ValueKeeper.getInstance();
            LectionActivity l=(LectionActivity) getActivity();
            l.addEvaluationResult(questionText,answer);
            evaluated=true;
        nextSlide=null;


    return true;

    }


}