package com.bp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ich on 25.06.2016.
 */
public class QuestionSlide extends Slide {
    View view;
    //method for the left button
    Method methodLeft;
    //method for the right button
    Method methodRight;

    /**
     *@author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view=inflater.inflate(R.layout.layout_slide_question,container,false);
       //generic method of all slides to fill the slideDescription into the layout
        fillLayout();

        return view;
    }


    @Override
    /**
     * @author Tim
     */
    void fillLayout() {
        MethodFactory methodFactory= new MethodFactory(getActivity());
        methodLeft =methodFactory.createMethod(parameter.get("method"));
        methodRight=methodFactory.createMethod(parameter.get("method2"));

        TextView text=(TextView) view.findViewById(R.id.question_text);
        text.setText(parameter.get("text"));

        Button buttonLeft =(Button) view.findViewById((R.id.button_left));
        buttonLeft.setText(parameter.get("buttonText"));

        buttonLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                methodLeft.method(parameter.get("methodParameter"));
            }});

        Button buttonRight =(Button) view.findViewById((R.id.button_right));
        buttonRight.setText(parameter.get("buttonText2"));



        buttonRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                methodRight.method(parameter.get("methodParameter2"));
            }});

    }

}