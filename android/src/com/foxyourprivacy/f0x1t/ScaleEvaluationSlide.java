package com.foxyourprivacy.f0x1t;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foxyourprivacy.f0x1t.activities.LectionActivity;
import com.foxyourprivacy.f0x1t.slides.EvaluationSlide;

/**
 * Created by Ich on 25.06.2016.
 */
public class ScaleEvaluationSlide extends EvaluationSlide {
    View view;

    String questionText;


    //points given for a correct solution
    String points = "0";


    /**
     * @author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.layout_slide_question_scale, container, false);
        fillLayout();
        return view;
    }


    @Override
    public
    /**
     * @author Tim
     */
    void fillLayout() {
        TextView text = (TextView) view.findViewById(R.id.question_text);
        text.setText(parameter.get("text"));
        questionText = parameter.get("text");


        points = parameter.get("points");


    }

    @Override
    /**checks if the answer is right
     * @author Tim
     */
    public boolean evaluation() {
        RadioGroup radioButtons = (RadioGroup) view.findViewById(R.id.radio_group);
        int radioButtonID = radioButtons.getCheckedRadioButtonId();
        View radioButton = radioButtons.findViewById(radioButtonID);
        int idx = radioButtons.indexOfChild(radioButton);
        if (idx == -1) {
            Toast toast = Toast.makeText(getActivity(), "Bitte beantworte die Frage.", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else {
            ValueKeeper v = ValueKeeper.getInstance();
            LectionActivity l = (LectionActivity) getActivity();
            l.addEvaluationResult(questionText, Integer.toString(idx));
            evaluated = true;
            nextSlide = null;
            return true;
        }


    }


}