///*
//package com.foxyourprivacy.f0x1t.slides;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.foxyourprivacy.f0x1t.R;
//import com.foxyourprivacy.f0x1t.ValueKeeper;
//import com.foxyourprivacy.f0x1t.activities.LessonActivity;
//
//
///**
// * An implementation of the Evaluationslide
// * Should be kept so we can use it for later studies
// * Created by Tim on 25.06.2016.
// */
//public class TextEvaluationSlide extends EvaluationSlide {
//    View view;
//
//    String questionText;
//
//
//    //points given for a correct solution
//    String points = "0";
//
//
//
//    /**
//     * @author Tim
//    */
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
//        view = inflater.inflate(R.layout.layout_slide_question_text, container, false);
//        fillLayout();
//        return view;
//    }
//
//
//
//    /**
//     * @author Tim
//    */
//    @Override
//    public void fillLayout() {
//        TextView text = (TextView) view.findViewById(R.id.question_text);
//        text.setText(parameter.get("text"));
//        questionText = parameter.get("text");
//
//
//        points = parameter.get("points");
//
//
//    }
//
//
//    /**checks if the answer is right
//     * @author Tim
//    */
//    @Override
//    public boolean evaluation() {
//        EditText textField = (EditText) view.findViewById(R.id.answer_text_field);
//        String answer = textField.getText().toString();
//
//
//        ValueKeeper v = ValueKeeper.getInstance();
//        LessonActivity l = (LessonActivity) getActivity();
//        l.addEvaluationResult(questionText, answer);
//        evaluated = true;
//        nextSlide = -99;
//
//
//        return true;
//
//    }
//
//
//}
