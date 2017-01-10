package com.foxyourprivacy.f0x1t.Slides;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxyourprivacy.f0x1t.Activities.LectionActivity;
import com.foxyourprivacy.f0x1t.R;

/**
 * Created by Ich on 25.06.2016.
 */
//Slide to be displayed at the end of an quizLection for celebrating the results
public class CertificateSlide extends Slide {
    View view;
    boolean lectionSolved = false;

    /**
     * fills the layout by calling fillLayout and evaluates points needed
     *
     * @author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.layout_slide_certificate, container, false);
        int pointsNeeded = 0;
        try {
            //receiving how much points are necessary for a victory
            pointsNeeded = Integer.parseInt(parameter.get("pointsNeeded"));
        } catch (NumberFormatException e) {
            Log.d("CertificateSlide", "NumberformatException" + e);

        }
        //if the amount of points is meed set lectionSolved to true
        if (getActivity() instanceof LectionActivity) {
            if (((LectionActivity) getActivity()).lection.score >= pointsNeeded) {
                lectionSolved = true;
            }
        }
        fillLayout();

        return view;
    }

    /**
     * fills the layout with the designated content
     *
     * @author Tim
     */
    @Override
    public void fillLayout() {
        TextView text = (TextView) view.findViewById(R.id.text);

        //if the necessary points for a victory are reached the victory message is displayed
        if (lectionSolved) {
            text.setText(parameter.get("successText"));


        } else {
            //otherwise the failure message
            text.setText(parameter.get("failureText"));
        }


    }

    @Override
    /**
     * @author Tim
     */
    public boolean isLectionSolved() {
        return lectionSolved;

    }

}
