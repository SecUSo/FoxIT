/*
package com.foxyourprivacy.f0x1t.slides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.lessonmethods.MethodFactory;

*/
/**
 * Created by Ich on 25.06.2016.
 *//*

public class Quiz4Slide extends QuizSlide {
    View view;

    boolean answer1true = false;
    boolean answer2true = false;
    boolean answer3true = false;
    boolean answer4true = false;

    //Strings to be displayed if the answer is right or wrong
    String rightAnswer = "Richtig.";
    String wrongAnswer = "Falsch.";

    //points given for a correct solution
    String points = "0";


    */
/**
     * @author Tim
 *//*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.layout_slide_quiz, container, false);
        fillLayout();
        return view;
    }


    @Override
    public
    */
/**
     * @author Tim
 *//*

    void fillLayout() {
        TextView text = (TextView) view.findViewById(R.id.quiz_text);
        text.setText(parameter.get("text"));

        CheckBox box1 = (CheckBox) view.findViewById(R.id.checkBox);
        box1.setText(parameter.get("answer1text"));
        if (parameter.get("answer1solution").equals("true")) {
            answer1true = true;
        }
        CheckBox box2 = (CheckBox) view.findViewById(R.id.checkBox2);
        box2.setText(parameter.get("answer2text"));
        if (parameter.get("answer2solution").equals("true")) {
            answer2true = true;
        }
        CheckBox box3 = (CheckBox) view.findViewById(R.id.checkBox3);
        box3.setText(parameter.get("answer3text"));
        if (parameter.get("answer3solution").equals("true")) {
            answer3true = true;
        }
        CheckBox box4 = (CheckBox) view.findViewById(R.id.checkBox4);
        box4.setText(parameter.get("answer4text"));
        if (parameter.get("answer4solution").equals("true")) {
            answer4true = true;
        }

        points = parameter.get("points");

        if (parameter.get("successText") != null) {
            rightAnswer = parameter.get("successText");
        }
        if (parameter.get("failureText") != null) {
            wrongAnswer = parameter.get("failureText");
        }

    }

    @Override
    */
/**checks if the answer is right
     * @author Tim
 *//*

    public boolean evaluation() {
        //disables the checkboxes for them not to be changed
        CheckBox box1 = (CheckBox) view.findViewById(R.id.checkBox);
        box1.setEnabled(false);
        CheckBox box2 = (CheckBox) view.findViewById(R.id.checkBox2);
        box2.setEnabled(false);
        CheckBox box3 = (CheckBox) view.findViewById(R.id.checkBox3);
        box3.setEnabled(false);
        CheckBox box4 = (CheckBox) view.findViewById(R.id.checkBox4);
        box4.setEnabled(false);
        evaluated = true;
        //shows which answers are right or wrong
        if ((answer1true && box1.isChecked()) || (!answer1true && !box1.isChecked())) {
            box1.setBackgroundResource(R.color.rightAnswer);
        } else {
            box1.setBackgroundResource(R.color.wrongAnswer);
        }
        if ((answer2true && box2.isChecked()) || (!answer2true && !box2.isChecked())) {
            box2.setBackgroundResource(R.color.rightAnswer);
        } else {
            box2.setBackgroundResource(R.color.wrongAnswer);
        }
        if ((answer3true && box3.isChecked()) || (!answer3true && !box3.isChecked())) {
            box3.setBackgroundResource(R.color.rightAnswer);
        } else {
            box3.setBackgroundResource(R.color.wrongAnswer);
        }
        if ((answer4true && box4.isChecked()) || (!answer4true && !box4.isChecked())) {
            box4.setBackgroundResource(R.color.rightAnswer);
        } else {
            box4.setBackgroundResource(R.color.wrongAnswer);
        }

        //displayes the victory or failure text
        if (answer1true == box1.isChecked() && answer2true == box2.isChecked() && answer3true == box3.isChecked() && answer4true == box4.isChecked()) {
            Toast toast = Toast.makeText(getActivity(), rightAnswer, Toast.LENGTH_LONG);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            //v.setBackgroundColor(Color.GREEN);
            toast.show();
            MethodFactory factory = new MethodFactory(getActivity());
            factory.createMethod("scoreAdd").callClassMethod(points);

        } else {

            Toast toast = Toast.makeText(getActivity(), wrongAnswer, Toast.LENGTH_LONG);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            //v.setBackgroundColor(Color.RED);
            toast.show();


        }
        return false;
    }


}*/
