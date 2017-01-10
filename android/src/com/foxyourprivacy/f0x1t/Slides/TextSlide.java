package com.foxyourprivacy.f0x1t.Slides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxyourprivacy.f0x1t.R;

/**
 * Created by Tim on 25.06.2016.
 */
public class TextSlide extends Slide {
    View view;

    /* fills the slide's layout by calling fillLayout
     *@author Tim
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.layout_slide_text, container, false);
        fillLayout();
        return view;
    }


    /**
     * fills the slide's layout
     *
     * @author Tim
     */
    @Override
    public void fillLayout() {
        //sets the displayed text
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(parameter.get("text"));
    }


}