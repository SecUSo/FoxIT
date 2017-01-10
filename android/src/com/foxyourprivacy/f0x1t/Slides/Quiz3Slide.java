package com.foxyourprivacy.f0x1t.Slides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.foxyourprivacy.f0x1t.LectionMethods.MethodFactory;
import com.foxyourprivacy.f0x1t.R;

/**
 * Created by Ich on 25.06.2016.
 */
public class Quiz3Slide extends QuizSlide {
    View view;

    boolean answer1true = false;
    boolean answer2true = false;
    boolean answer3true = false;

    //Strings to be displayed if the answer is right or wrong
    String rightAnswer = "Richtig.";
    String wrongAnswer = "Falsch.";

    //points given for a correct solution
    String points = "0";


    /**
     * @author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.layout_slide_quiz, container, false);
        fillLayout();
        nextSlide = "quiz";
        return view;
    }


    @Override
    public
    /**
     * @author Tim
     */
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


        points = parameter.get("points");

        if (parameter.get("successText") != null) {
            rightAnswer = parameter.get("successText");
        }
        if (parameter.get("failureText") != null) {
            wrongAnswer = parameter.get("failureText");
        }

    }

    @Override
    /**checks if the answer is right
     * @author Tim
     */
    public boolean evaluation() {
        //disables the checkboxes for them not to be changed
        CheckBox box1 = (CheckBox) view.findViewById(R.id.checkBox);
        box1.setEnabled(false);
        CheckBox box2 = (CheckBox) view.findViewById(R.id.checkBox2);
        box2.setEnabled(false);
        CheckBox box3 = (CheckBox) view.findViewById(R.id.checkBox3);
        box3.setEnabled(false);

        nextSlide = null;
        evaluated = true;
        //shows which answers are right or wrong
        if (answer1true) {
            box1.setBackgroundResource(R.color.rightAnswer);
        } else {
            box1.setBackgroundResource(R.color.wrongAnswer);
        }
        if (answer2true) {
            box2.setBackgroundResource(R.color.rightAnswer);
        } else {
            box2.setBackgroundResource(R.color.wrongAnswer);
        }
        if (answer3true) {
            box3.setBackgroundResource(R.color.rightAnswer);
        } else {
            box3.setBackgroundResource(R.color.wrongAnswer);
        }


        //displayes the victory or failure text
        if (answer1true == box1.isChecked() && answer2true == box2.isChecked() && answer3true == box3.isChecked()) {
            Toast.makeText(getActivity(), rightAnswer, Toast.LENGTH_LONG).show();
            MethodFactory factory = new MethodFactory(getActivity());
            factory.createMethod("scoreAdd").callClassMethod(points);

        } else {
            Toast.makeText(getActivity(), wrongAnswer,
                    Toast.LENGTH_LONG).show();

        }
        return false;
    }


}