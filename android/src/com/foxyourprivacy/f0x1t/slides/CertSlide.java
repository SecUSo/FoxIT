package com.foxyourprivacy.f0x1t.slides;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.activities.LessonActivity;

import java.util.regex.Pattern;

/**
 * Created by noah on 04.06.17.
 */

public class CertSlide extends Slide {
    public Boolean solved = false;
    View view;
    int completepoints = 0;
    int reachedpoints = 0;
    String positiveAnswer, negativeAnswer;

    @Override
    public void fillLayout() {
        TextView answer = (TextView) view.findViewById(R.id.certificate_answer);
        TextView points = (TextView) view.findViewById(R.id.certificate_points);
        Log.d("points", reachedpoints + " / " + completepoints);
        if (reachedpoints >= completepoints) {
            answer.setText(positiveAnswer);
            points.setTextColor(Color.GREEN);
            solved = true;

        } else {
            answer.setText(negativeAnswer);
            points.setTextColor(Color.RED);
        }
        Log.d("filllayoutactivity", getActivity().toString());

        points.setText(String.valueOf(reachedpoints) + "/" + String.valueOf(((LessonActivity) getActivity()).lesson.maxscore));

    }

    @Override
    public void setArguments(Bundle arg) {
        super.setArguments(arg);
        slideInfo = slideInfo.replaceFirst(Pattern.quote("[CERT]"), "");
        String[] informations = slideInfo.split(";");
        Log.d("Certslide", slideInfo);
        completepoints = Integer.valueOf(informations[0]);
        Log.d("completepoints", completepoints + "");
        positiveAnswer = informations[1];
        negativeAnswer = informations[2];


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_slide_certificate, container, false);
        Log.d("create2activity", getActivity().toString());
        type = "cert";
        return view;
    }

    public void evaluate() {
        reachedpoints = ((LessonActivity) getActivity()).lesson.score;
        fillLayout();

    }

    @Override
    public boolean isLessonSolved() {
        return solved;
    }
}
